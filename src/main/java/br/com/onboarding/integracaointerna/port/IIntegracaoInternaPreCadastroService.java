package br.com.onboarding.integracaointerna.port;

import br.com.onboarding.precadastro.model.PreCadastro;

public interface IIntegracaoInternaPreCadastroService {
    String getDestino() ;
    void sincronizarPreCadastro(PreCadastro preCadastro) ;
}
