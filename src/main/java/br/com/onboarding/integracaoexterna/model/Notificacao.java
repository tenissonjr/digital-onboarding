package br.com.onboarding.integracaoexterna.model;

import java.time.LocalDateTime;

import br.com.onboarding.integracaoexterna.enumeration.SituacaoSincronizacaoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "NOTIFICACAO_ONBOARDING")
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String hash;
    
    @Column(name = "tim_notificacao")
    private LocalDateTime dataHoraNotificacao;

    @Column(name = "tim_sincronizacao", nullable = false)
    private LocalDateTime dataHoraSincronizacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoSincronizacaoEnum situacaoAtual= SituacaoSincronizacaoEnum.PENDENTE_SINCRONIZACAO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }


    public void setHash(String hash) {
        this.hash = hash;
    }


    public LocalDateTime getDataHoraNotificacao() {
        return dataHoraNotificacao;
    }


    public void setDataHoraNotificacao(LocalDateTime dataNotificacao) {
        this.dataHoraNotificacao = dataNotificacao;
    }


    public LocalDateTime getDataHoraSincronizacao() {
        return dataHoraSincronizacao;
    }


    public void setDataHoraSincronizacao(LocalDateTime dataRecebimento) {
        this.dataHoraSincronizacao = dataRecebimento;
    }

    public SituacaoSincronizacaoEnum getSituacaoAtual() {
        return situacaoAtual;
    }

    public void setSituacaoAtual(SituacaoSincronizacaoEnum status) {
        this.situacaoAtual = status;
    }


    
}