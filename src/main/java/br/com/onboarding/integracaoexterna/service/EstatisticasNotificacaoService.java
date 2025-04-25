package br.com.onboarding.integracaoexterna.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import br.com.onboarding.integracaoexterna.dto.EstatisticasDTO;
import br.com.onboarding.integracaoexterna.dto.EstatisticasParamDTO;
import br.com.onboarding.integracaoexterna.dto.EstatisticasDTO.TimePeriodStats;
import br.com.onboarding.integracaoexterna.dto.EstatisticasParamDTO.TimeUnit;
import br.com.onboarding.integracaoexterna.enumeration.SituacaoSincronizacaoEnum;
import br.com.onboarding.integracaoexterna.repository.HistoricoSincronizacaoPreCadastroExternoRepository;
import br.com.onboarding.precadastro.enumeration.SituacaoPreCadastro;
import br.com.onboarding.precadastro.service.PreCadastroService;

@Service
public class EstatisticasNotificacaoService {

    private final HistoricoSincronizacaoPreCadastroExternoRepository historicoSincronizacaoRepository;
    private final PreCadastroService preCadastroService;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public EstatisticasNotificacaoService(HistoricoSincronizacaoPreCadastroExternoRepository historicoSincronizacaoRepository,
            PreCadastroService preCadastroService) {

        this.historicoSincronizacaoRepository = historicoSincronizacaoRepository;
        this.preCadastroService = preCadastroService;
    }

    private LocalDateTime calculateStartTime(LocalDateTime now, EstatisticasParamDTO.TimeUnit unit, int amount) {
        return switch (unit) {
            case SEGUNDOS -> now.minus(amount, ChronoUnit.SECONDS);
            case MINUTOS -> now.minus(amount, ChronoUnit.MINUTES);
            case HORAS -> now.minus(amount, ChronoUnit.HOURS);
            case DIAS -> now.minus(amount, ChronoUnit.DAYS);
            case MESES -> now.minus(amount, ChronoUnit.MONTHS);
        };
    }

    private Map<SituacaoSincronizacaoEnum, Long> obterEstatisticasSincronizacao(LocalDateTime startTime, LocalDateTime endTime) {
        Map<SituacaoSincronizacaoEnum, Long> estatisticas = new EnumMap<>(SituacaoSincronizacaoEnum.class);
        for (SituacaoSincronizacaoEnum status : SituacaoSincronizacaoEnum.values()) {
            long count = historicoSincronizacaoRepository.countBySituacaoAndDataHoraBetween(status, startTime, endTime);
            estatisticas.put(status, count);
        }
        return estatisticas;
    }


    public EstatisticasDTO gerarEstatisticar(EstatisticasParamDTO param) {
        LocalDateTime now = LocalDateTime.now();
        List<TimePeriodStats> timePeriodStatsList = new ArrayList<>();

        for (int i = 0; i < param.amount(); i++) {

            LocalDateTime startTime = calculateStartTime(now, param.unit(), i + 1);
            LocalDateTime endTime = calculateStartTime(now, param.unit(), i);

            Map<SituacaoSincronizacaoEnum, Long> totaisSituacaoSincronizacao = obterEstatisticasSincronizacao(startTime, endTime);
            Map<SituacaoPreCadastro, Long> totaisSituacaoPreCadastro = preCadastroService.obterEstatisticasPreCadastro(startTime, endTime);


            TimePeriodStats stats = new TimePeriodStats();
            stats.setPeriodo(formatPeriodLabel(param.unit(), i));
            stats.setTotaisSituacoesSincronizacao(totaisSituacaoSincronizacao);
           stats.setTotaisSituacoesPreCadastro(totaisSituacaoPreCadastro);

            timePeriodStatsList.add(stats);

        }    
        EstatisticasDTO estatisticasDTO = new EstatisticasDTO();
        estatisticasDTO.setTimePeriodStats(timePeriodStatsList);
        estatisticasDTO.setTimestamp(now.format(formatter));

        return estatisticasDTO;
    }

    private String formatPeriodLabel(TimeUnit unit,         int i) {
        return switch (unit) {
            case SEGUNDOS -> "Últimos " + i + " segundos";
            case MINUTOS -> "Últimos " + i + " minutos";
            case HORAS -> "Últimas " + i + " horas";
            case DIAS -> "Últimos " + i + " dias";
            case MESES -> "Últimos " + i + " meses";
        };

    }
}