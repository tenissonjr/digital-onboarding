package br.com.onboarding.infraestructure.adapter.out.sivis;

import org.springframework.stereotype.Service;

import br.com.onboarding.integracaointerna.port.IIntegracaoInternaPreCadastroService;
import br.com.onboarding.precadastro.model.PreCadastro;

@Service
public class IntegracaoSivisAdapter implements IIntegracaoInternaPreCadastroService {

    @Override
    public String getDestino() {
        return "SIVIS";
    }

    @Override
    public void sincronizarPreCadastro(PreCadastro preCadastro) {
        //TODO Implementar a lógica de sincronização com o SIVIS
        System.out.println("Sincronizando  PreCadastro com o sistema SIVIS hash=" + preCadastro.getHash());
    }

}
