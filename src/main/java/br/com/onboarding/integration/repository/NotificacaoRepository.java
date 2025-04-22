package br.com.onboarding.integration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.onboarding.integration.model.Notificacao;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    // Método para buscar por hash (caso necessário)
    Optional<Notificacao> findByHash(String hash);

    Optional<Notificacao> findByDataRecebimentoIsNull();
}