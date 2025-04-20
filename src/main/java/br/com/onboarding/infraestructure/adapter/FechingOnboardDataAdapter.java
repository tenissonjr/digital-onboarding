package br.com.onboarding.infraestructure.adapter;

import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.onboarding.infraestructure.exception.OnboardingServiceException;
import br.com.onboarding.integration.dto.OnboardingDataDto;
import br.com.onboarding.integration.port.IFechingOnboardData;
import io.swagger.v3.core.util.Json;

@Service
public class FechingOnboardDataAdapter implements IFechingOnboardData {

    private static final Logger log = LoggerFactory.getLogger(FechingOnboardDataAdapter.class);

    @Value("${app.onboarding.api.url}")
    private String apiUrl;

    @Value("${app.retry.max-attempts}")
    private int maxAttempts;

    @Value("${app.retry.delay-time}")
    private int delayTime;


    @Override
    public OnboardingDataDto fetchOnboardingData(Object hash) throws OnboardingServiceException {
        
        String url = apiUrl + hash.toString();
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        
        
        int attempts = 0;
        Exception lastException = null;
        long startTime = System.currentTimeMillis();

        while (attempts < maxAttempts) {
            try {
                HttpRequest request = buildHttpRequest(url);
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    return parseJsonToDto(response.body());
                } else {
                    log.warn("Falha ao obter dados para hash {}. Status HTTP: {}", hash, response.statusCode());
                }
            } catch (Exception e) {
                lastException = e;
                log.warn("Tentativa {} falhou ao buscar dados para hash {}: {}", (attempts + 1), hash, e.getMessage());
            }

            attempts++;
            if (attempts < maxAttempts) {
                waitBeforeRetry();
            }
        }
        long totalTime = System.currentTimeMillis() - startTime;
        log.error("Todas as {} tentativas falharam ao buscar dados para hash: {}. Tempo total: {} ms", maxAttempts, hash, totalTime);
        throw new OnboardingServiceException("Falha ao obter dados de onboarding após " + maxAttempts + " tentativas", lastException);
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
            throw new OnboardingServiceException("Thread interrompida durante o tempo de espera", ie);
        }
    }

    private OnboardingDataDto parseJsonToDto(String json) {
        try {
            OnboardingDataDto dto = Json.mapper().readValue(new StringReader(json), OnboardingDataDto.class);
            log.info("Conversão de JSON para DTO realizada com sucesso.");
            log.info("Dados convertidos::Nome => ", dto.nome());
            return dto;
        } catch (Exception e) {
            log.error("Erro ao converter JSON para DTO: {}", e.getMessage());
            throw new OnboardingServiceException("Erro ao converter JSON para DTO", e);
        }
    }


}
