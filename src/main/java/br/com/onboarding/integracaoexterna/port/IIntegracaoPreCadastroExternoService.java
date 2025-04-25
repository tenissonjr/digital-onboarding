package br.com.onboarding.integracaoexterna.port;

import br.com.onboarding.infraestructure.exception.IntegracaoPreCadastroExternoException;

public interface IIntegracaoPreCadastroExternoService {

    IPreCadastroExterno obterPreCadastroExterno(Object hash) throws IntegracaoPreCadastroExternoException;

}
