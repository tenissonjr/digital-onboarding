package br.com.onboarding.integracaoexterna.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.onboarding.infraestructure.messaging.broker.IMessageBroker;
import br.com.onboarding.infraestructure.messaging.broker.MessageTopic;
import br.com.onboarding.integracaoexterna.dto.EstatisticasDTO;
import br.com.onboarding.integracaoexterna.dto.NoticacaoParamDTO;
import br.com.onboarding.integracaoexterna.dto.NotificacaoDTO;
import br.com.onboarding.integracaoexterna.service.EstatisticasNotificacaoService;
import br.com.onboarding.integracaoexterna.service.IntegracaoExternaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/onboarding/notificacoes")
public class NotificacaoController {

    private static final Logger log = LoggerFactory.getLogger(NotificacaoController.class);
    
    private final IMessageBroker messageBroker;
    private final IntegracaoExternaService notificacaoService;
    private final EstatisticasNotificacaoService estatisticasNotificacaoService;


    public NotificacaoController(IMessageBroker messageBroker, IntegracaoExternaService notificacaoService, EstatisticasNotificacaoService estatisticasNotificacaoService) {
        this.notificacaoService = notificacaoService;
        this.messageBroker = messageBroker;
        this.estatisticasNotificacaoService = estatisticasNotificacaoService;
    }

    @PostMapping
    public ResponseEntity<Void> receberNotificacao(@Valid @RequestBody NoticacaoParamDTO notification) {

        log.info("Recebida notificação com hash =" + notification.hash());
        messageBroker.publish(MessageTopic.NOTIFICACAO_PRECADASTRO_EXTERNO, notification.hash());

        return ResponseEntity.status(HttpStatus.ACCEPTED).build(); 
    }  

    @GetMapping
    public ResponseEntity<List<NotificacaoDTO>> listarNoticacoes() {
        return ResponseEntity.ok(notificacaoService.listarNotificacoes()); 
    }  


    @GetMapping("/pendentes")
    public ResponseEntity<List<NotificacaoDTO>> listarNoticacoesPendentes() {
        return ResponseEntity.ok(notificacaoService.listarNotificacoesPendentes()); 
    }  

    @GetMapping("/pendentes/processar")
    public ResponseEntity<Void> processarNotificacoesPendentes() {
        notificacaoService.processarNotificacoesPendentes();
        return ResponseEntity.status(HttpStatus.ACCEPTED).build(); 
    }  

    @PostMapping("/estatisticas")
    public ResponseEntity<EstatisticasDTO> gerarEstatisticas(@RequestBody br.com.onboarding.integracaoexterna.dto.EstatisticasParamDTO estatisticasParam) {
        return ResponseEntity.ok(estatisticasNotificacaoService.gerarEstatisticar(estatisticasParam));
    }

        
}
