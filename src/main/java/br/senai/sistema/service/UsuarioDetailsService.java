package br.senai.sistema.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.senai.sistema.model.Usuario;
import br.senai.sistema.repository.UsuarioRepository;

/**
 * Ensina o Spring Security a encontrar um usuário no NOSSO banco de dados.
 *
 * Quando alguém tenta entrar, o Spring Security chama loadUserByUsername
 * com o login digitado. Nós buscamos o usuário e devolvemos seus dados
 * (senha criptografada e perfil). A comparação da senha é feita pelo Spring.
 *
 * Usado a partir do Encontro 7.
 */
@Service
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + login));

        return User.withUsername(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(usuario.getPerfil().name())
                .disabled(!usuario.isAtivo())
                .build();
    }
}
