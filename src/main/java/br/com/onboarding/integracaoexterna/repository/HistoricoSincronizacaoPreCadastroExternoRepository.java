package br.com.onboarding.integracaoexterna.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.onboarding.integracaoexterna.enumeration.SituacaoSincronizacaoEnum;
import br.com.onboarding.integracaoexterna.model.HistoricoSincronizacaoPreCadastroExterno;

@Repository
public interface HistoricoSincronizacaoPreCadastroExternoRepository extends JpaRepository<HistoricoSincronizacaoPreCadastroExterno, Long> {

    /**
     * Busca o histórico mais recente pelo hash
     */
    Optional<HistoricoSincronizacaoPreCadastroExterno> findFirstByHashOrderByDataHoraDesc(String hash);

    /**
     * Busca todos os registros com determinado status
     */
    List<HistoricoSincronizacaoPreCadastroExterno> findBySituacao(SituacaoSincronizacaoEnum situacao);

    long countBySituacaoAndDataHoraBetween(SituacaoSincronizacaoEnum status, LocalDateTime startTime,
            LocalDateTime endTime);

}