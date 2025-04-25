package br.com.onboarding.infraestructure.messaging.broker;

public enum MessageTopic {
    NOTIFICACAO_PRECADASTRO_EXTERNO("notificacao-recebida"),
    DADOS_PRECADASTRO_EXTERNO_PENDENTE_SINCRONIZACAO("dados-pendentes"),
    DADOS_PRE_CADASTRO_EXTERNO_OBTIDOS("dados-obtidos") ,
    FALHA_SINCRONIZACAO_PRECADASTRO_EXTERNO("falha-sincronizacao"),
    DADOS_PRE_CADASTRO_EXTERNO_SINCRONIZADOS("dados-sincronizados"),;

    private final String topicName;

    MessageTopic(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicName() {
        return topicName;
    }
}
