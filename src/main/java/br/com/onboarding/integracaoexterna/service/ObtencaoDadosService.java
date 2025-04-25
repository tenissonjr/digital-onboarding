package br.com.onboarding.integracaoexterna.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.onboarding.infraestructure.exception.IntegracaoPreCadastroExternoException;
import br.com.onboarding.infraestructure.messaging.broker.IMessageBroker;
import br.com.onboarding.infraestructure.messaging.broker.MessageTopic;
import br.com.onboarding.integracaoexterna.port.IIntegracaoPreCadastroExternoService;
import br.com.onboarding.integracaoexterna.port.IPreCadastroExterno;

@Service
public class ObtencaoDadosService {

    private static final Logger log = LoggerFactory.getLogger(ObtencaoDadosService.class);

    private final IMessageBroker messageBroker;

    private final IIntegracaoPreCadastroExternoService fetchingOnboardDataService;

    private final HistoricoSincronizacaoService historicoSincronizacaoService;;

    public ObtencaoDadosService(IMessageBroker messageBroker,
            IIntegracaoPreCadastroExternoService fetchingOnboardDataService,
            HistoricoSincronizacaoService historicoSincronizacaoService) {
        this.fetchingOnboardDataService = fetchingOnboardDataService;
        this.messageBroker = messageBroker;
        this.historicoSincronizacaoService = historicoSincronizacaoService;
        this.messageBroker.subscribe(MessageTopic.DADOS_PENDENTES, this::obterDadosOnboarding);
    }

    @Transactional
    public void obterDadosOnboarding(Object hash) {
        String hashStr = hash.toString();
        log.info("Iniciando obtenção de dados de onboarding para o hash: {}", hashStr);
        try {
            IPreCadastroExterno dto = fetchingOnboardDataService.obterPreCadastroExterno(hash);
            this.messageBroker.publish(MessageTopic.DADOS_OBTIDOS, dto);
        } catch (IntegracaoPreCadastroExternoException e) {
            log.error("Erro ao obter dados de onboarding para o hash {}: {}", hashStr, e.getMessage());
            historicoSincronizacaoService.registrarFalhaSincronizacao(hashStr, e.getMessage());
        } catch (Exception e) {
            log.error("Erro inesperado ao obter dados de onboarding para o hash {}: {}", hashStr, e.getMessage(), e);
            historicoSincronizacaoService.registrarFalhaSincronizacao(hashStr, "Erro inesperado: " + e.getMessage());
        }
    }

}