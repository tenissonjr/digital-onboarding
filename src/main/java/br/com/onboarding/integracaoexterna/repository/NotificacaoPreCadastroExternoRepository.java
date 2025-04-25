package br.com.onboarding.integracaoexterna.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.onboarding.integracaoexterna.model.NotificacaoPreCadastroExterno;

@Repository
public interface NotificacaoPreCadastroExternoRepository extends JpaRepository<NotificacaoPreCadastroExterno, Long> {
    // Método para buscar por hash (caso necessário)
    Optional<NotificacaoPreCadastroExterno> findByHash(String hash);

    Optional<NotificacaoPreCadastroExterno> findByDataHoraSincronizacaoIsNull();
}