package br.com.onboarding.precadastro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.onboarding.precadastro.model.PreCadastro;

@Repository
public interface PreCadastroRepository extends JpaRepository<PreCadastro, Long> {
    // Método para buscar por hash
    PreCadastro findByHash(String hash);
}