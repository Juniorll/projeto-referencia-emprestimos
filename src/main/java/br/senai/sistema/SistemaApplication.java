package br.senai.sistema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Ponto de partida do sistema.
 *
 * A anotação @SpringBootApplication liga a configuração automática do Spring Boot:
 * ele procura as classes deste pacote (e dos subpacotes), cria os objetos necessários
 * e sobe um servidor web (Tomcat) na porta 8080.
 *
 * Para executar: no terminal, rode  mvn spring-boot:run
 * (ou clique em "Run" acima do método main, no VS Code / IntelliJ).
 */
@SpringBootApplication
public class SistemaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SistemaApplication.class, args);
    }
}
