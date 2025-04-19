package br.com.onboarding.integration.dto;

import java.time.LocalDate;

public record OnboardingDataDto(
    String hash,
    String cpf,
    String nome,
    String nomeSocial,
    LocalDate dataNascimento,
    String nomeMae,
    String numeroDocumento,
    String paisOrigem,
    String orgaoEmissor,
    String uf,
    LocalDate dataExpedicao,
    LocalDate dataVencimento,
    String fotoUsuario,
    String documentoFrente,
    String documentoVerso
) {}