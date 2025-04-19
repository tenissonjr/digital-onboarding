package br.com.onboarding.integration.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.onboarding.infraestructure.messaging.broker.IMessageBroker;
import br.com.onboarding.infraestructure.messaging.broker.MessageTopic;
import br.com.onboarding.integration.model.NotificacaoOnboarding;
import br.com.onboarding.integration.repository.NotificacaoOnboardingRepository;

@Service
public class NotificacaoOnboardingService {


    private final NotificacaoOnboardingRepository notificacaoOnboardingRepository;
    private final IMessageBroker messageBroker;

    
    public NotificacaoOnboardingService(IMessageBroker messageBroker,NotificacaoOnboardingRepository notificacaoOnboardingRepository) {
        this.notificacaoOnboardingRepository = notificacaoOnboardingRepository;
        this.messageBroker = messageBroker;
        this.messageBroker.subscribe(MessageTopic.NOTIFICACAO_RECEBIDA, this::criarNotificacao);
        this.messageBroker.subscribe(MessageTopic.DADOS_OBTIDOS, this::atualizarNotificacao);
    } 

    private void criarNotificacao(Object hash) {
        // Verifica se já existe uma notificação com o mesmo hash
        if (notificacaoOnboardingRepository.findByHash(hash.toString()).isPresent()) {
            throw new IllegalArgumentException("Já existe uma notificação com o hash fornecido.");
        }

        // Cria uma nova instância de NotificacaoOnboarding
        NotificacaoOnboarding notificacao = new NotificacaoOnboarding();
        notificacao.setHash(hash.toString());


        // Salva a notificação no banco de dados
        notificacaoOnboardingRepository.save(notificacao);


        this.messageBroker.publish(MessageTopic.DADOS_PENDENTES, hash);


    }

    private void atualizarNotificacao(Object hash) {
        Optional<NotificacaoOnboarding> notificacaoOptional = notificacaoOnboardingRepository.findByHash(hash.toString());
        if (notificacaoOptional.isPresent()) {
            NotificacaoOnboarding notificacao = notificacaoOptional.get();
            notificacao.setDataRecebimento(LocalDateTime.now());
            notificacaoOnboardingRepository.save(notificacao);
        } else {
            throw new IllegalArgumentException("Notificação não encontrada com o hash fornecido.");
        }
    }
}