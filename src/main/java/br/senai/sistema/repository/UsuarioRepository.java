package br.senai.sistema.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senai.sistema.model.Usuario;

/**
 * Acesso ao banco de dados para a entidade Usuario.
 *
 * Basta declarar a interface: o Spring Data cria a implementação sozinho,
 * com métodos prontos como save, findAll, findById, deleteById e count.
 * O método findByLogin é gerado a partir do NOME do método.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByLogin(String login);
}
