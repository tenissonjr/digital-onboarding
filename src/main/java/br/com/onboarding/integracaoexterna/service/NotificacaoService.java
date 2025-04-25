package br.com.onboarding.integracaoexterna.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.onboarding.infraestructure.messaging.broker.IMessageBroker;
import br.com.onboarding.infraestructure.messaging.broker.MessageTopic;
import br.com.onboarding.integracaoexterna.dto.NotificacaoDTO;
import br.com.onboarding.integracaoexterna.enumeration.SituacaoSincronizacaoEnum;
import br.com.onboarding.integracaoexterna.model.NotificacaoPreCadastroExterno;
import br.com.onboarding.integracaoexterna.repository.NotificacaoPreCadastroExternoRepository;

@Service
public class NotificacaoService {

    private final IMessageBroker messageBroker;
    private final NotificacaoPreCadastroExternoRepository notificacaoRepository;
    private final HistoricoSincronizacaoService historicoSincronizacaoService;
    
    public NotificacaoService( IMessageBroker messageBroker,NotificacaoPreCadastroExternoRepository notificacaoRepository,HistoricoSincronizacaoService historicoSincronizacaoService) {
        this.messageBroker = messageBroker;
        this.notificacaoRepository = notificacaoRepository;
        this.historicoSincronizacaoService = historicoSincronizacaoService;
        // Inscreve-se nos tópicos relevantes para receber mensagens
        this.messageBroker.subscribe(MessageTopic.NOTIFICACAO_RECEBIDA, this::criarNotificacao);
        this.messageBroker.subscribe(MessageTopic.DADOS_SINCRONIZADOS, this::atualizarNotificacaoSincronizada);

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


        this.messageBroker.publish(MessageTopic.DADOS_PENDENTES, hash);
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
            messageBroker.publish(MessageTopic.DADOS_PENDENTES, notificacao.hash());
        }
    }
}