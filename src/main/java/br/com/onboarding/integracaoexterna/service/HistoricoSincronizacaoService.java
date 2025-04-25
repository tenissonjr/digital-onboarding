package br.com.onboarding.integracaoexterna.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import br.com.onboarding.integracaoexterna.enumeration.SituacaoSincronizacaoEnum;
import br.com.onboarding.integracaoexterna.model.HistoricoSincronizacaoPreCadastroExterno;
import br.com.onboarding.integracaoexterna.repository.HistoricoSincronizacaoPreCadastroExternoRepository;

@Service
public class HistoricoSincronizacaoService {

    private final HistoricoSincronizacaoPreCadastroExternoRepository historicoSincronizacaoRepository;

    public HistoricoSincronizacaoService(HistoricoSincronizacaoPreCadastroExternoRepository historicoSincronizacaoRepository) {
        this.historicoSincronizacaoRepository = historicoSincronizacaoRepository;
    }

    private void registrarHistorico(String hash, SituacaoSincronizacaoEnum situacao, String mensagemErro) {
        HistoricoSincronizacaoPreCadastroExterno historico = new HistoricoSincronizacaoPreCadastroExterno();
        historico.setHash(hash);
        historico.setSituacao(situacao);
        historico.setMensagemErro(mensagemErro);
        historico.setDataHora(LocalDateTime.now());

        historicoSincronizacaoRepository.save(historico);
    }
    public void registrarFalhaSincronizacao(String hash,  String mensagemErro) {
        registrarHistorico(hash, SituacaoSincronizacaoEnum.FALHA_SINCRONIZACAO, mensagemErro);
    }
    public void registrarSucessoSincronizacao(String hash) {
        registrarHistorico(hash, SituacaoSincronizacaoEnum.SUCESSO_SINCRONIZACAO, null);
    }

    public void registrarPendenciaSincronizacao(String hash) {
        registrarHistorico(hash, SituacaoSincronizacaoEnum.PENDENTE_SINCRONIZACAO, null);
    }

}