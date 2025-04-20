package br.com.onboarding.infraestructure.exception;

public class OnboardingServiceException extends RuntimeException {
    public OnboardingServiceException(String message) {
        super(message);
    }
    public OnboardingServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}