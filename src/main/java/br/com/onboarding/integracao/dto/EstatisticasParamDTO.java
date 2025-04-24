package br.com.onboarding.integracao.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record EstatisticasParamDTO(
    @NotNull(message = "A unidade de tempo (unit) é obrigatória")
    TimeUnit unit,

    @NotNull(message = "A quantidade de unidades de tempo (amount) é obrigatória")
    @Min(value = 1, message = "A quantidade deve ser maior ou igual a 1")
    Integer amount
) {
    public enum TimeUnit {
        SEGUNDOS, MINUTOS, HORAS, DIAS, MESES
    }
}