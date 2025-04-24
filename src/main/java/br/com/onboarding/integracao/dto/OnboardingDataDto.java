package br.com.onboarding.integracao.dto;

import java.time.LocalDate;

public class OnboardingDataDto {
    private String hash;
    private String dadosOriginais;
    private String cpf;
    private String nome;
    private String nomeSocial;
    private LocalDate dataNascimento;
    private String nomeMae;
    private String numeroDocumento;
    private String paisOrigem;
    private String orgaoEmissor;
    private String uf;
    private LocalDate dataExpedicao;
    private LocalDate dataVencimento;
    private String fotoUsuario;
    private String documentoFrente;
    private String documentoVerso;



    
    // Getters and Setters
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeSocial() {
        return nomeSocial;
    }

    public void setNomeSocial(String nomeSocial) {
        this.nomeSocial = nomeSocial;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getPaisOrigem() {
        return paisOrigem;
    }

    public void setPaisOrigem(String paisOrigem) {
        this.paisOrigem = paisOrigem;
    }

    public String getOrgaoEmissor() {
        return orgaoEmissor;
    }

    public void setOrgaoEmissor(String orgaoEmissor) {
        this.orgaoEmissor = orgaoEmissor;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public LocalDate getDataExpedicao() {
        return dataExpedicao;
    }

    public void setDataExpedicao(LocalDate dataExpedicao) {
        this.dataExpedicao = dataExpedicao;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getFotoUsuario() {
        return fotoUsuario;
    }

    public void setFotoUsuario(String fotoUsuario) {
        this.fotoUsuario = fotoUsuario;
    }

    public String getDocumentoFrente() {
        return documentoFrente;
    }

    public void setDocumentoFrente(String documentoFrente) {
        this.documentoFrente = documentoFrente;
    }

    public String getDocumentoVerso() {
        return documentoVerso;
    }

    public void setDocumentoVerso(String documentoVerso) {
        this.documentoVerso = documentoVerso;
    }

    public String getDadosOriginais() {
        return dadosOriginais;
    }

    public void setDadosOriginais(String dadosOriginais) {
        this.dadosOriginais = dadosOriginais;
    }
}