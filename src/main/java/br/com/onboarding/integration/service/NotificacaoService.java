package br.com.onboarding.integration.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.onboarding.infraestructure.messaging.broker.IMessageBroker;
import br.com.onboarding.infraestructure.messaging.broker.MessageTopic;
import br.com.onboarding.integration.dto.NotificacaoDTO;
import br.com.onboarding.integration.dto.OnboardingDataDto;
import br.com.onboarding.integration.model.Notificacao;
import br.com.onboarding.integration.repository.NotificacaoRepository;

@Service
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final IMessageBroker messageBroker;
    
    public NotificacaoService(NotificacaoRepository notificacaoRepository, IMessageBroker messageBroker) {
        this.notificacaoRepository = notificacaoRepository;
        this.messageBroker = messageBroker;
        this.messageBroker.subscribe(MessageTopic.NOTIFICACAO_RECEBIDA, this::criarNotificacao);
        this.messageBroker.subscribe(MessageTopic.DADOS_OBTIDOS, this::atualizarNotificacao);
    } 

    private void criarNotificacao(Object hash) {
        // Verifica se já existe uma notificação com o mesmo hash
        if (notificacaoRepository.findByHash(hash.toString()).isPresent()) {
            throw new IllegalArgumentException("Já existe uma notificação com o hash fornecido.");
        }
        // Cria uma nova instância de Notificacao
        Notificacao notificacao = new Notificacao();
        notificacao.setHash(hash.toString());
        // Salva a notificação no banco de dados
        notificacaoRepository.save(notificacao);
        this.messageBroker.publish(MessageTopic.DADOS_PENDENTES, hash);
    }

    private void atualizarNotificacao(Object onboardingDataDto) {
        OnboardingDataDto dto = (OnboardingDataDto) onboardingDataDto;
        Optional<Notificacao> notificacaoOptional = notificacaoRepository.findByHash(dto.hash());
        if (notificacaoOptional.isPresent()) {
            Notificacao notificacao = notificacaoOptional.get();
            notificacao.setDataRecebimento(LocalDateTime.now());
            notificacaoRepository.save(notificacao);
        } else {
            throw new IllegalArgumentException("Notificação não encontrada com o hash fornecido.");
        }
    }

    public List<NotificacaoDTO> listarNotificacoesPendentes() {
        return notificacaoRepository
                .findByDataRecebimentoIsNull()
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