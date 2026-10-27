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
 * Entidade Emprestimo: representa o empréstimo que o sistema guarda.
 *
 * Cada objeto desta classe vira UMA LINHA da tabela "emprestimos" no banco.
 * As anotações de validação (@NotBlank, @Size...) definem as regras dos campos do formulário.
 * Os atributos com @ManyToOne guardam, no banco, só o id do registro relacionado (chave estrangeira).
 */
@Entity
@Table(name = "emprestimos")
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Quem retirou o equipamento. */
    @NotBlank(message = "Campo obrigatório.")
    @Size(max = 100, message = "Use no máximo 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String solicitante;

    /** Data da retirada. */
    @NotNull(message = "Campo obrigatório.")
    @PastOrPresent(message = "A data não pode ser futura.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(nullable = false)
    private LocalDate dataRetirada;

    /** Data combinada para a devolução. */
    @NotNull(message = "Campo obrigatório.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(nullable = false)
    private LocalDate dataPrevista;

    /** Equipamento emprestado: relacionamento N:1 (muitos registros daqui apontam para um Equipamento). */
    @NotNull(message = "Selecione um equipamento.")
    @ManyToOne
    @JoinColumn(name = "equipamento_id", nullable = false)
    private Equipamento equipamento;

    /** Construtor vazio: obrigatório para o JPA e para o formulário criarem objetos. */
    public Emprestimo() {
    }

    /** Construtor com os dados principais: facilita criar objetos no código. */
    public Emprestimo(String solicitante, LocalDate dataRetirada, LocalDate dataPrevista, Equipamento equipamento) {
        this.solicitante = solicitante;
        this.dataRetirada = dataRetirada;
        this.dataPrevista = dataPrevista;
        this.equipamento = equipamento;
    }

    // ---- Getters e setters ----

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public LocalDate getDataRetirada() {
        return dataRetirada;
    }

    public void setDataRetirada(LocalDate dataRetirada) {
        this.dataRetirada = dataRetirada;
    }

    public LocalDate getDataPrevista() {
        return dataPrevista;
    }

    public void setDataPrevista(LocalDate dataPrevista) {
        this.dataPrevista = dataPrevista;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }
}