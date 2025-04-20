package br.com.onboarding.integration.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.onboarding.integration.enumeration.FetchStatus;
import br.com.onboarding.integration.model.HistoricoSincronizacaoOnboarding;

@Repository
public interface HistoricoSincronizacaoOnboardingRepository extends JpaRepository<HistoricoSincronizacaoOnboarding, Long> {

    /**
     * Busca o histórico mais recente pelo hash
     */
    Optional<HistoricoSincronizacaoOnboarding> findFirstByHashOrderByDataHoraDesc(String hash);

    /**
     * Busca todos os registros com determinado status
     */
    List<HistoricoSincronizacaoOnboarding> findByStatus(FetchStatus status);

}