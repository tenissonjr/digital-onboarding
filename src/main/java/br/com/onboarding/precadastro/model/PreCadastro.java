package br.com.onboarding.precadastro.model;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.onboarding.integration.dto.OnboardingDataDto;
import br.com.onboarding.precadastro.enumeration.SituacaoPreCadastro;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "PRECADASTRO")
public class PreCadastro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String hash;

    @Column(length = 14)
    private String cpf;

    @Column(length = 200)
    private String nome;

    @Column(name = "nome_social", length = 200)
    private String nomeSocial;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "nome_mae", length = 200)
    private String nomeMae;

    @Column(name = "numero_documento", length = 30)
    private String numeroDocumento;

    @Column(name = "pais_origem", length = 100)
    private String paisOrigem;

    @Column(name = "orgao_emissor", length = 10)
    private String orgaoEmissor;

    @Column(length = 2)
    private String uf;

    @Column(name = "data_expedicao")
    private LocalDate dataExpedicao;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoPreCadastro situacao;

    @Column(name = "data_cadastro", nullable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "data_validacao")
    private LocalDateTime dataValidacao;
    
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "onboarding_data_id")
    private List<ValidacaoPreCadastro> validationErrors = new ArrayList<>();

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

    public SituacaoPreCadastro getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoPreCadastro status) {
        this.situacao = status;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataRecebimento) {
        this.dataCadastro = dataRecebimento;
    }


    public List<ValidacaoPreCadastro> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(List<ValidacaoPreCadastro> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public LocalDateTime getDataValidacao() {
        return dataValidacao;
    }

    public void setDataValidacao(LocalDateTime dataValidacao) {
        this.dataValidacao = dataValidacao;
    }

    
    public static PreCadastro valueOf(OnboardingDataDto dto) {
        PreCadastro preCadastro = new PreCadastro();
        preCadastro.setHash(dto.hash());
        preCadastro.setCpf(dto.cpf());
        preCadastro.setNome(dto.nome());
        preCadastro.setNomeSocial(dto.nomeSocial());
        preCadastro.setDataNascimento(dto.dataNascimento());
        preCadastro.setNomeMae(dto.nomeMae());
        preCadastro.setNumeroDocumento(dto.numeroDocumento());
        preCadastro.setPaisOrigem(dto.paisOrigem());
        preCadastro.setOrgaoEmissor(dto.orgaoEmissor());
        preCadastro.setUf(dto.uf());
        preCadastro.setDataExpedicao(dto.dataExpedicao());
        preCadastro.setDataVencimento(dto.dataVencimento());
        preCadastro.setDataCadastro(LocalDateTime.now());
        preCadastro.setSituacao(SituacaoPreCadastro.PENDENTE_VALIDACAO);


        return  preCadastro;
    }


    
}