package br.senai.sistema.config;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.senai.sistema.model.Categoria;
import br.senai.sistema.model.Emprestimo;
import br.senai.sistema.model.Equipamento;
import br.senai.sistema.model.Perfil;
import br.senai.sistema.model.SituacaoEmprestimo;
import br.senai.sistema.model.Usuario;
import br.senai.sistema.repository.CategoriaRepository;
import br.senai.sistema.repository.EmprestimoRepository;
import br.senai.sistema.repository.EquipamentoRepository;
import br.senai.sistema.repository.UsuarioRepository;

/**
 * Carga inicial de dados: executa uma vez sempre que a aplicação sobe.
 * Cada bloco só insere os dados se a tabela ainda estiver vazia (por isso não duplica).
 */
@Component
public class DadosIniciais implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EquipamentoRepository equipamentoRepository;
    private final CategoriaRepository categoriaRepository;
    private final EmprestimoRepository emprestimoRepository;

    public DadosIniciais(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                         EquipamentoRepository equipamentoRepository, CategoriaRepository categoriaRepository,
                         EmprestimoRepository emprestimoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.equipamentoRepository = equipamentoRepository;
        this.categoriaRepository = categoriaRepository;
        this.emprestimoRepository = emprestimoRepository;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            usuarioRepository.save(new Usuario("Administrador", "admin",
                    passwordEncoder.encode("admin123"), Perfil.ADMIN));
            usuarioRepository.save(new Usuario("Operador", "operador",
                    passwordEncoder.encode("operador123"), Perfil.OPERADOR));
        }

        if (categoriaRepository.count() == 0) {
            Categoria categoria1 = new Categoria("Ferramentas elétricas");
            Categoria categoria2 = new Categoria("Instrumentos de medição");
            Categoria categoria3 = new Categoria("Informática");
            categoriaRepository.save(categoria1);
            categoriaRepository.save(categoria2);
            categoriaRepository.save(categoria3);
        }

        if (equipamentoRepository.count() == 0) {
            Equipamento equipamento1 = new Equipamento("PAT-0007", "Furadeira de impacto", "Bosch", LocalDate.of(2024, 3, 15));
            Equipamento equipamento2 = new Equipamento("PAT-0012", "Multímetro digital", "Minipa", LocalDate.of(2025, 2, 10));
            Equipamento equipamento3 = new Equipamento("PAT-0031", "Osciloscópio", "Tektronix", LocalDate.of(2023, 11, 28));
            // Cada equipamento recebe uma categoria (relacionamento N:1)
            List<Categoria> categorias = categoriaRepository.findAll(Sort.by("id"));
            if (!categorias.isEmpty()) {
                equipamento1.setCategoria(categorias.get(0));
                equipamento2.setCategoria(categorias.get(1 % categorias.size()));
                equipamento3.setCategoria(categorias.get(1 % categorias.size()));
            }
            equipamentoRepository.save(equipamento1);
            equipamentoRepository.save(equipamento2);
            equipamentoRepository.save(equipamento3);
        }

        // Operação principal: cada registro aponta para registros que já existem (relacionamento).
        // O "%" (resto da divisão) evita erro se existirem menos de 3 registros cadastrados.
        if (emprestimoRepository.count() == 0) {
            List<Equipamento> equipamentos = equipamentoRepository.findAll(Sort.by("id"));
            if (!equipamentos.isEmpty()) {
<<<<<<< HEAD
                Emprestimo emprestimo1 = new Emprestimo("Prof. Marcos Lima", LocalDate.of(2026, 10, 14), LocalDate.of(2026, 10, 21),
                        equipamentos.get(0));
                Emprestimo emprestimo2 = new Emprestimo("Ana Souza (Turma DS)", LocalDate.of(2026, 10, 15), LocalDate.of(2026, 10, 17),
                        equipamentos.get(1 % equipamentos.size()));
                Emprestimo emprestimo3 = new Emprestimo("Laboratório de Eletrônica", LocalDate.of(2026, 10, 16), LocalDate.of(2026, 10, 23),
=======
                Emprestimo emprestimo1 = new Emprestimo("Prof. Marcos Lima", LocalDate.of(2026, 10, 14), LocalDate.of(2026, 10, 21), SituacaoEmprestimo.DEVOLVIDO,
                        equipamentos.get(0));
                emprestimo1.setDataDevolucao(LocalDate.of(2026, 10, 20));
                Emprestimo emprestimo2 = new Emprestimo("Ana Souza (Turma DS)", LocalDate.of(2026, 10, 15), LocalDate.of(2026, 10, 17), SituacaoEmprestimo.EMPRESTADO,
                        equipamentos.get(1 % equipamentos.size()));
                Emprestimo emprestimo3 = new Emprestimo("Laboratório de Eletrônica", LocalDate.of(2026, 10, 16), LocalDate.of(2026, 10, 23), SituacaoEmprestimo.EMPRESTADO,
>>>>>>> 383f0626a23a6d0a3155dcf87426ed09ef6d006e
                        equipamentos.get(2 % equipamentos.size()));
                emprestimoRepository.save(emprestimo1);
                emprestimoRepository.save(emprestimo2);
                emprestimoRepository.save(emprestimo3);
            }
        }
    }
}
