package br.senai.sistema.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.senai.sistema.model.Categoria;
import br.senai.sistema.model.Equipamento;
import br.senai.sistema.repository.CategoriaRepository;
import br.senai.sistema.repository.EquipamentoRepository;

/**
 * Conversores: transformam o id que vem do formulário (um texto, por exemplo "3")
 * no objeto correspondente do banco. São usados pelas listas de escolha (select)
 * dos formulários que têm relacionamento.
 */
@Configuration
public class Conversores implements WebMvcConfigurer {

    private final EquipamentoRepository equipamentoRepository;
    private final CategoriaRepository categoriaRepository;

    public Conversores(EquipamentoRepository equipamentoRepository,
                       CategoriaRepository categoriaRepository) {
        this.equipamentoRepository = equipamentoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // "3" -> equipamento de id 3 (texto vazio -> nenhum, e a validação avisa se for obrigatório)
        registry.addConverter(String.class, Equipamento.class,
                id -> id.isBlank() ? null : equipamentoRepository.findById(Long.valueOf(id)).orElse(null));

        // "3" -> categoria de id 3 (texto vazio -> nenhum, e a validação avisa se for obrigatório)
        registry.addConverter(String.class, Categoria.class,
                id -> id.isBlank() ? null : categoriaRepository.findById(Long.valueOf(id)).orElse(null));
    }
}
