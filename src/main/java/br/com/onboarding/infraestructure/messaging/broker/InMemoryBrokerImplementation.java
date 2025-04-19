package br.com.onboarding.infraestructure.messaging.broker;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;


public class InMemoryBrokerImplementation implements IMessageBroker {
    private final ConcurrentHashMap<MessageTopic, CopyOnWriteArrayList<Consumer<Object>>> subscribers = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public void publish(MessageTopic topic, Object message) {
        var consumers = subscribers.getOrDefault(topic, new CopyOnWriteArrayList<>());
        for (var consumer : consumers) {
            executor.submit(() -> consumer.accept(message));
        }
    }

    @Override
    public void subscribe(MessageTopic topic, Consumer<Object> consumer) {
        subscribers.computeIfAbsent(topic, k -> new CopyOnWriteArrayList<>()).add(consumer);
    }
}