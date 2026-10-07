package br.senai.sistema.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Disponibiliza valores para TODAS as páginas.
 *
 * O nome do sistema vem de application.yml (app: nome:) e aparece
 * no menu e no título das páginas. Altere lá para o nome do SEU sistema.
 */
@ControllerAdvice
public class AtributosGlobais {

    @Value("${app.nome:Meu Sistema}")
    private String nomeDoSistema;

    @ModelAttribute("appNome")
    public String appNome() {
        return nomeDoSistema;
    }
}
