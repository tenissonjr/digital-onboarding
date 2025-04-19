package br.com.onboarding.precadastro.service;

import org.springframework.stereotype.Service;

import br.com.onboarding.infraestructure.messaging.broker.IMessageBroker;
import br.com.onboarding.precadastro.model.PreCadastro;
import br.com.onboarding.precadastro.repository.PreCadastroRepository;

@Service
public class PreCadastroService {

    private final PreCadastroRepository preCadastroRepository;

    public PreCadastroService(IMessageBroker messageBroker, PreCadastroRepository preCadastroRepository) {
        this.preCadastroRepository = preCadastroRepository;
    }

    public PreCadastro criarPreCadastro(String hash) {
        // Verifica se já existe um PreCadastro com o mesmo hash
        if (preCadastroRepository.findByHash(hash) != null) {
            throw new IllegalArgumentException("Já existe um PreCadastro com o hash fornecido.");
        }

        // Cria uma nova instância de PreCadastro
        PreCadastro preCadastro = new PreCadastro();
        preCadastro.setHash(hash);

        // Salva o PreCadastro no banco de dados
        return preCadastroRepository.save(preCadastro);
    }
}