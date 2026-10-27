package br.senai.sistema.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sistema.model.Equipamento;
import br.senai.sistema.service.CategoriaService;
import br.senai.sistema.service.EquipamentoService;
import jakarta.validation.Valid;

/**
 * Controller de Equipamento: recebe as requisições do navegador para /equipamentos.
 */
@Controller
@RequestMapping("/equipamentos")
public class EquipamentoController {

    private final EquipamentoService equipamentoService;
    private final CategoriaService categoriaService;

    public EquipamentoController(EquipamentoService equipamentoService,
                                  CategoriaService categoriaService) {
        this.equipamentoService = equipamentoService;
        this.categoriaService = categoriaService;
    }

    /** GET /equipamentos -> mostra a lista. */
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("equipamentos", equipamentoService.listarTodos());
        return "equipamentos/lista";
    }

    /** GET /equipamentos/novo -> abre o formulário vazio. */
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("equipamento", new Equipamento());
        carregarListas(model);
        return "equipamentos/form";
    }

    /**
     * POST /equipamentos/salvar -> recebe o formulário (cadastro ou edição).
     * @Valid confere as regras da entidade; os erros ficam em "resultado".
     */
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("equipamento") Equipamento equipamento, BindingResult resultado,
                         Model model, RedirectAttributes atributos) {
        if (resultado.hasErrors()) {
            carregarListas(model);
            return "equipamentos/form";   // volta para o formulário mostrando os erros
        }
        try {
            equipamentoService.salvar(equipamento);
        } catch (DataIntegrityViolationException e) {
            // O banco recusou: já existe outro registro com o mesmo patrimônio.
            resultado.rejectValue("patrimonio", "duplicado", "Já existe um registro com este patrimônio.");
            carregarListas(model);
            return "equipamentos/form";
        }
        atributos.addFlashAttribute("mensagem", "Equipamento salvo com sucesso.");
        return "redirect:/equipamentos";
    }

    /** GET /equipamentos/5/editar -> abre o formulário preenchido com o registro de id 5. */
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("equipamento", equipamentoService.buscarPorId(id));
        carregarListas(model);
        return "equipamentos/form";
    }

    /**
     * POST /equipamentos/5/excluir -> exclui o registro de id 5.
     * Se houver registros ligados a ele, o banco recusa a exclusão (integridade referencial)
     * e o sistema mostra um aviso, em vez de uma página de erro.
     */
    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes atributos) {
        try {
            equipamentoService.excluir(id);
            atributos.addFlashAttribute("mensagem", "Equipamento excluído com sucesso.");
        } catch (DataIntegrityViolationException e) {
            atributos.addFlashAttribute("erro", "Não é possível excluir: existem empréstimos ligados a este equipamento.");
        }
        return "redirect:/equipamentos";
    }

    /** Coloca no model as listas usadas nas listas de escolha (select) do formulário. */
    private void carregarListas(Model model) {
        model.addAttribute("categorias", categoriaService.listarTodos());
    }
}
