package br.com.onboarding.integracao.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.onboarding.infraestructure.messaging.broker.IMessageBroker;
import br.com.onboarding.infraestructure.messaging.broker.MessageTopic;
import br.com.onboarding.integracao.dto.NotificacaoDTO;
import br.com.onboarding.integracao.dto.OnboardingDataDto;
import br.com.onboarding.integracao.enumeration.SituacaoSincronizacaoEnum;
import br.com.onboarding.integracao.model.HistoricoSincronizacao;
import br.com.onboarding.integracao.model.Notificacao;
import br.com.onboarding.integracao.repository.HistoricoSincronizacaoRepository;
import br.com.onboarding.integracao.repository.NotificacaoRepository;

@Service
public class NotificacaoService {

    private final IMessageBroker messageBroker;
    private final NotificacaoRepository notificacaoRepository;
    private final HistoricoSincronizacaoRepository historicoSincronizacaoRepository;
    
    public NotificacaoService( IMessageBroker messageBroker,NotificacaoRepository notificacaoRepository,HistoricoSincronizacaoRepository historicoSincronizacaoRepository) {
        this.messageBroker = messageBroker;
        this.notificacaoRepository = notificacaoRepository;
        this.historicoSincronizacaoRepository = historicoSincronizacaoRepository;
        // Inscreve-se nos tópicos relevantes para receber mensagens
        this.messageBroker.subscribe(MessageTopic.NOTIFICACAO_RECEBIDA, this::criarNotificacao);
        this.messageBroker.subscribe(MessageTopic.DADOS_OBTIDOS, this::atualizarNotificacao);
    } 

    private void criarNotificacao(Object hash) {
        // Verifica se já existe uma notificação com o mesmo hash
        if (notificacaoRepository.findByHash(hash.toString()).isPresent()) {
            throw new IllegalArgumentException("Já existe uma notificação com o hash fornecido.");
        }
        LocalDateTime dataNotificacao = LocalDateTime.now();
        // Cria uma nova instância de Notificacao
        Notificacao notificacao = new Notificacao();
        notificacao.setHash(hash.toString());
        notificacao.setDataHoraNotificacao(dataNotificacao);
        // Salva a notificação no banco de dados
        notificacaoRepository.save(notificacao);

        // Cria um histórico de sincronização
        HistoricoSincronizacao historico = new HistoricoSincronizacao();
        historico.setHash(hash.toString());
        historico.setDataHora(dataNotificacao);
        historico.setSituacao(SituacaoSincronizacaoEnum.PENDENTE_SINCRONIZACAO);
        this.historicoSincronizacaoRepository.save(historico);


        this.messageBroker.publish(MessageTopic.DADOS_PENDENTES, hash);
    }

    private void atualizarNotificacao(Object onboardingDataDto) {
        OnboardingDataDto dto = (OnboardingDataDto) onboardingDataDto;
        Optional<Notificacao> notificacaoOptional = notificacaoRepository.findByHash(dto.getHash());
        if (notificacaoOptional.isPresent()) {
            Notificacao notificacao = notificacaoOptional.get();
            notificacao.setSituacaoAtual(SituacaoSincronizacaoEnum.SUCESSO_SINCRONIZACAO);
            notificacao.setDataHoraSincronizacao(LocalDateTime.now());
            notificacaoRepository.save(notificacao);
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