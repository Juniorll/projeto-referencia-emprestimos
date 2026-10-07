package br.senai.sistema.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.senai.sistema.service.DiagnosticoService;

/**
 * Controller: recebe as requisições do navegador e decide qual página mostrar.
 *
 * @Controller   -> o Spring reconhece esta classe como controller.
 * @GetMapping   -> associa um endereço (rota) a um método.
 * Model        -> "mochila" de dados que vai do Java para a página HTML.
 * return "..." -> nome do arquivo HTML em src/main/resources/templates.
 */
@Controller
public class InicioController {

    private final DiagnosticoService diagnosticoService;

    public InicioController(DiagnosticoService diagnosticoService) {
        this.diagnosticoService = diagnosticoService;
    }

    /**
     * Página inicial. Por enquanto mostra o diagnóstico do ambiente.
     * Nos próximos encontros ela virará o painel do seu sistema.
     */
    @GetMapping("/")
    public String inicio(Model model) {
        Map<String, DiagnosticoService.Item> itens = diagnosticoService.verificar();
        model.addAttribute("itens", itens.values());
        model.addAttribute("tudoOk", diagnosticoService.tudoOk(itens));
        return "index";
    }

    /** Página de diagnóstico do ambiente. */
    @GetMapping("/diagnostico")
    public String diagnostico(Model model) {
        Map<String, DiagnosticoService.Item> itens = diagnosticoService.verificar();
        model.addAttribute("itens", itens.values());
        model.addAttribute("tudoOk", diagnosticoService.tudoOk(itens));
        return "diagnostico";
    }

    /** Página de login (usada quando a segurança for ligada). */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
