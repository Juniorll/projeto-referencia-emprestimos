package br.senai.sistema.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Entidade Categoria: representa a categoria que o sistema guarda.
 *
 * Cada objeto desta classe vira UMA LINHA da tabela "categorias" no banco.
 * As anotações de validação (@NotBlank, @Size...) definem as regras dos campos do formulário.
 */
@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nome da categoria. */
    @NotBlank(message = "Campo obrigatório.")
    @Size(max = 60, message = "Use no máximo 60 caracteres.")
    @Column(nullable = false, length = 60)
    private String nome;

    /** Construtor vazio: obrigatório para o JPA e para o formulário criarem objetos. */
    public Categoria() {
    }

    /** Construtor com os dados principais: facilita criar objetos no código. */
    public Categoria(String nome) {
        this.nome = nome;
    }

    // ---- Getters e setters ----

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
