package br.senai.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senai.sistema.model.Emprestimo;

/**
 * Acesso ao banco de dados para a entidade Emprestimo.
 *
 * Basta declarar a interface: o Spring Data cria a implementação sozinho,
 * com métodos prontos como findAll, findById, save, deleteById e count.
 * JpaRepository<Emprestimo, Long>  ->  entidade Emprestimo, chave primária do tipo Long.
 */
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
}