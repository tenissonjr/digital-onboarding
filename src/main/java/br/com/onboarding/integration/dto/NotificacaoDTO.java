package br.com.onboarding.integration.dto;

import java.time.LocalDateTime;

import br.com.onboarding.integration.model.Notificacao;

public record NotificacaoDTO(
    String hash,
    LocalDateTime dataNotificacao,
    LocalDateTime dataRecebimento)  {

    public NotificacaoDTO(Notificacao notificacao) {
        this(
            notificacao.getHash(),
            notificacao.getDataNotificacao(),
            notificacao.getDataRecebimento()
        );
    }    
}
