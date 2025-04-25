package br.com.onboarding.precadastro.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.onboarding.infraestructure.messaging.broker.IMessageBroker;
import br.com.onboarding.infraestructure.messaging.broker.MessageTopic;
import br.com.onboarding.integracaoexterna.port.IPreCadastroExterno;
import br.com.onboarding.precadastro.enumeration.SituacaoPreCadastro;
import br.com.onboarding.precadastro.model.ErrosValidacaoPreCadastro;
import br.com.onboarding.precadastro.model.PreCadastro;
import br.com.onboarding.precadastro.repository.ErrosValidacaoPreCadastroRepository;
import br.com.onboarding.precadastro.repository.PreCadastroRepository;
import br.com.onboarding.shared.dto.ErroSincronizacaoDTO;

@Service
public class PreCadastroService {

    private final IMessageBroker messageBroker;
    private final PreCadastroRepository preCadastroRepository;
    private final ErrosValidacaoPreCadastroRepository errosValidacaoPreCadastroRepository;

    private static final Logger log = LoggerFactory.getLogger(PreCadastroService.class);

    public PreCadastroService(IMessageBroker messageBroker, PreCadastroRepository preCadastroRepository,
            ErrosValidacaoPreCadastroRepository errosValidacaoPreCadastroRepository) {

        this.messageBroker = messageBroker;
        this.preCadastroRepository = preCadastroRepository;
        this.errosValidacaoPreCadastroRepository = errosValidacaoPreCadastroRepository;
        this.messageBroker.subscribe(MessageTopic.DADOS_PRE_CADASTRO_EXTERNO_OBTIDOS, this::criarPreCadastro);
    }

    private void criarPreCadastro(Object preCadastroExternoObject) {

        IPreCadastroExterno preCadastroExterno = (IPreCadastroExterno) preCadastroExternoObject;

        try {
            // Cria uma nova instância de PreCadastro
            PreCadastro preCadastro = PreCadastro.valueOf(preCadastroExterno);

            // Salva o PreCadastro no banco de dados
            preCadastroRepository.save(preCadastro);

            this.messageBroker.publish(MessageTopic.DADOS_PRE_CADASTRO_EXTERNO_SINCRONIZADOS, preCadastroExterno.getHash());

            validarPreCadastro(preCadastro);

        } catch (Exception e) {
            log.error("Falha na sincronização", e);
            this.messageBroker.publish(MessageTopic.FALHA_SINCRONIZACAO_PRECADASTRO_EXTERNO, new ErroSincronizacaoDTO(preCadastroExterno.getHash(), e.getMessage()));
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