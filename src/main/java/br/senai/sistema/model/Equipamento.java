package br.senai.sistema.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidade Equipamento: representa o equipamento que o sistema guarda.
 *
 * Cada objeto desta classe vira UMA LINHA da tabela "equipamentos" no banco.
 * Cada atributo vira UMA COLUNA dessa tabela.
 */
@Entity
@Table(name = "equipamentos")
public class Equipamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Número de patrimônio (não se repete). */
    @Column(nullable = false, unique = true, length = 20)
    private String patrimonio;

    /** O que é o equipamento. */
    @Column(nullable = false, length = 100)
    private String descricao;

    /** Fabricante. */
    @Column(length = 50)
    private String marca;

    /** Data de compra. */
    @Column(nullable = false)
    private LocalDate dataAquisicao;

    /** Construtor vazio: obrigatório para o JPA criar objetos ao ler do banco. */
    public Equipamento() {
    }

    /** Construtor com os dados principais: facilita criar objetos no código. */
    public Equipamento(String patrimonio, String descricao, String marca, LocalDate dataAquisicao) {
        this.patrimonio = patrimonio;
        this.descricao = descricao;
        this.marca = marca;
        this.dataAquisicao = dataAquisicao;
    }

    // ---- Getters e setters: a forma de ler e alterar os atributos privados ----

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
}