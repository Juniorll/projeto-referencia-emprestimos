package br.senai.sistema.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import br.senai.sistema.model.Emprestimo;
import br.senai.sistema.model.Equipamento;
import br.senai.sistema.model.SituacaoEmprestimo;

/**
 * Acesso ao banco de dados para a entidade Emprestimo.
 *
 * Basta declarar a interface: o Spring Data cria a implementação sozinho,
 * com métodos prontos como findAll, findById, save, deleteById e count.
 * JpaRepository<Emprestimo, Long>  ->  entidade Emprestimo, chave primária do tipo Long.
 */
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {

    // Regra de negócio: empréstimos de um equipamento em uma situação (ex.: os que ainda estão emprestados)
    List<Emprestimo> findByEquipamentoAndSituacao(Equipamento equipamento, SituacaoEmprestimo situacao);

    // Filtro: registros de uma situação, na ordem indicada
    List<Emprestimo> findBySituacao(SituacaoEmprestimo situacao, Sort ordem);
}
