package br.com.onboarding.precadastro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.onboarding.precadastro.model.ErrosValidacaoPreCadastro;

@Repository
public interface ErrosValidacaoPreCadastroRepository extends JpaRepository<ErrosValidacaoPreCadastro, Long> {
    // Métodos customizados podem ser adicionados aqui, se necessário
}