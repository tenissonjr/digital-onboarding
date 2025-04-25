package br.com.onboarding.precadastro.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import br.com.onboarding.infraestructure.messaging.broker.IMessageBroker;
import br.com.onboarding.infraestructure.messaging.broker.MessageTopic;
import br.com.onboarding.integracaoexterna.dto.valid.ValidOnboardingDataDTO;
import br.com.onboarding.integracaoexterna.service.HistoricoSincronizacaoService;
import br.com.onboarding.precadastro.enumeration.SituacaoPreCadastro;
import br.com.onboarding.precadastro.model.ErrosValidacaoPreCadastro;
import br.com.onboarding.precadastro.model.PreCadastro;
import br.com.onboarding.precadastro.repository.ErrosValidacaoPreCadastroRepository;
import br.com.onboarding.precadastro.repository.PreCadastroRepository;

@Service
public class PreCadastroService {

    private final IMessageBroker messageBroker;
    private final PreCadastroRepository preCadastroRepository;
    private final HistoricoSincronizacaoService historicoSincronizacaoService;
    private final ErrosValidacaoPreCadastroRepository errosValidacaoPreCadastroRepository;

    public PreCadastroService(IMessageBroker messageBroker, PreCadastroRepository preCadastroRepository,
            HistoricoSincronizacaoService historicoSincronizacaoService,
            ErrosValidacaoPreCadastroRepository errosValidacaoPreCadastroRepository) {

        this.messageBroker = messageBroker;
        this.preCadastroRepository = preCadastroRepository;
        this.historicoSincronizacaoService = historicoSincronizacaoService;
        this.errosValidacaoPreCadastroRepository = errosValidacaoPreCadastroRepository;
        this.messageBroker.subscribe(MessageTopic.DADOS_OBTIDOS, this::criarPreCadastro);
    }

    private void criarPreCadastro(Object onboardingDataDto) {

        ValidOnboardingDataDTO validDto = (ValidOnboardingDataDTO) onboardingDataDto;

        try {
            // Cria uma nova instância de PreCadastro
            PreCadastro preCadastro = PreCadastro.valueOf(validDto);

            // Salva o PreCadastro no banco de dados
            preCadastroRepository.save(preCadastro);

            this.messageBroker.publish(MessageTopic.DADOS_SINCRONIZADOS, validDto.getHash());

            validarPreCadastro(preCadastro);

        } catch (Exception e) {
            // Lidar com exceções específicas, se necessário
            historicoSincronizacaoService.registrarFalhaSincronizacao(validDto.getHash(), e.getMessage());
        }

    }

    private void validarPreCadastro(PreCadastro preCadastro) {
        List<ErrosValidacaoPreCadastro> erros = validarDadosPreCadastro(preCadastro);

        if (erros.isEmpty()) {
            preCadastroRepository.save(preCadastro.registrarSituacaoSucessoValidacao());
        } else {
            salvarErrosValidacao(erros);
            preCadastroRepository.save(preCadastro.registrarSituacaoErroValidacao());
        }
    }

    private List<ErrosValidacaoPreCadastro> validarDadosPreCadastro(PreCadastro preCadastro) {
        List<ErrosValidacaoPreCadastro> erros = new ArrayList<>();

        if (StringUtils.isEmpty(preCadastro.getNomeMae())) {
            erros.add(new ErrosValidacaoPreCadastro(preCadastro, "nomeMae", "Nome da mãe pode ser vazio"));
        }

        if (preCadastro.getNomeMae().contains("Jr.")) {
            erros.add(new ErrosValidacaoPreCadastro(preCadastro, "nomeMae", "Nome da mãe inválido."));
        }

        // TODO: Adicionar mais validações conforme a necessidade

        return erros;
    }


    private void salvarErrosValidacao(List<ErrosValidacaoPreCadastro> erros) {
        for (ErrosValidacaoPreCadastro erro : erros) {
            errosValidacaoPreCadastroRepository.save(erro);
        }
    }

    public Map<SituacaoPreCadastro, Long> obterEstatisticasPreCadastro(LocalDateTime startTime, LocalDateTime endTime) {
        Map<SituacaoPreCadastro, Long> estatisticas = new EnumMap<>(SituacaoPreCadastro.class);
        for (SituacaoPreCadastro status : SituacaoPreCadastro.values()) {
            long count = preCadastroRepository.countBySituacaoAndDataCadastroBetween(status, startTime, endTime);
            estatisticas.put(status, count);
        }
        return estatisticas;
    }
}