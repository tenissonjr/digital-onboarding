package br.com.onboarding.integracao.port;

import br.com.onboarding.infraestructure.exception.IntegracaoOnboardingServiceException;
import br.com.onboarding.integracao.dto.OnboardingDataDto;

public interface IIntegracaoOnboardDigitalService {

    OnboardingDataDto obterDadosPessoais(Object hash) throws IntegracaoOnboardingServiceException;

}
