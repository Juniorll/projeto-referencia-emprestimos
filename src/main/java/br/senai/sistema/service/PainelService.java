package br.senai.sistema.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.senai.sistema.model.Emprestimo;
import br.senai.sistema.model.Indicador;
import br.senai.sistema.model.SituacaoEmprestimo;
import br.senai.sistema.repository.EmprestimoRepository;
import br.senai.sistema.repository.EquipamentoRepository;

/**
 * Calcula os indicadores do painel (página inicial).
 * Usa consultas de contagem do Spring Data (count, countBy...) e laços (for) para contar e somar.
 */
@Service
public class PainelService {

    private final EquipamentoRepository equipamentoRepository;
    private final EmprestimoRepository emprestimoRepository;

    public PainelService(EquipamentoRepository equipamentoRepository,
                         EmprestimoRepository emprestimoRepository) {
        this.equipamentoRepository = equipamentoRepository;
        this.emprestimoRepository = emprestimoRepository;
    }

    /** Data de hoje no horário de Brasília (o Codespace usa o horário de Londres). */
    private LocalDate hoje() {
        return LocalDate.now(ZoneId.of("America/Sao_Paulo"));
    }

    /** Os indicadores do painel, na ordem em que aparecem. */
    public List<Indicador> indicadores() {
        long equipamentos = equipamentoRepository.count();
        long emprestados = emprestimoRepository.countBySituacao(SituacaoEmprestimo.EMPRESTADO);
        long atrasados = emprestimoRepository.countBySituacaoAndDataPrevistaBefore(SituacaoEmprestimo.EMPRESTADO, hoje());
        long devolvidos = emprestimoRepository.countBySituacao(SituacaoEmprestimo.DEVOLVIDO);

        List<Indicador> lista = new ArrayList<>();
        lista.add(new Indicador("Equipamentos", String.valueOf(equipamentos), "cadastrados", "bi-tools", "primary"));
        lista.add(new Indicador("Emprestados", String.valueOf(emprestados), "fora do almoxarifado", "bi-box-arrow-right", "warning"));
        lista.add(new Indicador("Em atraso", String.valueOf(atrasados), "devolução prevista já passou", "bi-alarm", "danger"));
        lista.add(new Indicador("Devolvidos", String.valueOf(devolvidos), "empréstimos encerrados", "bi-check2-circle", "success"));
        return lista;
    }

    /** Lista de atenção: empréstimos em atraso, do mais atrasado para o menos atrasado. */
    public List<Emprestimo> atencao() {
        return emprestimoRepository.findBySituacaoAndDataPrevistaBeforeOrderByDataPrevistaAsc(SituacaoEmprestimo.EMPRESTADO, hoje());
    }

    /** Gráfico: nomes das barras. */
    public List<String> graficoRotulos() {
        return List.of("No prazo", "Em atraso", "Devolvidos");
    }

    /** Gráfico: altura de cada barra. */
    public List<Long> graficoValores() {
        long emprestados = emprestimoRepository.countBySituacao(SituacaoEmprestimo.EMPRESTADO);
        long atrasados = emprestimoRepository.countBySituacaoAndDataPrevistaBefore(SituacaoEmprestimo.EMPRESTADO, hoje());
        long devolvidos = emprestimoRepository.countBySituacao(SituacaoEmprestimo.DEVOLVIDO);
        return List.of(emprestados - atrasados, atrasados, devolvidos);
    }
}
