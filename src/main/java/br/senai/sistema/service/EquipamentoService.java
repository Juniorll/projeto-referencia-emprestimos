package br.senai.sistema.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.senai.sistema.model.Equipamento;
import br.senai.sistema.repository.EquipamentoRepository;

/**
 * Camada de serviço de Equipamento: concentra as operações e, mais adiante, as regras de negócio.
 */
@Service
public class EquipamentoService {

    private final EquipamentoRepository equipamentoRepository;

    // Injeção de dependência: o Spring entrega o repositório pronto pelo construtor.
    public EquipamentoService(EquipamentoRepository equipamentoRepository) {
        this.equipamentoRepository = equipamentoRepository;
    }

    /** Lista todos os registros, em ordem alfabética. */
    public List<Equipamento> listarTodos() {
        return equipamentoRepository.findAll(Sort.by("descricao"));
    }

    /** Busca um registro pelo id. Se não existir, gera um erro com mensagem clara. */
    public Equipamento buscarPorId(Long id) {
        return equipamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Equipamento não encontrado: " + id));
    }

    /** Salva: se o id for vazio, cadastra um novo; se tiver id, atualiza o existente. */
    public Equipamento salvar(Equipamento equipamento) {
        return equipamentoRepository.save(equipamento);
    }

    /** Exclui pelo id. */
    public void excluir(Long id) {
        equipamentoRepository.deleteById(id);
    }
}