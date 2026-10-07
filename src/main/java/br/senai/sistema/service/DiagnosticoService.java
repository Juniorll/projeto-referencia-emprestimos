package br.senai.sistema.service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootVersion;
import org.springframework.stereotype.Service;

import br.senai.sistema.repository.UsuarioRepository;

/**
 * Coleta informações do ambiente para a página de diagnóstico.
 * Serve para conferir, no primeiro encontro, se tudo está funcionando.
 */
@Service
public class DiagnosticoService {

    private final DataSource dataSource;
    private final UsuarioRepository usuarioRepository;

    @Value("${app.seguranca.ativa:false}")
    private boolean segurancaAtiva;

    public DiagnosticoService(DataSource dataSource, UsuarioRepository usuarioRepository) {
        this.dataSource = dataSource;
        this.usuarioRepository = usuarioRepository;
    }

    /** Resultado de uma verificação: nome, valor encontrado e se está OK. */
    public record Item(String nome, String valor, boolean ok) {
    }

    public Map<String, Item> verificar() {
        Map<String, Item> itens = new LinkedHashMap<>();

        String versaoJava = System.getProperty("java.version");
        int principal = Runtime.version().feature();
        itens.put("java", new Item("Java", versaoJava, principal >= 21));

        itens.put("spring", new Item("Spring Boot", SpringBootVersion.getVersion(), true));

        try (Connection conexao = dataSource.getConnection()) {
            DatabaseMetaData meta = conexao.getMetaData();
            itens.put("banco", new Item("Banco de dados",
                    meta.getDatabaseProductName() + " " + meta.getDatabaseProductVersion(), true));
        } catch (Exception e) {
            itens.put("banco", new Item("Banco de dados", "Falha na conexão: " + e.getMessage(), false));
        }

        try {
            long total = usuarioRepository.count();
            itens.put("tabelas", new Item("Tabelas e carga inicial", total + " usuário(s) cadastrado(s)", total > 0));
        } catch (Exception e) {
            itens.put("tabelas", new Item("Tabelas e carga inicial", "Erro: " + e.getMessage(), false));
        }

        boolean codespaces = "true".equalsIgnoreCase(System.getenv("CODESPACES"));
        itens.put("ambiente", new Item("Ambiente", codespaces ? "GitHub Codespaces" : "Computador local", true));

        long memoriaMb = Runtime.getRuntime().maxMemory() / (1024 * 1024);
        itens.put("memoria", new Item("Memória disponível para o Java", memoriaMb + " MB", memoriaMb >= 256));

        itens.put("seguranca", new Item("Segurança (login)",
                segurancaAtiva ? "Ligada" : "Desligada (será ligada no Encontro 7)", true));

        return itens;
    }

    public boolean tudoOk(Map<String, Item> itens) {
        return itens.values().stream().allMatch(Item::ok);
    }
}
