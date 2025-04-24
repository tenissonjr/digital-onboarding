package br.com.onboarding.integracao.dto;


import java.util.List;
import java.util.Map;

import br.com.onboarding.integracao.enumeration.SituacaoSincronizacaoEnum;
import br.com.onboarding.precadastro.enumeration.SituacaoPreCadastro;

public class EstatisticasDTO {

    private List<TimePeriodStats> timePeriodStats;
    private String timestamp;


    public static class TimePeriodStats {
        private String periodo; // Ex: "Última Hora", "Penúltima Hora"
        private Map<SituacaoSincronizacaoEnum, Long> totaisSituacoesSincronizacao;
        private Map<SituacaoPreCadastro, Long> totaisSituacoesPreCadastro;
        public String getPeriodo() {
            return periodo;
        }
        public void setPeriodo(String periodo) {
            this.periodo = periodo;
        }
        public Map<SituacaoSincronizacaoEnum, Long> getTotaisSituacoesSincronizacao() {
            return totaisSituacoesSincronizacao;
        }
        public void setTotaisSituacoesSincronizacao(Map<SituacaoSincronizacaoEnum, Long> totaisSituacoesSincronizacao) {
            this.totaisSituacoesSincronizacao = totaisSituacoesSincronizacao;
        }
        public Map<SituacaoPreCadastro, Long> getTotaisSituacoesPreCadastro() {
            return totaisSituacoesPreCadastro;
        }
        public void setTotaisSituacoesPreCadastro(Map<SituacaoPreCadastro, Long> totaisSituacoesPreCadastro) {
            this.totaisSituacoesPreCadastro = totaisSituacoesPreCadastro;
        }
        
    }


    public List<TimePeriodStats> getTimePeriodStats() {
        return timePeriodStats;
    }


    public void setTimePeriodStats(List<TimePeriodStats> timePeriodStats) {
        this.timePeriodStats = timePeriodStats;
    }


    public String getTimestamp() {
        return timestamp;
    }


    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    
}