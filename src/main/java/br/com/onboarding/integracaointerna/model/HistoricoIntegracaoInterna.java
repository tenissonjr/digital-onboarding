package br.com.onboarding.integracaointerna.model;


import java.time.LocalDateTime;

import br.com.onboarding.integracaointerna.enumeration.SituacaoIntegracaoInternaEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "HISTORICO_INTEGRACAO_INTERNA")
public class HistoricoIntegracaoInterna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String hash;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoIntegracaoInternaEnum situacao;

    @Column(name = "des_destino", length = 60)
    private String destino;


    @Column(name = "mensagem_erro", length = 1000)
    private String mensagemErro;


    @PrePersist
    protected void onCreate() {
        dataHora = LocalDateTime.now();
    }

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

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }


    public String getMensagemErro() {
        return mensagemErro;
    }

    public void setMensagemErro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }

    public SituacaoIntegracaoInternaEnum getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoIntegracaoInternaEnum situacao) {
        this.situacao = situacao;
    }

    public String getDestino() {
        return destino;
    }
    public void setDestino(String destino) {
        this.destino = destino;
    }
}