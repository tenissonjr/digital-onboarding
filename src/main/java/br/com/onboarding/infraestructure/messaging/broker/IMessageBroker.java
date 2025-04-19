package br.com.onboarding.infraestructure.messaging.broker;

import java.util.function.Consumer;

public interface IMessageBroker {
    void publish(MessageTopic topic, Object message);
    void subscribe(MessageTopic topic, Consumer<Object> consumer);
}