package br.com.onboarding.integration.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.onboarding.infraestructure.messaging.broker.IMessageBroker;
import br.com.onboarding.infraestructure.messaging.broker.MessageTopic;
import br.com.onboarding.integration.dto.NotificationDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/onboarding")
public class NotificationController {

    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);
    
    private final IMessageBroker messageBroker;

    public NotificationController(IMessageBroker messageBroker) {
        this.messageBroker = messageBroker;
    }

    @PostMapping("/notification")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void receiveNotification(@Valid @RequestBody NotificationDTO notification) {

        log.info("Received notification with hash: " + notification.hash());

        messageBroker.publish(MessageTopic.NOTIFICACAO_RECEBIDA, notification.hash());

    }  
}
