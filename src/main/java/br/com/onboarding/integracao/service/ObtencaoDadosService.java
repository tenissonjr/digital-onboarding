package br.com.onboarding.integracao.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.onboarding.infraestructure.exception.IntegracaoOnboardingServiceException;
import br.com.onboarding.infraestructure.messaging.broker.IMessageBroker;
import br.com.onboarding.infraestructure.messaging.broker.MessageTopic;
import br.com.onboarding.integracao.dto.OnboardingDataDto;
import br.com.onboarding.integracao.enumeration.SituacaoSincronizacaoEnum;
import br.com.onboarding.integracao.model.HistoricoSincronizacao;
import br.com.onboarding.integracao.port.IIntegracaoOnboardDigitalService;
import br.com.onboarding.integracao.repository.HistoricoSincronizacaoRepository;

@Service
public class ObtencaoDadosService {

    private static final Logger log = LoggerFactory.getLogger(ObtencaoDadosService.class);


    private final IMessageBroker messageBroker;

    private final IIntegracaoOnboardDigitalService fetchingOnboardDataService;

    private final HistoricoSincronizacaoRepository historicoSincronizacaoOnboardingRepository;

    
    public ObtencaoDadosService(IMessageBroker messageBroker, IIntegracaoOnboardDigitalService fetchingOnboardDataService,
            HistoricoSincronizacaoRepository historicoSincronizacaoOnboardingRepository) {
        this.fetchingOnboardDataService = fetchingOnboardDataService;
        this.messageBroker = messageBroker;
        this.historicoSincronizacaoOnboardingRepository = historicoSincronizacaoOnboardingRepository;
        this.messageBroker.subscribe(MessageTopic.DADOS_PENDENTES, this::obterDadosOnboarding);
    }

    @Transactional
    public void obterDadosOnboarding(Object hash) {
        String hashStr = hash.toString();
        log.info("Iniciando obtenção de dados de onboarding para o hash: {}", hashStr);
        HistoricoSincronizacao historico = new HistoricoSincronizacao();
        historico.setHash(hash.toString());
        
        try {
            OnboardingDataDto dto = fetchingOnboardDataService.obterDadosPessoais(hash);
            this.messageBroker.publish(MessageTopic.DADOS_OBTIDOS, dto);        
            historico.setSituacao(SituacaoSincronizacaoEnum.SUCESSO_SINCRONIZACAO);
            log.info("Dados de onboarding obtidos com sucesso para o hash: {}", hashStr);
        } catch (IntegracaoOnboardingServiceException e) {
            log.error("Erro ao obter dados de onboarding para o hash {}: {}", hashStr, e.getMessage());
            historico.setSituacao(SituacaoSincronizacaoEnum.FALHA_SINCRONIZACAO);
            historico.setMensagemErro(e.getMessage());
        } catch (Exception e) {
            log.error("Erro inesperado ao obter dados de onboarding para o hash {}: {}", hashStr, e.getMessage(), e);
            historico.setSituacao(SituacaoSincronizacaoEnum.FALHA_SINCRONIZACAO);
            historico.setMensagemErro("Erro inesperado: " + e.getMessage());
        } finally {
            historico.setDataHora(LocalDateTime.now());
            historicoSincronizacaoOnboardingRepository.save(historico);
        }
        

    }

}