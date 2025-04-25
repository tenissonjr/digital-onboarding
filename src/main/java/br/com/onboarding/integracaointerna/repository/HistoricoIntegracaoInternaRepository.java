package br.com.onboarding.integracaointerna.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.onboarding.integracaointerna.model.HistoricoIntegracaoInterna;

@Repository
public interface HistoricoIntegracaoInternaRepository extends JpaRepository<HistoricoIntegracaoInterna, Long> {
 
}