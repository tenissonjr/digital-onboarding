package br.com.onboarding.infraestructure.messaging.broker;

public enum MessageTopic {
    NOTIFICACAO_RECEBIDA("notificacao-recebida"),
    DADOS_PENDENTES("dados-pendentes"),
    DADOS_OBTIDOS("dados-obtidos") ,
    DADOS_SINCRONIZADOS("dados-sincronizados");

    private final String topicName;

    MessageTopic(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }
}
