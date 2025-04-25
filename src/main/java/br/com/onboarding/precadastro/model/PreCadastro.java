package br.com.onboarding.precadastro.model;


import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.onboarding.integracao.dto.valid.ValidCaptureItemReportDTO;
import br.com.onboarding.integracao.dto.valid.ValidCapturesReportDTO;
import br.com.onboarding.integracao.dto.valid.ValidOcrDocumentReportDTO;
import br.com.onboarding.integracao.dto.valid.ValidOnboardingDataDTO;
import br.com.onboarding.precadastro.enumeration.SituacaoPreCadastro;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PRECADASTRO")
public class PreCadastro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String hash;


    @Column(name = "num_cpf",length = 14)
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

    @Column(name = "jso_dados_originais")
    private String dadosOriginais;
    

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

    public String getDadosOriginais() {
        return dadosOriginais;
    }

    public void setDadosOriginais(String dadosOriginais) {
        this.dadosOriginais = dadosOriginais;
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

    public LocalDateTime getDataValidacao() {
        return dataValidacao;
    }

    public void setDataValidacao(LocalDateTime dataValidacao) {
        this.dataValidacao = dataValidacao;
    }

    

    
    public static PreCadastro valueOf(ValidOnboardingDataDTO validDto) {
        PreCadastro preCadastro = new PreCadastro();
        preCadastro.setHash(validDto.getHash());
        preCadastro.setDadosOriginais(validDto.getDadosOriginais());

        if (validDto.getCapturesReport()==null ||validDto.getCapturesReport().isEmpty()) {   
            return preCadastro;
        }
        ValidCapturesReportDTO captureReportProvaVida = validDto.getCapturesReport().get(0);
        ValidCaptureItemReportDTO captureItemReportFace = captureReportProvaVida.getCaptureItemReport().get(0); 

        ValidCapturesReportDTO captureReportDocumentos = validDto.getCapturesReport().get(1);
        ValidCaptureItemReportDTO captureItemReportDocumentos = captureReportDocumentos.getCaptureItemReport().get(0);         
        ValidOcrDocumentReportDTO dadosDocumento= captureItemReportDocumentos.getOcrDocumentReport();

        preCadastro.setCpf(dadosDocumento.getCpf());
        preCadastro.setNome(dadosDocumento.getDocumentName());

        preCadastro.setDataNascimento(dadosDocumento.getData_de_nascimento());
        preCadastro.setNomeMae(dadosDocumento.getFiliacao1());
        preCadastro.setNumeroDocumento(dadosDocumento.getNumero_da_CNH());
        /* 
        preCadastro.setPaisOrigem(dto.getPaisOrigem());
        preCadastro.setOrgaoEmissor(dto.getOrgaoEmissor());
        preCadastro.setUf(dto.getUf());
         */

         preCadastro.setDataExpedicao(dadosDocumento.getData_de_expedicao());
         preCadastro.setDataVencimento(dadosDocumento.getData_de_validade());
 

        preCadastro.setDataCadastro(LocalDateTime.now());
        preCadastro.setSituacao(SituacaoPreCadastro.PENDENTE_VALIDACAO);


        return  preCadastro;
    }

    public PreCadastro registrarSituacaoSucessoValidacao() {
        setSituacao(SituacaoPreCadastro.VALIDADO_SUCESSO);
        setDataValidacao(LocalDateTime.now());
        return this;
    }

    public PreCadastro registrarSituacaoErroValidacao() {
        setSituacao(SituacaoPreCadastro.VALIDADO_COM_ERROS);
        setDataValidacao(LocalDateTime.now());
        return this;
    }



    
}