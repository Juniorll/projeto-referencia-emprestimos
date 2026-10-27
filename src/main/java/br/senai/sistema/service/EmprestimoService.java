package br.senai.sistema.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.senai.sistema.model.Emprestimo;
import br.senai.sistema.model.SituacaoEmprestimo;
import br.senai.sistema.repository.EmprestimoRepository;

/**
 * Camada de serviço de Emprestimo: concentra as operações e as REGRAS DE NEGÓCIO.
 * Quando uma regra não é cumprida, o método lança RegraNegocioException com a mensagem para o usuário.
 */
@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;

    // Injeção de dependência: o Spring entrega os repositórios prontos pelo construtor.
    public EmprestimoService(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }

    /** Lista todos os registros, em ordem de retirada. */
    public List<Emprestimo> listarTodos() {
        return emprestimoRepository.findAll(Sort.by("dataRetirada"));
    }

    /** Lista os registros de uma situação; sem filtro (null), lista todos. */
    public List<Emprestimo> listar(SituacaoEmprestimo situacao) {
        if (situacao == null) {
            return listarTodos();
        }
        return emprestimoRepository.findBySituacao(situacao, Sort.by("dataRetirada"));
    }

    /** Busca um registro pelo id. Se não existir, gera um erro com mensagem clara. */
    public Emprestimo buscarPorId(Long id) {
        return emprestimoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado: " + id));
    }

    /**
     * Salva, conferindo as regras de negócio. Empréstimo novo começa EMPRESTADO;
     * na edição, a situação e a data de devolução não mudam pelo formulário.
     */
    @Transactional
    public Emprestimo salvar(Emprestimo emprestimo) {
        if (emprestimo.getDataPrevista().isBefore(emprestimo.getDataRetirada())) {             // regra 2
            throw new RegraNegocioException("A devolução prevista não pode ser antes da retirada.");
        }
        if (emprestimo.getId() == null) {
            emprestimo.setSituacao(SituacaoEmprestimo.EMPRESTADO);
        } else {
            Emprestimo atual = buscarPorId(emprestimo.getId());
            emprestimo.setSituacao(atual.getSituacao());
            emprestimo.setDataDevolucao(atual.getDataDevolucao());
        }
        if (emprestimo.getSituacao() == SituacaoEmprestimo.EMPRESTADO) {
            verificarEquipamentoDisponivel(emprestimo);                                  // regra 1
        }
        return emprestimoRepository.save(emprestimo);
    }

    /** Registra a devolução: o empréstimo passa a DEVOLVIDO, com a data de hoje. */
    @Transactional
    public void registrarDevolucao(Long id) {
        Emprestimo emprestimo = buscarPorId(id);
        if (emprestimo.getSituacao() == SituacaoEmprestimo.DEVOLVIDO) {
            throw new RegraNegocioException("Este empréstimo já foi devolvido.");
        }
        emprestimo.setSituacao(SituacaoEmprestimo.DEVOLVIDO);
        emprestimo.setDataDevolucao(LocalDate.now(ZoneId.of("America/Sao_Paulo")));
        emprestimoRepository.save(emprestimo);
    }

    /** Exclui pelo id. */
    public void excluir(Long id) {
        emprestimoRepository.deleteById(id);
    }

    /** Regra de negócio 1: um equipamento emprestado não pode ser emprestado de novo até ser devolvido. */
    private void verificarEquipamentoDisponivel(Emprestimo emprestimo) {
        List<Emprestimo> emAberto = emprestimoRepository.findByEquipamentoAndSituacao(emprestimo.getEquipamento(), SituacaoEmprestimo.EMPRESTADO);
        for (Emprestimo outro : emAberto) {
            boolean outroRegistro = !outro.getId().equals(emprestimo.getId());
            if (outroRegistro) {
                throw new RegraNegocioException("Este equipamento já está emprestado e ainda não foi devolvido.");
            }
        }
    }
}
