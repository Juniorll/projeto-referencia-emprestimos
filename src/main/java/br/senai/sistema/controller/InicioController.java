package br.senai.sistema.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.senai.sistema.service.DiagnosticoService;
import br.senai.sistema.service.PainelService;

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
    private final PainelService painelService;

    public InicioController(DiagnosticoService diagnosticoService, PainelService painelService) {
        this.diagnosticoService = diagnosticoService;
        this.painelService = painelService;
    }

    /**
     * Página inicial: o PAINEL do sistema, com os indicadores do negócio.
     * (O diagnóstico do ambiente continua disponível em /diagnostico.)
     */
    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("indicadores", painelService.indicadores());
        model.addAttribute("atencao", painelService.atencao());
        model.addAttribute("agora", LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));
        model.addAttribute("graficoRotulos", painelService.graficoRotulos());
        model.addAttribute("graficoValores", painelService.graficoValores());
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
