package br.senai.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senai.sistema.model.Equipamento;

/**
 * Acesso ao banco de dados para a entidade Equipamento.
 *
 * Basta declarar a interface: o Spring Data cria a implementação sozinho,
 * com métodos prontos como findAll, findById, save, deleteById e count.
 * JpaRepository<Equipamento, Long>  ->  entidade Equipamento, chave primária do tipo Long.
 */
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
}