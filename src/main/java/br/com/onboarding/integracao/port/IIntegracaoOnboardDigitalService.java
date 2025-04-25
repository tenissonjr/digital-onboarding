package br.com.onboarding.integracao.port;

import br.com.onboarding.infraestructure.exception.IntegracaoOnboardingServiceException;
import br.com.onboarding.integracao.dto.valid.ValidOnboardingDataDTO;

public interface IIntegracaoOnboardDigitalService {

    ValidOnboardingDataDTO obterDadosPessoais(Object hash) throws IntegracaoOnboardingServiceException;

}
