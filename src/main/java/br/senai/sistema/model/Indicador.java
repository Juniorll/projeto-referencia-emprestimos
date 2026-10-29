package br.senai.sistema.model;

/**
 * Um indicador do painel: um número importante para quem gerencia o negócio.
 * Não é uma entidade (não vai para o banco): é só um objeto para levar os dados até a página.
 */
public class Indicador {

    private final String titulo;    // ex.: "Abaixo do mínimo"
    private final String valor;     // o número, já formatado (ex.: "3" ou "R$ 1.250,00")
    private final String detalhe;   // uma explicação curta
    private final String icone;     // ícone do Bootstrap Icons (ex.: "bi-box-seam")
    private final String cor;       // cor do Bootstrap (primary, success, warning, danger...)

    public Indicador(String titulo, String valor, String detalhe, String icone, String cor) {
        this.titulo = titulo;
        this.valor = valor;
        this.detalhe = detalhe;
        this.icone = icone;
        this.cor = cor;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getValor() {
        return valor;
    }

    public String getDetalhe() {
        return detalhe;
    }

    public String getIcone() {
        return icone;
    }

    public String getCor() {
        return cor;
    }
}
