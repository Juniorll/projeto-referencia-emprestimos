package br.senai.sistema.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.senai.sistema.model.Categoria;
import br.senai.sistema.repository.CategoriaRepository;

/**
 * Camada de serviço de Categoria: concentra as operações e, mais adiante, as regras de negócio.
 */
@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    // Injeção de dependência: o Spring entrega o repositório pronto pelo construtor.
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    /** Lista todos os registros, em ordem alfabética. */
    public List<Categoria> listarTodos() {
        return categoriaRepository.findAll(Sort.by("nome"));
    }

    /** Busca um registro pelo id. Se não existir, gera um erro com mensagem clara. */
    public Categoria buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada: " + id));
    }

    /** Salva: se o id for vazio, cadastra um novo; se tiver id, atualiza o existente. */
    public Categoria salvar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    /** Exclui pelo id. */
    public void excluir(Long id) {
        categoriaRepository.deleteById(id);
    }
}