package br.senai.sistema.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.senai.sistema.model.Equipamento;
import br.senai.sistema.repository.EquipamentoRepository;

/**
 * Camada de serviço de Equipamento: é aqui que ficarão as regras de negócio.
 * Por enquanto, ela só busca os dados no repositório.
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
}