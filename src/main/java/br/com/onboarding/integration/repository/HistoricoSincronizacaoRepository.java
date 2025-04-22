package br.com.onboarding.integration.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.onboarding.integration.enumeration.SituacaoHistoricoSincronizacao;
import br.com.onboarding.integration.enumeration.SituacaoSincronizacao;
import br.com.onboarding.integration.model.HistoricoSincronizacao;

@Repository
public interface HistoricoSincronizacaoRepository extends JpaRepository<HistoricoSincronizacao, Long> {

    /**
     * Busca o histórico mais recente pelo hash
     */
    Optional<HistoricoSincronizacao> findFirstByHashOrderByDataHoraDesc(String hash);

    /**
     * Busca todos os registros com determinado status
     */
    List<HistoricoSincronizacao> findBySituacao(SituacaoHistoricoSincronizacao situacao);

    long countBySituacaoAndDataHoraBetween(SituacaoSincronizacao status, LocalDateTime startTime,
            LocalDateTime endTime);

}