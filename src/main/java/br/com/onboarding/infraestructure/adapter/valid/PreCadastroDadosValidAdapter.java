package br.com.onboarding.infraestructure.adapter.valid;

import java.time.LocalDate;

import br.com.onboarding.infraestructure.adapter.valid.dto.ValidCaptureItemReportDTO;
import br.com.onboarding.infraestructure.adapter.valid.dto.ValidCapturesReportDTO;
import br.com.onboarding.infraestructure.adapter.valid.dto.ValidOcrDocumentReportDTO;
import br.com.onboarding.infraestructure.adapter.valid.dto.ValidOnboardingDataDTO;
import br.com.onboarding.integracaoexterna.port.IPreCadastroExterno;

public class PreCadastroDadosValidAdapter implements IPreCadastroExterno{

    private final ValidOnboardingDataDTO validDto;
    private final ValidCaptureItemReportDTO captureItemReportFace;
    private final ValidCaptureItemReportDTO captureItemReportDocumentos;
    private final ValidOcrDocumentReportDTO dadosDocumento;

    public PreCadastroDadosValidAdapter(ValidOnboardingDataDTO validDto) {
        this.validDto = validDto;
        ValidCapturesReportDTO captureReportProvaVida = validDto.getCapturesReport().get(0);
        this.captureItemReportFace = captureReportProvaVida.getCaptureItemReport().get(0); 
        ValidCapturesReportDTO captureReportDocumentos = validDto.getCapturesReport().get(1);
        this.captureItemReportDocumentos = captureReportDocumentos.getCaptureItemReport().get(0);         
        this.dadosDocumento= captureItemReportDocumentos.getOcrDocumentReport();
    }

    @Override
    public String getHash() {
        return this.validDto.getHash();
    }

    @Override
    public String getDadosOriginais() {
        return this.validDto.getDadosOriginais();
    }

    @Override
    public String getCpf() {
        return this.dadosDocumento.getCpf();
    }

    @Override
    public String getNome() {
        return this.dadosDocumento.getDocumentName();
    }

    @Override
    public String getNomeSocial() {
        return null;
    }

    @Override
    public LocalDate getDataNascimento() {
        return dadosDocumento.getData_de_nascimento();
    }

    @Override
    public String getNomeMae() {
       return dadosDocumento.getFiliacao1();
    }

    @Override
    public String getNumeroDocumento() {
       return dadosDocumento.getNumero_da_CNH();
    }

    @Override
    public String getPaisOrigem() {
        return null;
    }

    @Override
    public String getOrgaoEmissor() {
        return dadosDocumento.getOrgao_emissor_do_RG();
    }

    @Override
    public String getUf() {
       return null;
    }

    @Override
    public LocalDate getDataExpedicao() {
        return dadosDocumento.getData_de_expedicao() ;
    }

    @Override
    public LocalDate getDataVencimento() {
        return dadosDocumento.getData_de_validade();
    }

    @Override
    public String getImagemFace() {
        return this.captureItemReportFace.getBase64();
    }

    @Override
    public String getImagemDocumento() {
        return this.captureItemReportDocumentos.getBase64();
    }


}
