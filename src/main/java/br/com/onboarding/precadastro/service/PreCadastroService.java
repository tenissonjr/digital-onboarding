package br.com.onboarding.precadastro.service;

import org.springframework.stereotype.Service;

import br.com.onboarding.infraestructure.messaging.broker.IMessageBroker;
import br.com.onboarding.infraestructure.messaging.broker.MessageTopic;
import br.com.onboarding.integration.dto.OnboardingDataDto;
import br.com.onboarding.precadastro.model.PreCadastro;
import br.com.onboarding.precadastro.repository.PreCadastroRepository;

@Service
public class PreCadastroService {

    private final PreCadastroRepository preCadastroRepository;

    public PreCadastroService(IMessageBroker messageBroker, PreCadastroRepository preCadastroRepository) {
        this.preCadastroRepository = preCadastroRepository;
        messageBroker.subscribe(MessageTopic.DADOS_OBTIDOS, this::criarPreCadastro);
    }

    private PreCadastro criarPreCadastro(Object onboardingDataDto) {

        OnboardingDataDto dto = (OnboardingDataDto) onboardingDataDto;


        // Cria uma nova instância de PreCadastro
        PreCadastro preCadastro =PreCadastro.valueOf(dto);

        // Salva o PreCadastro no banco de dados
        return preCadastroRepository.save(preCadastro);
    }
}