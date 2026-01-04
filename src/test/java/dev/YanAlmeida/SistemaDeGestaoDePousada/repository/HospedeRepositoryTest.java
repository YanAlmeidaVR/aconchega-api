package dev.YanAlmeida.SistemaDeGestaoDePousada.repository;

import dev.YanAlmeida.SistemaDeGestaoDePousada.entity.HospedeModel;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class HospedeRepositoryTest {

    @Autowired
    private HospedeRepository hospedeRepository;

    @Autowired
    private EntityManager entityManager;

    // ========== TESTES PARA findByCpf ==========

    @Test
    @DisplayName("Deve retornar hóspede quando CPF existe")
    void findByCpfSuccess() {
        // Arrange
        HospedeModel hospede = criarHospede("João Silva", "11987654321", "123.456.789-00");
        persistirHospede(hospede);

        // Act
        Optional<HospedeModel> resultado = hospedeRepository.findByCpf("123.456.789-00");

        // Assert
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNomeHospede()).isEqualTo("João Silva");
        assertThat(resultado.get().getCpf()).isEqualTo("123.456.789-00");
        assertThat(resultado.get().getTelefone()).isEqualTo("11987654321");
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando CPF não existe")
    void findByCpfNotFound() {
        // Act
        Optional<HospedeModel> resultado = hospedeRepository.findByCpf("999.999.999-99");

        // Assert
        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("Deve buscar CPF com formatação diferente")
    void findByCpfDifferentFormat() {
        // Arrange
        HospedeModel hospede = criarHospede("Maria Santos", "11912345678", "111.222.333-44");
        persistirHospede(hospede);

        // Act
        Optional<HospedeModel> resultado = hospedeRepository.findByCpf("111.222.333-44");

        // Assert
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getCpf()).isEqualTo("111.222.333-44");
    }

    @Test
    @DisplayName("Deve retornar vazio quando CPF é nulo")
    void findByCpfNull() {
        // Act
        Optional<HospedeModel> resultado = hospedeRepository.findByCpf(null);

        // Assert
        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar vazio quando CPF é string vazia")
    void findByCpfEmpty() {
        // Act
        Optional<HospedeModel> resultado = hospedeRepository.findByCpf("");

        // Assert
        assertThat(resultado).isEmpty();
    }

    // ========== TESTES PARA existsByCpf ==========

    @Test
    @DisplayName("Deve retornar true quando CPF existe")
    void existsByCpfTrue() {
        // Arrange
        HospedeModel hospede = criarHospede("Carlos Oliveira", "11999887766", "555.666.777-88");
        persistirHospede(hospede);

        // Act
        boolean existe = hospedeRepository.existsByCpf("555.666.777-88");

        // Assert
        assertThat(existe).isTrue();
    }

    @Test
    @DisplayName("Deve retornar false quando CPF não existe")
    void existsByCpfFalse() {
        // Act
        boolean existe = hospedeRepository.existsByCpf("000.000.000-00");

        // Assert
        assertThat(existe).isFalse();
    }

    @Test
    @DisplayName("Deve retornar false quando CPF é nulo")
    void existsByCpfNull() {
        // Act
        boolean existe = hospedeRepository.existsByCpf(null);

        // Assert
        assertThat(existe).isFalse();
    }

    @Test
    @DisplayName("Deve verificar CPF após deletar hóspede")
    void existsByCpfAfterDelete() {
        // Arrange
        HospedeModel hospede = criarHospede("Ana Costa", "11955554444", "222.333.444-55");
        hospede = persistirHospede(hospede);
        String cpf = hospede.getCpf();

        // Act
        hospedeRepository.delete(hospede);
        entityManager.flush();
        boolean existe = hospedeRepository.existsByCpf(cpf);

        // Assert
        assertThat(existe).isFalse();
    }

    // ========== TESTES DE INTEGRAÇÃO JPA ==========

    @Test
    @DisplayName("Deve salvar hóspede com sucesso")
    void saveShouldPersistHospede() {
        // Arrange
        HospedeModel hospede = criarHospede("Pedro Almeida", "11944443333", "777.888.999-00");

        // Act
        HospedeModel salvo = hospedeRepository.save(hospede);

        // Assert
        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.getNomeHospede()).isEqualTo("Pedro Almeida");
    }

    @Test
    @DisplayName("Deve buscar todos os hóspedes")
    void findAllShouldReturnAllHospedes() {
        // Arrange
        persistirHospede(criarHospede("Hospede 1", "11111111111", "111.111.111-11"));
        persistirHospede(criarHospede("Hospede 2", "22222222222", "222.222.222-22"));
        persistirHospede(criarHospede("Hospede 3", "33333333333", "333.333.333-33"));

        // Act
        var hospedes = hospedeRepository.findAll();

        // Assert
        assertThat(hospedes).hasSize(3);
    }

    @Test
    @DisplayName("Deve atualizar hóspede existente")
    void updateShouldModifyHospede() {
        // Arrange
        HospedeModel hospede = criarHospede("Nome Original", "11900000000", "444.555.666-77");
        hospede = persistirHospede(hospede);

        // Act
        hospede.setNomeHospede("Nome Atualizado");
        hospede.setTelefone("11999999999");
        hospedeRepository.save(hospede);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Optional<HospedeModel> atualizado = hospedeRepository.findById(hospede.getId());
        assertThat(atualizado).isPresent();
        assertThat(atualizado.get().getNomeHospede()).isEqualTo("Nome Atualizado");
        assertThat(atualizado.get().getTelefone()).isEqualTo("11999999999");
    }

    @Test
    @DisplayName("Deve deletar hóspede por ID")
    void deleteShouldRemoveHospede() {
        // Arrange
        HospedeModel hospede = criarHospede("Deletar Teste", "11988887777", "888.999.000-11");
        hospede = persistirHospede(hospede);
        Long id = hospede.getId();

        // Act
        hospedeRepository.deleteById(id);
        entityManager.flush();

        // Assert
        Optional<HospedeModel> deletado = hospedeRepository.findById(id);
        assertThat(deletado).isEmpty();
    }

    // ========== MÉTODOS AUXILIARES ==========

    private HospedeModel criarHospede(String nome, String telefone, String cpf) {
        HospedeModel hospede = new HospedeModel();
        hospede.setNomeHospede(nome);
        hospede.setTelefone(telefone);
        hospede.setCpf(cpf);
        return hospede;
    }

    private HospedeModel persistirHospede(HospedeModel hospede) {
        entityManager.persist(hospede);
        entityManager.flush();
        entityManager.clear();
        return hospede;
    }
}