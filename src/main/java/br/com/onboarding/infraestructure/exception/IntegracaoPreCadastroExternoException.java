package br.com.onboarding.infraestructure.exception;

public class IntegracaoPreCadastroExternoException extends RuntimeException {
    public IntegracaoPreCadastroExternoException(String message) {
        super(message);
    }
    public IntegracaoPreCadastroExternoException(String message, Throwable cause) {
        super(message, cause);
    }

}