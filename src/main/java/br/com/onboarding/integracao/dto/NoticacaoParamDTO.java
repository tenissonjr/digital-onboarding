package br.com.onboarding.integracao.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NoticacaoParamDTO(
        @NotBlank(message = "Hash é obrigatório") 
        @Size(min = 32, max = 64, message = "Hash deve ter entre 32 e 64 caracteres") 
        String hash
        ) {
};
