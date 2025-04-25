package br.com.onboarding.integracaoexterna.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.onboarding.infraestructure.exception.IntegracaoPreCadastroExternoException;
import br.com.onboarding.infraestructure.messaging.broker.IMessageBroker;
import br.com.onboarding.infraestructure.messaging.broker.MessageTopic;
import br.com.onboarding.integracaoexterna.dto.NotificacaoDTO;
import br.com.onboarding.integracaoexterna.enumeration.SituacaoSincronizacaoEnum;
import br.com.onboarding.integracaoexterna.model.NotificacaoPreCadastroExterno;
import br.com.onboarding.integracaoexterna.port.IIntegracaoPreCadastroExternoService;
import br.com.onboarding.integracaoexterna.port.IPreCadastroExterno;
import br.com.onboarding.integracaoexterna.repository.NotificacaoPreCadastroExternoRepository;
import br.com.onboarding.shared.dto.ErroSincronizacaoDTO;

@Service
public class IntegracaoExternaService {

    private final IMessageBroker messageBroker;
    private final NotificacaoPreCadastroExternoRepository notificacaoRepository;
    private final HistoricoSincronizacaoService historicoSincronizacaoService;
    private final IIntegracaoPreCadastroExternoService integracaoPreCadastroExternoService;
    
    private static final Logger log = LoggerFactory.getLogger(IntegracaoExternaService.class);

    public IntegracaoExternaService( IMessageBroker messageBroker,NotificacaoPreCadastroExternoRepository notificacaoRepository,HistoricoSincronizacaoService historicoSincronizacaoService
    , IIntegracaoPreCadastroExternoService integracaoPreCadastroExternoService) {
        this.messageBroker = messageBroker;
        this.notificacaoRepository = notificacaoRepository;
        this.historicoSincronizacaoService = historicoSincronizacaoService;
        this.integracaoPreCadastroExternoService = integracaoPreCadastroExternoService;
        // Inscreve-se nos tópicos relevantes para receber mensagens
        this.messageBroker.subscribe(MessageTopic.NOTIFICACAO_PRECADASTRO_EXTERNO, this::criarNotificacao);
        this.messageBroker.subscribe(MessageTopic.DADOS_PRECADASTRO_EXTERNO_PENDENTE_SINCRONIZACAO, this::obterDadosOnboarding);
        this.messageBroker.subscribe(MessageTopic.DADOS_PRE_CADASTRO_EXTERNO_SINCRONIZADOS, this::atualizarNotificacaoSincronizada);
        this.messageBroker.subscribe(MessageTopic.FALHA_SINCRONIZACAO_PRECADASTRO_EXTERNO, this::registrarFalhaSincronizacao);
    } 

    private void criarNotificacao(Object hash) {
        // Verifica se já existe uma notificação com o mesmo hash
        if (notificacaoRepository.findByHash(hash.toString()).isPresent()) {
            throw new IllegalArgumentException("Já existe uma notificação com o hash fornecido.");
        }
        LocalDateTime dataNotificacao = LocalDateTime.now();
        // Cria uma nova instância de Notificacao
        NotificacaoPreCadastroExterno notificacao = new NotificacaoPreCadastroExterno();
        notificacao.setHash(hash.toString());
        notificacao.setDataHoraNotificacao(dataNotificacao);
        // Salva a notificação no banco de dados
        notificacaoRepository.save(notificacao);

        // Cria um histórico de sincronização
        historicoSincronizacaoService.registrarPendenciaSincronizacao(hash.toString());


        this.messageBroker.publish(MessageTopic.DADOS_PRECADASTRO_EXTERNO_PENDENTE_SINCRONIZACAO, hash);
    }

    private void atualizarNotificacaoSincronizada(Object hash) {
        Optional<NotificacaoPreCadastroExterno> notificacaoOptional = notificacaoRepository.findByHash(hash.toString());
        if (notificacaoOptional.isPresent()) {
            NotificacaoPreCadastroExterno notificacao = notificacaoOptional.get();
            notificacao.setSituacaoAtual(SituacaoSincronizacaoEnum.SUCESSO_SINCRONIZACAO);
            notificacao.setDataHoraSincronizacao(LocalDateTime.now());
            notificacaoRepository.save(notificacao);

            // Registra o histórico de sincronização
            historicoSincronizacaoService.registrarSucessoSincronizacao(hash.toString());

        } else {
            throw new IllegalArgumentException("Notificação não encontrada com o hash fornecido.");
        }
    }

    public List<NotificacaoDTO> listarNotificacoesPendentes() {
        return notificacaoRepository
                .findByDataHoraSincronizacaoIsNull()
                .stream()
                .map(NotificacaoDTO::new)
                .toList();
    }

    public List<NotificacaoDTO> listarNotificacoes() {
        return notificacaoRepository
                .findAll()
                .stream()
                .map(NotificacaoDTO::new)
                .toList();
    }


    public void processarNotificacoesPendentes() {
        List<NotificacaoDTO> notificacoesPendentes = listarNotificacoesPendentes();
        for (NotificacaoDTO notificacao : notificacoesPendentes) {
            messageBroker.publish(MessageTopic.DADOS_PRECADASTRO_EXTERNO_PENDENTE_SINCRONIZACAO, notificacao.hash());
        }
    }

    @Transactional
    public void obterDadosOnboarding(Object hash) {
        String hashStr = hash.toString();
        log.info("Iniciando obtenção de dados de onboarding para o hash: {}", hashStr);
        try {
            IPreCadastroExterno dto = integracaoPreCadastroExternoService.obterPreCadastroExterno(hash);
            this.messageBroker.publish(MessageTopic.DADOS_PRE_CADASTRO_EXTERNO_OBTIDOS, dto);
        } catch (IntegracaoPreCadastroExternoException e) {
            log.error("Erro ao obter dados de onboarding para o hash {}: {}", hashStr, e.getMessage());
            historicoSincronizacaoService.registrarFalhaSincronizacao(hashStr, e.getMessage());
        } catch (Exception e) {
            log.error("Erro inesperado ao obter dados de onboarding para o hash {}: {}", hashStr, e.getMessage(), e);
            historicoSincronizacaoService.registrarFalhaSincronizacao(hashStr, "Erro inesperado: " + e.getMessage());
        }
    }    

    private void registrarFalhaSincronizacao(Object erroSincronizacaoObject) {
        ErroSincronizacaoDTO erroSincronizacaoDTO = (ErroSincronizacaoDTO) erroSincronizacaoObject;
        historicoSincronizacaoService.registrarFalhaSincronizacao(erroSincronizacaoDTO.hash(), erroSincronizacaoDTO.erro());
    }
}