package br.com.onboarding.integration.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.onboarding.infraestructure.exception.OnboardingServiceException;
import br.com.onboarding.infraestructure.messaging.broker.IMessageBroker;
import br.com.onboarding.infraestructure.messaging.broker.MessageTopic;
import br.com.onboarding.integration.dto.OnboardingDataDto;
import br.com.onboarding.integration.enumeration.FetchStatus;
import br.com.onboarding.integration.model.HistoricoSincronizacaoOnboarding;
import br.com.onboarding.integration.port.IFechingOnboardData;
import br.com.onboarding.integration.repository.HistoricoSincronizacaoOnboardingRepository;

@Service
public class ObtencaoDadosOnBoardingService {

    private static final Logger log = LoggerFactory.getLogger(ObtencaoDadosOnBoardingService.class);


    private final IMessageBroker messageBroker;

    private final IFechingOnboardData fechingOnboardData;

    private final HistoricoSincronizacaoOnboardingRepository historicoSincronizacaoOnboardingRepository;

    public ObtencaoDadosOnBoardingService(IMessageBroker messageBroker, IFechingOnboardData fechingOnboardData,
            HistoricoSincronizacaoOnboardingRepository historicoSincronizacaoOnboardingRepository) {
        this.fechingOnboardData = fechingOnboardData;
        this.messageBroker = messageBroker;
        this.historicoSincronizacaoOnboardingRepository = historicoSincronizacaoOnboardingRepository;
        this.messageBroker.subscribe(MessageTopic.DADOS_PENDENTES, this::obterDadosOnboarding);
    }

    private void obterDadosOnboarding(Object hash) {
        log.info("Iniciando obtenção de dados de onboarding para o hash: {}", hash);
        HistoricoSincronizacaoOnboarding historico = new HistoricoSincronizacaoOnboarding();
        historico.setHash(hash.toString());
        
        try {
            OnboardingDataDto dto = fechingOnboardData.fetchOnboardingData(hash);
            this.messageBroker.publish(MessageTopic.DADOS_OBTIDOS, dto);        
            historico.setStatus(FetchStatus.SUCESSO);
            historico.setDataHora(LocalDateTime.now());
            historicoSincronizacaoOnboardingRepository.save(historico);
            log.info("Dados de onboarding obtidos com sucesso para o hash: {}", hash);
        } catch (OnboardingServiceException e) {
            log.error("Erro ao salvar histórico de sincronização: {}", e.getMessage());
            historico.setStatus(FetchStatus.FALHA);
            historico.setDataHora(LocalDateTime.now());
            historico.setMensagemErro(e.getMessage());
        }

        historicoSincronizacaoOnboardingRepository.save(historico);

    }

}