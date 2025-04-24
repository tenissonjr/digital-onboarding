package br.com.onboarding.infraestructure.exception;

public class IntegracaoOnboardingServiceException extends RuntimeException {
    public IntegracaoOnboardingServiceException(String message) {
        super(message);
    }
    public IntegracaoOnboardingServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}