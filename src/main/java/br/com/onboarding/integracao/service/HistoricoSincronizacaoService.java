package br.com.onboarding.integracao.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import br.com.onboarding.integracao.enumeration.SituacaoSincronizacaoEnum;
import br.com.onboarding.integracao.model.HistoricoSincronizacao;
import br.com.onboarding.integracao.repository.HistoricoSincronizacaoRepository;

@Service
public class HistoricoSincronizacaoService {

    private final HistoricoSincronizacaoRepository historicoSincronizacaoRepository;

    public HistoricoSincronizacaoService(HistoricoSincronizacaoRepository historicoSincronizacaoRepository) {
        this.historicoSincronizacaoRepository = historicoSincronizacaoRepository;
    }

    private void registrarHistorico(String hash, SituacaoSincronizacaoEnum situacao, String mensagemErro) {
        HistoricoSincronizacao historico = new HistoricoSincronizacao();
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