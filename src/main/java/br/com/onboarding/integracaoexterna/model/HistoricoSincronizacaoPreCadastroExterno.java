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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "HISTORICO_SINCRONIZACAO_PRECADASTRO_EXTERNO")
public class HistoricoSincronizacaoPreCadastroExterno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String hash;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoSincronizacaoEnum situacao;

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

    public SituacaoSincronizacaoEnum getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoSincronizacaoEnum situacao) {
        this.situacao = situacao;
    }

    
}