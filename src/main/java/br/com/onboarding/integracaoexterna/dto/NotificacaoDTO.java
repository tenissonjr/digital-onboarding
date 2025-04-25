package br.com.onboarding.integracaoexterna.dto;

import java.time.LocalDateTime;

import br.com.onboarding.integracaoexterna.model.NotificacaoPreCadastroExterno;

public record NotificacaoDTO(
    String hash,
    LocalDateTime dataNotificacao,
    LocalDateTime dataRecebimento)  {

    public NotificacaoDTO(NotificacaoPreCadastroExterno notificacao) {
        this(
            notificacao.getHash(),
            notificacao.getDataHoraNotificacao(),
            notificacao.getDataHoraSincronizacao()
        );
    }    
}
