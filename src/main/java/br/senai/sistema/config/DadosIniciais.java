package br.senai.sistema.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.senai.sistema.model.Equipamento;
import br.senai.sistema.model.Perfil;
import br.senai.sistema.model.Usuario;
import br.senai.sistema.repository.EquipamentoRepository;
import br.senai.sistema.repository.UsuarioRepository;

/**
 * Carga inicial de dados: executa uma vez sempre que a aplicação sobe.
 * Só insere os dados se a tabela ainda estiver vazia (por isso não duplica).
 */
@Component
public class DadosIniciais implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final EquipamentoRepository equipamentoRepository;

    public DadosIniciais(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder,
                         EquipamentoRepository equipamentoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.equipamentoRepository = equipamentoRepository;
    }

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            usuarioRepository.save(new Usuario("Administrador", "admin",
                    passwordEncoder.encode("admin123"), Perfil.ADMIN));
            usuarioRepository.save(new Usuario("Operador", "operador",
                    passwordEncoder.encode("operador123"), Perfil.OPERADOR));
        }

        if (equipamentoRepository.count() == 0) {
            Equipamento equipamento1 = new Equipamento("PAT-0007", "Furadeira de impacto", "Bosch", LocalDate.of(2024, 3, 15));
            Equipamento equipamento2 = new Equipamento("PAT-0012", "Multímetro digital", "Minipa", LocalDate.of(2025, 2, 10));
            Equipamento equipamento3 = new Equipamento("PAT-0031", "Osciloscópio", "Tektronix", LocalDate.of(2023, 11, 28));
            equipamentoRepository.save(equipamento1);
            equipamentoRepository.save(equipamento2);
            equipamentoRepository.save(equipamento3);
        }
    }
}