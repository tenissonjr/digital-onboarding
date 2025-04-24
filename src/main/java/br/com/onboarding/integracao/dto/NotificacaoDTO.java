package br.com.onboarding.integracao.dto;

import java.time.LocalDateTime;

import br.com.onboarding.integracao.model.Notificacao;

public record NotificacaoDTO(
    String hash,
    LocalDateTime dataNotificacao,
    LocalDateTime dataRecebimento)  {

    public NotificacaoDTO(Notificacao notificacao) {
        this(
            notificacao.getHash(),
            notificacao.getDataHoraNotificacao(),
            notificacao.getDataHoraSincronizacao()
        );
    }    
}
