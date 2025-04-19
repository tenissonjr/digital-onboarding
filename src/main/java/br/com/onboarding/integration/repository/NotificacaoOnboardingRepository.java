package br.com.onboarding.integration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.onboarding.integration.model.NotificacaoOnboarding;

@Repository
public interface NotificacaoOnboardingRepository extends JpaRepository<NotificacaoOnboarding, Long> {
    // Método para buscar por hash (caso necessário)
    Optional<NotificacaoOnboarding> findByHash(String hash);
}