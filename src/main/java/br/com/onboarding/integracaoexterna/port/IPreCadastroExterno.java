package br.com.onboarding.integracaoexterna.port;

import java.time.LocalDate;

public interface IPreCadastroExterno {

    String getHash();
    String getImagemFace();    
    String getCpf();
    String getNome();
    String getNomeSocial();
    LocalDate getDataNascimento();
    String getNomeMae();
    String getImagemDocumento();    
    String getNumeroDocumento();
    String getPaisOrigem();
    String getOrgaoEmissor();
    String getUf();
    LocalDate getDataExpedicao();
    LocalDate getDataVencimento();
    String getDadosOriginais();
}