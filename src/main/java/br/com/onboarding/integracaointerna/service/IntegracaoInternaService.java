package br.com.onboarding.integracaointerna.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.onboarding.infraestructure.messaging.broker.IMessageBroker;
import br.com.onboarding.infraestructure.messaging.broker.MessageTopic;
import br.com.onboarding.integracaointerna.enumeration.SituacaoIntegracaoInternaEnum;
import br.com.onboarding.integracaointerna.model.HistoricoIntegracaoInterna;
import br.com.onboarding.integracaointerna.port.IIntegracaoInternaPreCadastroService;
import br.com.onboarding.integracaointerna.repository.HistoricoIntegracaoInternaRepository;
import br.com.onboarding.precadastro.model.PreCadastro;

@Service
public class IntegracaoInternaService {

    private static final Logger log = LoggerFactory.getLogger(IntegracaoInternaService.class);

    private final IMessageBroker messageBroker;
    private final List<IIntegracaoInternaPreCadastroService> integracoesInternas ;
    private final HistoricoIntegracaoInternaRepository historicoIntegracaoInternaRepository;

    public IntegracaoInternaService(IMessageBroker messageBroker 
    , List<IIntegracaoInternaPreCadastroService> integracoesInternas
    , HistoricoIntegracaoInternaRepository historicoIntegracaoInternaRepository) {
        this.messageBroker = messageBroker;
        this.integracoesInternas = integracoesInternas;
        this.historicoIntegracaoInternaRepository = historicoIntegracaoInternaRepository;
        this.messageBroker.subscribe(MessageTopic.DADOS_PRE_CADASTRO_EXTERNO_SINCRONIZADOS, this::efetuarIntegracoesInternas);
    }

    void efetuarIntegracoesInternas(Object preCadastroObject) {
        if(this.integracoesInternas==null ||this.integracoesInternas.isEmpty()) {
            return;
        }
        PreCadastro preCadastro = (PreCadastro) preCadastroObject;

        for (IIntegracaoInternaPreCadastroService integracao : this.integracoesInternas) {
            try {
                integracao.sincronizarPreCadastro(preCadastro);
                registrarSucessoIntegracao(integracao.getDestino(), preCadastro);
            } catch (Exception e) {
                log.error("Erro ao sincronizar com {}: {}", integracao.getDestino(), e.getMessage());
                registrarFalhaIntegracao(integracao.getDestino(), preCadastro, e);
            }
        }
    
    }

    private void registrarSucessoIntegracao(String destino, PreCadastro preCadastro) {
        HistoricoIntegracaoInterna historico = new HistoricoIntegracaoInterna();
        historico.setHash(preCadastro.getHash());
        historico.setDestino(destino);
        historico.setSituacao(SituacaoIntegracaoInternaEnum.SUCESSO_SINCRONIZACAO);
        historico.setDataHora(LocalDateTime.now());
        historicoIntegracaoInternaRepository.save(historico);
    }

    private void registrarFalhaIntegracao(String destino, PreCadastro preCadastro, Exception e) {
        HistoricoIntegracaoInterna historico = new HistoricoIntegracaoInterna();
        historico.setHash(preCadastro.getHash());
        historico.setDestino(destino);
        historico.setSituacao(SituacaoIntegracaoInternaEnum.FALHA_SINCRONIZACAO);
        historico.setDataHora(LocalDateTime.now());
        historico.setMensagemErro(e.getMessage());
        historicoIntegracaoInternaRepository.save(historico);
    }

}
