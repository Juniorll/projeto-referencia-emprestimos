package br.senai.sistema.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

/**
 * Entidade Equipamento: representa o equipamento que o sistema guarda.
 *
 * Cada objeto desta classe vira UMA LINHA da tabela "equipamentos" no banco.
 * As anotações de validação (@NotBlank, @Size...) definem as regras dos campos do formulário.
 * Os atributos com @ManyToOne guardam, no banco, só o id do registro relacionado (chave estrangeira).
 */
@Entity
@Table(name = "equipamentos")
public class Equipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Número de patrimônio (não se repete). */
    @NotBlank(message = "Campo obrigatório.")
    @Size(max = 20, message = "Use no máximo 20 caracteres.")
    @Column(nullable = false, unique = true, length = 20)
    private String patrimonio;

    /** O que é o equipamento. */
    @NotBlank(message = "Campo obrigatório.")
    @Size(max = 100, message = "Use no máximo 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String descricao;

    /** Fabricante. */
    @Size(max = 50, message = "Use no máximo 50 caracteres.")
    @Column(length = 50)
    private String marca;

    /** Data de compra. */
    @NotNull(message = "Campo obrigatório.")
    @PastOrPresent(message = "A data não pode ser futura.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(nullable = false)
    private LocalDate dataAquisicao;

    /** Categoria do equipamento (opcional): relacionamento N:1 (muitos registros daqui apontam para uma Categoria). */
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    /** Construtor vazio: obrigatório para o JPA e para o formulário criarem objetos. */
    public Equipamento() {
    }

    /** Construtor com os dados principais: facilita criar objetos no código. */
    public Equipamento(String patrimonio, String descricao, String marca, LocalDate dataAquisicao) {
        this.patrimonio = patrimonio;
        this.descricao = descricao;
        this.marca = marca;
        this.dataAquisicao = dataAquisicao;
    }

    // ---- Getters e setters ----

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(String patrimonio) {
        this.patrimonio = patrimonio;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public LocalDate getDataAquisicao() {
        return dataAquisicao;
    }

    public void setDataAquisicao(LocalDate dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}