package br.senai.sistema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.senai.sistema.service.EquipamentoService;

/**
 * Controller de Equipamento: recebe as requisições do navegador para /equipamentos.
 */
@Controller
@RequestMapping("/equipamentos")
public class EquipamentoController {

    private final EquipamentoService equipamentoService;

    public EquipamentoController(EquipamentoService equipamentoService) {
        this.equipamentoService = equipamentoService;
    }

    /** GET /equipamentos -> mostra a lista. */
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("equipamentos", equipamentoService.listarTodos());
        return "equipamentos/lista";
    }
}