package br.com.onboarding.infraestructure.adapter;

import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.onboarding.infraestructure.exception.IntegracaoOnboardingServiceException;
import br.com.onboarding.integracao.dto.OnboardingDataDto;
import br.com.onboarding.integracao.port.IIntegracaoOnboardDigitalService;
import io.swagger.v3.core.util.Json;

@Service
public class FechingOnboardDataAdapter implements IIntegracaoOnboardDigitalService {

    private static final Logger log = LoggerFactory.getLogger(FechingOnboardDataAdapter.class);

    @Value("${app.onboarding.api.url}")
    private String apiUrl;

    @Value("${app.retry.max-attempts}")
    private int maxAttempts;

    @Value("${app.retry.delay-time}")
    private int delayTime;


    @Override
    public OnboardingDataDto obterDadosPessoais(Object hash) throws IntegracaoOnboardingServiceException {
        
        String url = apiUrl + hash.toString();
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        
        
        int attempts = 0;
        long startTime = System.currentTimeMillis();

        StringBuilder erros = new StringBuilder(apiUrl);
        while (attempts < maxAttempts) {
            try {
                HttpRequest request = buildHttpRequest(url);
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    return parseJsonToDto(response.body());
                } else {
                    erros.append(String.format("\nTentativa: %s. Data : %s . Status HTTP: %d",(attempts + 1), LocalDateTime.now() , response.statusCode()));
                    log.warn("Tentativa {} falhou ao buscar dados : Status HTTP: : {}", (attempts + 1), hash,  response.statusCode());
                }
            } catch (Exception e) {
                erros.append(String.format("\nTentativa: %s. Data : %s .Falha ao buscar dados. Mensagem: %s", (attempts + 1), LocalDateTime.now(), e.getCause()));
                log.warn("Tentativa {}  falhou ao buscar dados para hash {}: {}", (attempts + 1), hash, e.getCause());
            }
            attempts++;
            if (attempts < maxAttempts) {
                waitBeforeRetry();
            }
        }
        long totalTime = System.currentTimeMillis() - startTime;
        log.error("Todas as {} tentativas falharam ao buscar dados para hash: {}. Tempo total: {} ms", maxAttempts, hash, totalTime);
        throw new IntegracaoOnboardingServiceException("Falha ao obter dados de onboarding após " + maxAttempts + " tentativas" + ".\n Erros: " + erros.toString());
    }

    private HttpRequest buildHttpRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
    }

    private void waitBeforeRetry() {
        try {
            log.info("Aguardando {} segundos antes da próxima tentativa...", delayTime);
            Thread.sleep(delayTime * 1000L);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new IntegracaoOnboardingServiceException("Thread interrompida durante o tempo de espera", ie);
        }
    }

    private OnboardingDataDto parseJsonToDto(String json) {
        try {
            OnboardingDataDto dto = Json.mapper().readValue(new StringReader(json), OnboardingDataDto.class);
            dto.setDadosOriginais(json);
            log.info("Conversão de JSON para DTO realizada com sucesso.");
            log.info("Dados convertidos::Nome => ", dto.getNome());
            return dto;
        } catch (Exception e) {
            log.error("Erro ao converter JSON para DTO: {}", e.getMessage());
            throw new IntegracaoOnboardingServiceException("Erro ao converter JSON para DTO", e);
        }
    }


}
