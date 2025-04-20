package br.com.onboarding.integration.port;

import br.com.onboarding.infraestructure.exception.OnboardingServiceException;
import br.com.onboarding.integration.dto.OnboardingDataDto;

public interface IFechingOnboardData {

    OnboardingDataDto fetchOnboardingData(Object hash) throws OnboardingServiceException;

}
