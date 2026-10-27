package br.senai.sistema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sistema.model.Emprestimo;
import br.senai.sistema.model.SituacaoEmprestimo;
import br.senai.sistema.service.EmprestimoService;
import br.senai.sistema.service.EquipamentoService;
import br.senai.sistema.service.RegraNegocioException;
import jakarta.validation.Valid;

/**
 * Controller de Emprestimo: recebe as requisições do navegador para /emprestimos.
 * Os erros de regra de negócio (RegraNegocioException) viram o aviso vermelho da tela.
 */
@Controller
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;
    private final EquipamentoService equipamentoService;

    public EmprestimoController(EmprestimoService emprestimoService,
                                 EquipamentoService equipamentoService) {
        this.emprestimoService = emprestimoService;
        this.equipamentoService = equipamentoService;
    }

    /** GET /emprestimos -> mostra a lista. Com ?situacao=..., mostra só os registros daquela situação. */
    @GetMapping
    public String listar(@RequestParam(required = false) SituacaoEmprestimo situacao, Model model) {
        model.addAttribute("emprestimos", emprestimoService.listar(situacao));
        model.addAttribute("situacoes", SituacaoEmprestimo.values());   // opções do filtro
        model.addAttribute("situacao", situacao);                  // mantém a opção escolhida no filtro
        return "emprestimos/lista";
    }

    /** GET /emprestimos/novo -> abre o formulário vazio. */
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("emprestimo", new Emprestimo());
        carregarListas(model);
        return "emprestimos/form";
    }

    /**
     * POST /emprestimos/salvar -> recebe o formulário.
     * Primeiro as validações dos campos (@Valid); depois, no service, as regras de negócio.
     */
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("emprestimo") Emprestimo emprestimo, BindingResult resultado,
                         Model model, RedirectAttributes atributos) {
        if (resultado.hasErrors()) {
            carregarListas(model);
            return "emprestimos/form";   // volta para o formulário mostrando os erros dos campos
        }
        try {
            emprestimoService.salvar(emprestimo);
            atributos.addFlashAttribute("mensagem", "Empréstimo salvo com sucesso.");
        } catch (RegraNegocioException e) {
            model.addAttribute("erro", e.getMessage());   // aparece no aviso vermelho, acima do formulário
            carregarListas(model);
            return "emprestimos/form";
        }
        return "redirect:/emprestimos";
    }

    /** GET /emprestimos/5/editar -> abre o formulário preenchido com o registro de id 5. */
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("emprestimo", emprestimoService.buscarPorId(id));
        carregarListas(model);
        return "emprestimos/form";
    }

    /** POST /emprestimos/5/excluir -> exclui o registro de id 5 (se as regras permitirem). */
    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes atributos) {
        try {
            emprestimoService.excluir(id);
            atributos.addFlashAttribute("mensagem", "Empréstimo excluído com sucesso.");
        } catch (RegraNegocioException e) {
            atributos.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/emprestimos";
    }

    /** POST /emprestimos/5/devolucao -> registra a devolução do empréstimo de id 5. */
    @PostMapping("/{id}/devolucao")
    public String devolver(@PathVariable Long id, RedirectAttributes atributos) {
        try {
            emprestimoService.registrarDevolucao(id);
            atributos.addFlashAttribute("mensagem", "Devolução registrada com sucesso.");
        } catch (RegraNegocioException e) {
            atributos.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/emprestimos";
    }

    /** Coloca no model as listas usadas nas listas de escolha (select) do formulário. */
    private void carregarListas(Model model) {
        model.addAttribute("equipamentos", equipamentoService.listarTodos());
    }
}
