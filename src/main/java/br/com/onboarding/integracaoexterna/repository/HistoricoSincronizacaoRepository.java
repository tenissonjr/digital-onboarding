package br.com.onboarding.integracaoexterna.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.onboarding.integracaoexterna.enumeration.SituacaoSincronizacaoEnum;
import br.com.onboarding.integracaoexterna.model.HistoricoSincronizacao;

@Repository
public interface HistoricoSincronizacaoRepository extends JpaRepository<HistoricoSincronizacao, Long> {

    /**
     * Busca o histórico mais recente pelo hash
     */
    Optional<HistoricoSincronizacao> findFirstByHashOrderByDataHoraDesc(String hash);

    /**
     * Busca todos os registros com determinado status
     */
    List<HistoricoSincronizacao> findBySituacao(SituacaoSincronizacaoEnum situacao);

    long countBySituacaoAndDataHoraBetween(SituacaoSincronizacaoEnum status, LocalDateTime startTime,
            LocalDateTime endTime);

}