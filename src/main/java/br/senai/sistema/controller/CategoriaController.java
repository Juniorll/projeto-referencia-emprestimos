package br.senai.sistema.controller;

<<<<<<< HEAD
=======
import org.springframework.dao.DataIntegrityViolationException;
>>>>>>> 383f0626a23a6d0a3155dcf87426ed09ef6d006e
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sistema.model.Categoria;
import br.senai.sistema.service.CategoriaService;
import jakarta.validation.Valid;

/**
 * Controller de Categoria: recebe as requisições do navegador para /categorias.
 */
@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    /** GET /categorias -> mostra a lista. */
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaService.listarTodos());
        return "categorias/lista";
    }

    /** GET /categorias/novo -> abre o formulário vazio. */
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categorias/form";
    }

    /**
     * POST /categorias/salvar -> recebe o formulário (cadastro ou edição).
     * @Valid confere as regras da entidade; os erros ficam em "resultado".
     */
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("categoria") Categoria categoria, BindingResult resultado,
                         RedirectAttributes atributos) {
        if (resultado.hasErrors()) {
            return "categorias/form";   // volta para o formulário mostrando os erros
        }
        categoriaService.salvar(categoria);
        atributos.addFlashAttribute("mensagem", "Categoria salva com sucesso.");
        return "redirect:/categorias";
    }

    /** GET /categorias/5/editar -> abre o formulário preenchido com o registro de id 5. */
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("categoria", categoriaService.buscarPorId(id));
        return "categorias/form";
    }

<<<<<<< HEAD
    /** POST /categorias/5/excluir -> exclui o registro de id 5. */
    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes atributos) {
        categoriaService.excluir(id);
        atributos.addFlashAttribute("mensagem", "Categoria excluída com sucesso.");
=======
    /**
     * POST /categorias/5/excluir -> exclui o registro de id 5.
     * Se houver registros ligados a ele, o banco recusa a exclusão (integridade referencial)
     * e o sistema mostra um aviso, em vez de uma página de erro.
     */
    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes atributos) {
        try {
            categoriaService.excluir(id);
            atributos.addFlashAttribute("mensagem", "Categoria excluída com sucesso.");
        } catch (DataIntegrityViolationException e) {
            atributos.addFlashAttribute("erro", "Não é possível excluir: existem equipamentos ligados a esta categoria.");
        }
>>>>>>> 383f0626a23a6d0a3155dcf87426ed09ef6d006e
        return "redirect:/categorias";
    }
}
