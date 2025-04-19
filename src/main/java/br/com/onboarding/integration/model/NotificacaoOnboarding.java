package br.com.onboarding.integration.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "NOTIFICACAO_ONBOARDING")
public class NotificacaoOnboarding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String hash;

    
    @Column(name = "data_notificacao")
    private LocalDateTime dataNotificacao;

    @Column(name = "data_recebimento", nullable = false)
    private LocalDateTime dataRecebimento;


    @PrePersist
    protected void onCreate() {
       setDataNotificacao( LocalDateTime.now());
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


    public LocalDateTime getDataNotificacao() {
        return dataNotificacao;
    }


    public void setDataNotificacao(LocalDateTime dataNotificacao) {
        this.dataNotificacao = dataNotificacao;
    }


    public LocalDateTime getDataRecebimento() {
        return dataRecebimento;
    }


    public void setDataRecebimento(LocalDateTime dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }


    
}