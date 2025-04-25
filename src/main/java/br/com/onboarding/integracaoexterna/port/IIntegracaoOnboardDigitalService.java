package br.com.onboarding.integracaoexterna.port;

import br.com.onboarding.infraestructure.exception.IntegracaoOnboardingServiceException;
import br.com.onboarding.integracaoexterna.dto.valid.ValidOnboardingDataDTO;

public interface IIntegracaoOnboardDigitalService {

    ValidOnboardingDataDTO obterDadosPessoais(Object hash) throws IntegracaoOnboardingServiceException;

}
