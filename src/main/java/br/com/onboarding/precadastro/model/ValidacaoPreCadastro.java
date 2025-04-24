package br.com.onboarding.precadastro.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;


@Entity
@Table(name = "VALIDACAO_PRECADASTRO")
public class ValidacaoPreCadastro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pre_cadastro", nullable = false)
    private PreCadastro preCadastro;

    @Column(name = "tim_validacao", nullable = false)
    private LocalDateTime dataHoraValidacao;

    @Column(nullable = false)
    private String campo;

    @Column(nullable = false, length = 500)
    private String mensagem;
    

    @PrePersist
    protected void onCreate() {
        if (dataHoraValidacao == null) {
            dataHoraValidacao = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PreCadastro getPreCadastro() {
        return preCadastro;
    }

    public void setPreCadastro(PreCadastro onboardingData) {
        this.preCadastro = onboardingData;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getDataHoraValidacao() {
        return dataHoraValidacao;
    }

    public void setDataHoraValidacao(LocalDateTime dataRegistro) {
        this.dataHoraValidacao = dataRegistro;
    }

    
}