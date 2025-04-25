package br.com.onboarding.infraestructure.adapter.valid.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ValidOcrDocumentReportDTO {

    private Long id;
    private String faceUrl;
    private String docType;
    private String documentName;
    private String rg;
    private String cpf;
    private String filiacao1;
    private String filiacao2;
    private String orgao_emissor_do_RG;
    private String estado_emissor_do_RG;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate data_de_validade;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate data_de_nascimento;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate data_de_expedicao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate data_primeira_Habilitacao;
    private String local_de_emissao_da_CNH;
    private String numero_da_CNH;
    private String categoria_da_CNH;
    private String numero_do_Renach_na_CNH;
    private String campo_de_Observacao_na_CNH;
    private String informativo_da_observação_da_CNH;
    private String orgao_emissor_da_CNH;
    private String pais;
    private String numero_passaporte;
    private String numero_CTPS;
    private String numero_Serie_CTPS;
    private String numero_PisPasesp;
    private String estado_Civil;
    private String uF_CTPS;
    private String numero_Espelho_Frente;
    private String numero_Espelho_Verso;
    private String analfabeto;
    private String orgao_Emissor_Primario;
    private String orgao_Emissor_Secundario;
    private String naturalidade_Cidade;
    private String naturalidade_Uf;
    private String doc_origem;
    private String type;
    private String sexo;
    private String mrzCode;
    private String nacionalidade;
    private String orgao_Emissor_Registro_Migratorio;
    private String orgao_Emissor_Passaporte;

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getFiliacao1() {
        return filiacao1;
    }

    public void setFiliacao1(String filiacao1) {
        this.filiacao1 = filiacao1;
    }

    public String getFiliacao2() {
        return filiacao2;
    }

    public void setFiliacao2(String filiacao2) {
        this.filiacao2 = filiacao2;
    }

    public String getOrgao_emissor_do_RG() {
        return orgao_emissor_do_RG;
    }

    public void setOrgao_emissor_do_RG(String orgao_emissor_do_RG) {
        this.orgao_emissor_do_RG = orgao_emissor_do_RG;
    }

    public String getEstado_emissor_do_RG() {
        return estado_emissor_do_RG;
    }

    public void setEstado_emissor_do_RG(String estado_emissor_do_RG) {
        this.estado_emissor_do_RG = estado_emissor_do_RG;
    }

    public LocalDate getData_de_validade() {
        return data_de_validade;
    }

    public void setData_de_validade(LocalDate data_de_validade) {
        this.data_de_validade = data_de_validade;
    }

    public LocalDate getData_de_nascimento() {
        return data_de_nascimento;
    }

    public void setData_de_nascimento(LocalDate data_de_nascimento) {
        this.data_de_nascimento = data_de_nascimento;
    }

    public LocalDate getData_de_expedicao() {
        return data_de_expedicao;
    }

    public void setData_de_expedicao(LocalDate data_de_expedicao) {
        this.data_de_expedicao = data_de_expedicao;
    }

    public LocalDate getData_primeira_Habilitacao() {
        return data_primeira_Habilitacao;
    }

    public void setData_primeira_Habilitacao(LocalDate data_primeira_Habilitacao) {
        this.data_primeira_Habilitacao = data_primeira_Habilitacao;
    }

    public String getLocal_de_emissao_da_CNH() {
        return local_de_emissao_da_CNH;
    }

    public void setLocal_de_emissao_da_CNH(String local_de_emissao_da_CNH) {
        this.local_de_emissao_da_CNH = local_de_emissao_da_CNH;
    }

    public String getNumero_da_CNH() {
        return numero_da_CNH;
    }

    public void setNumero_da_CNH(String numero_da_CNH) {
        this.numero_da_CNH = numero_da_CNH;
    }

    public String getCategoria_da_CNH() {
        return categoria_da_CNH;
    }

    public void setCategoria_da_CNH(String categoria_da_CNH) {
        this.categoria_da_CNH = categoria_da_CNH;
    }

    public String getNumero_do_Renach_na_CNH() {
        return numero_do_Renach_na_CNH;
    }

    public void setNumero_do_Renach_na_CNH(String numero_do_Renach_na_CNH) {
        this.numero_do_Renach_na_CNH = numero_do_Renach_na_CNH;
    }

    public String getCampo_de_Observacao_na_CNH() {
        return campo_de_Observacao_na_CNH;
    }

    public void setCampo_de_Observacao_na_CNH(String campo_de_Observacao_na_CNH) {
        this.campo_de_Observacao_na_CNH = campo_de_Observacao_na_CNH;
    }

    public String getInformativo_da_observação_da_CNH() {
        return informativo_da_observação_da_CNH;
    }

    public void setInformativo_da_observação_da_CNH(String informativo_da_observação_da_CNH) {
        this.informativo_da_observação_da_CNH = informativo_da_observação_da_CNH;
    }

    public String getOrgao_emissor_da_CNH() {
        return orgao_emissor_da_CNH;
    }

    public void setOrgao_emissor_da_CNH(String orgao_emissor_da_CNH) {
        this.orgao_emissor_da_CNH = orgao_emissor_da_CNH;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getNumero_passaporte() {
        return numero_passaporte;
    }

    public void setNumero_passaporte(String numero_passaporte) {
        this.numero_passaporte = numero_passaporte;
    }

    public String getNumero_CTPS() {
        return numero_CTPS;
    }

    public void setNumero_CTPS(String numero_CTPS) {
        this.numero_CTPS = numero_CTPS;
    }

    public String getNumero_Serie_CTPS() {
        return numero_Serie_CTPS;
    }

    public void setNumero_Serie_CTPS(String numero_Serie_CTPS) {
        this.numero_Serie_CTPS = numero_Serie_CTPS;
    }

    public String getNumero_PisPasesp() {
        return numero_PisPasesp;
    }

    public void setNumero_PisPasesp(String numero_PisPasesp) {
        this.numero_PisPasesp = numero_PisPasesp;
    }

    public String getEstado_Civil() {
        return estado_Civil;
    }

    public void setEstado_Civil(String estado_Civil) {
        this.estado_Civil = estado_Civil;
    }

    public String getuF_CTPS() {
        return uF_CTPS;
    }

    public void setuF_CTPS(String uF_CTPS) {
        this.uF_CTPS = uF_CTPS;
    }

    public String getNumero_Espelho_Frente() {
        return numero_Espelho_Frente;
    }

    public void setNumero_Espelho_Frente(String numero_Espelho_Frente) {
        this.numero_Espelho_Frente = numero_Espelho_Frente;
    }

    public String getNumero_Espelho_Verso() {
        return numero_Espelho_Verso;
    }

    public void setNumero_Espelho_Verso(String numero_Espelho_Verso) {
        this.numero_Espelho_Verso = numero_Espelho_Verso;
    }

    public String getAnalfabeto() {
        return analfabeto;
    }

    public void setAnalfabeto(String analfabeto) {
        this.analfabeto = analfabeto;
    }

    public String getOrgao_Emissor_Primario() {
        return orgao_Emissor_Primario;
    }

    public void setOrgao_Emissor_Primario(String orgao_Emissor_Primario) {
        this.orgao_Emissor_Primario = orgao_Emissor_Primario;
    }

    public String getOrgao_Emissor_Secundario() {
        return orgao_Emissor_Secundario;
    }

    public void setOrgao_Emissor_Secundario(String orgao_Emissor_Secundario) {
        this.orgao_Emissor_Secundario = orgao_Emissor_Secundario;
    }

    public String getNaturalidade_Cidade() {
        return naturalidade_Cidade;
    }

    public void setNaturalidade_Cidade(String naturalidade_Cidade) {
        this.naturalidade_Cidade = naturalidade_Cidade;
    }

    public String getNaturalidade_Uf() {
        return naturalidade_Uf;
    }

    public void setNaturalidade_Uf(String naturalidade_Uf) {
        this.naturalidade_Uf = naturalidade_Uf;
    }

    public String getDoc_origem() {
        return doc_origem;
    }

    public void setDoc_origem(String doc_origem) {
        this.doc_origem = doc_origem;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getMrzCode() {
        return mrzCode;
    }

    public void setMrzCode(String mrzCode) {
        this.mrzCode = mrzCode;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getOrgao_Emissor_Registro_Migratorio() {
        return orgao_Emissor_Registro_Migratorio;
    }

    public void setOrgao_Emissor_Registro_Migratorio(String orgao_Emissor_Registro_Migratorio) {
        this.orgao_Emissor_Registro_Migratorio = orgao_Emissor_Registro_Migratorio;
    }

    public String getOrgao_Emissor_Passaporte() {
        return orgao_Emissor_Passaporte;
    }

    public void setOrgao_Emissor_Passaporte(String orgao_Emissor_Passaporte) {
        this.orgao_Emissor_Passaporte = orgao_Emissor_Passaporte;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}