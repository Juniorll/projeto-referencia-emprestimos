package br.senai.sistema.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuração de segurança (login e permissões).
 *
 * ESTADO INICIAL: DESLIGADA. Até o Encontro 7 todas as páginas ficam liberadas,
 * para você se concentrar no desenvolvimento das funcionalidades.
 *
 * Para LIGAR a segurança (Encontro 7), altere em application.yml:
 *     app: seguranca: ativa: true
 * e ajuste as regras do bloco "if (ativa)" para as páginas do seu sistema.
 */
@Configuration
public class SecurityConfig {

    @Value("${app.seguranca.ativa:false}")
    private boolean ativa;

    @Bean
    public SecurityFilterChain filtroDeSeguranca(HttpSecurity http) throws Exception {

        if (ativa) {
            http.authorizeHttpRequests(auth -> auth
                    // Arquivos públicos e páginas que não exigem login
                    .requestMatchers("/css/**", "/js/**", "/img/**", "/login", "/diagnostico").permitAll()
                    .requestMatchers(PathRequest.toH2Console()).permitAll()
                    // Exemplo de página restrita a um perfil (ajuste no Encontro 7)
                    .requestMatchers("/usuarios/**").hasRole("ADMIN")
                    // Todo o resto exige estar logado
                    .anyRequest().authenticated());
        } else {
            // Segurança desligada: tudo liberado
            http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        }

        http
            // Página de login personalizada (templates/login.html)
            .formLogin(form -> form
                    .loginPage("/login")
                    .defaultSuccessUrl("/", true)
                    .permitAll())
            .logout(logout -> logout
                    .logoutSuccessUrl("/login?saiu")
                    .permitAll())
            // O console do H2 usa recursos que precisam destas duas liberações
            .csrf(csrf -> csrf.ignoringRequestMatchers(PathRequest.toH2Console()))
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }

    /** Algoritmo usado para criptografar e conferir senhas. */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
