package br.senai.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senai.sistema.model.Categoria;

/**
 * Acesso ao banco de dados para a entidade Categoria.
 *
 * Basta declarar a interface: o Spring Data cria a implementação sozinho,
 * com métodos prontos como findAll, findById, save, deleteById e count.
 * JpaRepository<Categoria, Long>  ->  entidade Categoria, chave primária do tipo Long.
 */
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}