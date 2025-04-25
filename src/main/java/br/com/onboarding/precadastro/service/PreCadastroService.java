package br.com.onboarding.precadastro.service;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.onboarding.infraestructure.messaging.broker.IMessageBroker;
import br.com.onboarding.infraestructure.messaging.broker.MessageTopic;
import br.com.onboarding.integracao.dto.valid.ValidOnboardingDataDTO;
import br.com.onboarding.integracao.service.HistoricoSincronizacaoService;
import br.com.onboarding.precadastro.enumeration.SituacaoPreCadastro;
import br.com.onboarding.precadastro.model.PreCadastro;
import br.com.onboarding.precadastro.repository.PreCadastroRepository;

@Service
public class PreCadastroService {

    private final IMessageBroker messageBroker;
    private final PreCadastroRepository preCadastroRepository;
    private final HistoricoSincronizacaoService historicoSincronizacaoService;

    public PreCadastroService(IMessageBroker messageBroker, PreCadastroRepository preCadastroRepository,
            HistoricoSincronizacaoService historicoSincronizacaoService) {
        this.messageBroker = messageBroker;
        this.preCadastroRepository = preCadastroRepository;
        this.historicoSincronizacaoService = historicoSincronizacaoService;
        this.messageBroker.subscribe(MessageTopic.DADOS_OBTIDOS, this::criarPreCadastro);
    }

    private void criarPreCadastro(Object onboardingDataDto) {

        ValidOnboardingDataDTO validDto = (ValidOnboardingDataDTO) onboardingDataDto;

        try {
            // Cria uma nova instância de PreCadastro
            PreCadastro preCadastro = PreCadastro.valueOf(validDto);

            // Salva o PreCadastro no banco de dados
            preCadastroRepository.save(preCadastro);

            this.messageBroker.publish(MessageTopic.DADOS_SINCRONIZADOS, validDto.getHash());

        } catch (Exception e) {
            // Lidar com exceções específicas, se necessário
            historicoSincronizacaoService.registrarFalhaSincronizacao(validDto.getHash(), e.getMessage());
        }

    }

    public Map<SituacaoPreCadastro, Long> obterEstatisticasPreCadastro(LocalDateTime startTime, LocalDateTime endTime) {
        Map<SituacaoPreCadastro, Long> estatisticas = new EnumMap<>(SituacaoPreCadastro.class);
        for (SituacaoPreCadastro status : SituacaoPreCadastro.values()) {
            long count = preCadastroRepository.countBySituacaoAndDataCadastroBetween(status, startTime, endTime);
            estatisticas.put(status, count);
        }
        return estatisticas;
    }
}