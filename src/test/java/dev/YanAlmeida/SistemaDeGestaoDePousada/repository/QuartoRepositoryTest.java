package dev.YanAlmeida.SistemaDeGestaoDePousada.repository;

import dev.YanAlmeida.SistemaDeGestaoDePousada.entity.QuartoModel;
import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.quarto.QuartoStatus;
import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.quarto.TipoQuarto;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class QuartoRepositoryTest {

    @Autowired
    private QuartoRepository quartoRepository;

    @Autowired
    private EntityManager entityManager;

    // ========== TESTES PARA findByNumeroQuarto ==========

    @Test
    @DisplayName("Deve retornar quarto quando número existe")
    void findByNumeroQuartoSuccess() {
        // Arrange
        QuartoModel quarto = criarQuarto(101, TipoQuarto.SOLTEIRO, new BigDecimal("100.00"), QuartoStatus.DISPONIVEL);
        persistirQuarto(quarto);

        // Act
        Optional<QuartoModel> resultado = quartoRepository.findByNumeroQuarto(101);

        // Assert
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNumeroQuarto()).isEqualTo(101);
        assertThat(resultado.get().getTipoQuarto()).isEqualTo(TipoQuarto.SOLTEIRO);
        assertThat(resultado.get().getPrecoPorNoite()).isEqualByComparingTo(new BigDecimal("100.00"));
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando número não existe")
    void findByNumeroQuartoNotFound() {
        // Act
        Optional<QuartoModel> resultado = quartoRepository.findByNumeroQuarto(999);

        // Assert
        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar quarto correto quando existem vários quartos")
    void findByNumeroQuartoWithMultipleQuartos() {
        // Arrange
        persistirQuarto(criarQuarto(101, TipoQuarto.SOLTEIRO, new BigDecimal("100.00"), QuartoStatus.DISPONIVEL));
        persistirQuarto(criarQuarto(102, TipoQuarto.CASAL, new BigDecimal("150.00"), QuartoStatus.OCUPADO));
        persistirQuarto(criarQuarto(103, TipoQuarto.TRIPLA, new BigDecimal("200.00"), QuartoStatus.MANUTENÇÃO));

        // Act
        Optional<QuartoModel> resultado = quartoRepository.findByNumeroQuarto(102);

        // Assert
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNumeroQuarto()).isEqualTo(102);
        assertThat(resultado.get().getTipoQuarto()).isEqualTo(TipoQuarto.CASAL);
        assertThat(resultado.get().getQuartoStatus()).isEqualTo(QuartoStatus.OCUPADO);
    }

    @Test
    @DisplayName("Deve retornar vazio quando número do quarto é nulo")
    void findByNumeroQuartoNull() {
        // Act
        Optional<QuartoModel> resultado = quartoRepository.findByNumeroQuarto(null);

        // Assert
        assertThat(resultado).isEmpty();
    }

    // ========== TESTES PARA existsByNumeroQuarto ==========

    @Test
    @DisplayName("Deve retornar true quando número do quarto existe")
    void existsByNumeroQuartoTrue() {
        // Arrange
        persistirQuarto(criarQuarto(201, TipoQuarto.CASAL, new BigDecimal("150.00"), QuartoStatus.DISPONIVEL));

        // Act
        boolean existe = quartoRepository.existsByNumeroQuarto(201);

        // Assert
        assertThat(existe).isTrue();
    }

    @Test
    @DisplayName("Deve retornar false quando número do quarto não existe")
    void existsByNumeroQuartoFalse() {
        // Act
        boolean existe = quartoRepository.existsByNumeroQuarto(999);

        // Assert
        assertThat(existe).isFalse();
    }

    @Test
    @DisplayName("Deve retornar false quando número do quarto é nulo")
    void existsByNumeroQuartoNull() {
        // Act
        boolean existe = quartoRepository.existsByNumeroQuarto(null);

        // Assert
        assertThat(existe).isFalse();
    }

    @Test
    @DisplayName("Deve retornar true antes de deletar e false depois")
    void existsByNumeroQuartoAfterDelete() {
        // Arrange
        QuartoModel quarto = criarQuarto(301, TipoQuarto.TRIPLA, new BigDecimal("200.00"), QuartoStatus.DISPONIVEL);
        quarto = persistirQuarto(quarto);
        Integer numero = quarto.getNumeroQuarto();

        // Act & Assert - Antes de deletar
        assertThat(quartoRepository.existsByNumeroQuarto(numero)).isTrue();

        // Deletar
        quartoRepository.delete(quarto);
        entityManager.flush();

        // Act & Assert - Depois de deletar
        assertThat(quartoRepository.existsByNumeroQuarto(numero)).isFalse();
    }

    // ========== TESTES PARA findByQuartoStatus ==========

    @Test
    @DisplayName("Deve retornar todos os quartos disponíveis")
    void findByQuartoStatusDisponivel() {
        // Arrange
        persistirQuarto(criarQuarto(101, TipoQuarto.SOLTEIRO, new BigDecimal("100.00"), QuartoStatus.DISPONIVEL));
        persistirQuarto(criarQuarto(102, TipoQuarto.CASAL, new BigDecimal("150.00"), QuartoStatus.DISPONIVEL));
        persistirQuarto(criarQuarto(103, TipoQuarto.TRIPLA, new BigDecimal("200.00"), QuartoStatus.OCUPADO));
        persistirQuarto(criarQuarto(104, TipoQuarto.SOLTEIRO, new BigDecimal("100.00"), QuartoStatus.DISPONIVEL));

        // Act
        List<QuartoModel> resultado = quartoRepository.findByQuartoStatus(QuartoStatus.DISPONIVEL);

        // Assert
        assertThat(resultado).hasSize(3);
        assertThat(resultado).allMatch(q -> q.getQuartoStatus() == QuartoStatus.DISPONIVEL);
        assertThat(resultado).extracting(QuartoModel::getNumeroQuarto)
                .containsExactlyInAnyOrder(101, 102, 104);
    }

    @Test
    @DisplayName("Deve retornar todos os quartos ocupados")
    void findByQuartoStatusOcupado() {
        // Arrange
        persistirQuarto(criarQuarto(201, TipoQuarto.SOLTEIRO, new BigDecimal("100.00"), QuartoStatus.OCUPADO));
        persistirQuarto(criarQuarto(202, TipoQuarto.CASAL, new BigDecimal("150.00"), QuartoStatus.OCUPADO));
        persistirQuarto(criarQuarto(203, TipoQuarto.TRIPLA, new BigDecimal("200.00"), QuartoStatus.DISPONIVEL));

        // Act
        List<QuartoModel> resultado = quartoRepository.findByQuartoStatus(QuartoStatus.OCUPADO);

        // Assert
        assertThat(resultado).hasSize(2);
        assertThat(resultado).allMatch(q -> q.getQuartoStatus() == QuartoStatus.OCUPADO);
    }

    @Test
    @DisplayName("Deve retornar todos os quartos em manutenção")
    void findByQuartoStatusManutencao() {
        // Arrange
        persistirQuarto(criarQuarto(301, TipoQuarto.SOLTEIRO, new BigDecimal("100.00"), QuartoStatus.MANUTENÇÃO));
        persistirQuarto(criarQuarto(302, TipoQuarto.CASAL, new BigDecimal("150.00"), QuartoStatus.MANUTENÇÃO));
        persistirQuarto(criarQuarto(303, TipoQuarto.TRIPLA, new BigDecimal("200.00"), QuartoStatus.DISPONIVEL));

        // Act
        List<QuartoModel> resultado = quartoRepository.findByQuartoStatus(QuartoStatus.MANUTENÇÃO);

        // Assert
        assertThat(resultado).hasSize(2);
        assertThat(resultado).allMatch(q -> q.getQuartoStatus() == QuartoStatus.MANUTENÇÃO);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há quartos com o status especificado")
    void findByQuartoStatusEmpty() {
        // Arrange
        persistirQuarto(criarQuarto(401, TipoQuarto.SOLTEIRO, new BigDecimal("100.00"), QuartoStatus.DISPONIVEL));

        // Act
        List<QuartoModel> resultado = quartoRepository.findByQuartoStatus(QuartoStatus.OCUPADO);

        // Assert
        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar quartos de diferentes tipos com o mesmo status")
    void findByQuartoStatusMultipleTipos() {
        // Arrange
        persistirQuarto(criarQuarto(501, TipoQuarto.SOLTEIRO, new BigDecimal("80.00"), QuartoStatus.DISPONIVEL));
        persistirQuarto(criarQuarto(502, TipoQuarto.CASAL, new BigDecimal("120.00"), QuartoStatus.DISPONIVEL));
        persistirQuarto(criarQuarto(503, TipoQuarto.TRIPLA, new BigDecimal("180.00"), QuartoStatus.DISPONIVEL));

        // Act
        List<QuartoModel> resultado = quartoRepository.findByQuartoStatus(QuartoStatus.DISPONIVEL);

        // Assert
        assertThat(resultado).hasSize(3);
        assertThat(resultado).extracting(QuartoModel::getTipoQuarto)
                .containsExactlyInAnyOrder(TipoQuarto.SOLTEIRO, TipoQuarto.CASAL, TipoQuarto.TRIPLA);
    }

    // ========== TESTES DE INTEGRAÇÃO JPA ==========

    @Test
    @DisplayName("Deve salvar quarto com sucesso")
    void saveShouldPersistQuarto() {
        // Arrange
        QuartoModel quarto = criarQuarto(601, TipoQuarto.CASAL, new BigDecimal("150.00"), QuartoStatus.DISPONIVEL);

        // Act
        QuartoModel salvo = quartoRepository.save(quarto);

        // Assert
        assertThat(salvo.getId()).isNotNull();
        assertThat(salvo.getNumeroQuarto()).isEqualTo(601);
        assertThat(salvo.getTipoQuarto()).isEqualTo(TipoQuarto.CASAL);
        assertThat(salvo.getPrecoPorNoite()).isEqualByComparingTo(new BigDecimal("150.00"));
        assertThat(salvo.getQuartoStatus()).isEqualTo(QuartoStatus.DISPONIVEL);
    }

    @Test
    @DisplayName("Deve buscar todos os quartos")
    void findAllShouldReturnAllQuartos() {
        // Arrange
        persistirQuarto(criarQuarto(701, TipoQuarto.SOLTEIRO, new BigDecimal("100.00"), QuartoStatus.DISPONIVEL));
        persistirQuarto(criarQuarto(702, TipoQuarto.CASAL, new BigDecimal("150.00"), QuartoStatus.OCUPADO));
        persistirQuarto(criarQuarto(703, TipoQuarto.TRIPLA, new BigDecimal("200.00"), QuartoStatus.MANUTENÇÃO));

        // Act
        List<QuartoModel> quartos = quartoRepository.findAll();

        // Assert
        assertThat(quartos).hasSize(3);
        assertThat(quartos).extracting(QuartoModel::getNumeroQuarto)
                .containsExactlyInAnyOrder(701, 702, 703);
    }

    @Test
    @DisplayName("Deve atualizar status do quarto")
    void updateQuartoStatus() {
        // Arrange
        QuartoModel quarto = criarQuarto(801, TipoQuarto.CASAL, new BigDecimal("150.00"), QuartoStatus.DISPONIVEL);
        quarto = persistirQuarto(quarto);

        // Act
        quarto.setQuartoStatus(QuartoStatus.OCUPADO);
        quartoRepository.save(quarto);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Optional<QuartoModel> atualizado = quartoRepository.findById(quarto.getId());
        assertThat(atualizado).isPresent();
        assertThat(atualizado.get().getQuartoStatus()).isEqualTo(QuartoStatus.OCUPADO);
    }

    @Test
    @DisplayName("Deve atualizar preço do quarto")
    void updateQuartoPreco() {
        // Arrange
        QuartoModel quarto = criarQuarto(901, TipoQuarto.TRIPLA, new BigDecimal("200.00"), QuartoStatus.DISPONIVEL);
        quarto = persistirQuarto(quarto);

        // Act
        quarto.setPrecoPorNoite(new BigDecimal("250.00"));
        quartoRepository.save(quarto);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Optional<QuartoModel> atualizado = quartoRepository.findByNumeroQuarto(901);
        assertThat(atualizado).isPresent();
        assertThat(atualizado.get().getPrecoPorNoite()).isEqualByComparingTo(new BigDecimal("250.00"));
    }

    @Test
    @DisplayName("Deve deletar quarto por ID")
    void deleteShouldRemoveQuarto() {
        // Arrange
        QuartoModel quarto = criarQuarto(1001, TipoQuarto.SOLTEIRO, new BigDecimal("100.00"), QuartoStatus.DISPONIVEL);
        quarto = persistirQuarto(quarto);
        Long id = quarto.getId();

        // Act
        quartoRepository.deleteById(id);
        entityManager.flush();

        // Assert
        Optional<QuartoModel> deletado = quartoRepository.findById(id);
        assertThat(deletado).isEmpty();
    }

    @Test
    @DisplayName("Deve buscar quarto por ID")
    void findByIdShouldReturnQuarto() {
        // Arrange
        QuartoModel quarto = criarQuarto(1101, TipoQuarto.CASAL, new BigDecimal("150.00"), QuartoStatus.DISPONIVEL);
        quarto = persistirQuarto(quarto);
        Long id = quarto.getId();

        // Act
        Optional<QuartoModel> resultado = quartoRepository.findById(id);

        // Assert
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getId()).isEqualTo(id);
        assertThat(resultado.get().getNumeroQuarto()).isEqualTo(1101);
    }

    @Test
    @DisplayName("Deve contar total de quartos")
    void countShouldReturnTotalQuartos() {
        // Arrange
        persistirQuarto(criarQuarto(1201, TipoQuarto.SOLTEIRO, new BigDecimal("100.00"), QuartoStatus.DISPONIVEL));
        persistirQuarto(criarQuarto(1202, TipoQuarto.CASAL, new BigDecimal("150.00"), QuartoStatus.OCUPADO));
        persistirQuarto(criarQuarto(1203, TipoQuarto.TRIPLA, new BigDecimal("200.00"), QuartoStatus.DISPONIVEL));
        persistirQuarto(criarQuarto(1204, TipoQuarto.SOLTEIRO, new BigDecimal("100.00"), QuartoStatus.MANUTENÇÃO));

        // Act
        long total = quartoRepository.count();

        // Assert
        assertThat(total).isEqualTo(4);
    }

    // ========== TESTES DE VALIDAÇÃO DE DADOS ==========

    @Test
    @DisplayName("Deve salvar quarto com preço decimal preciso")
    void saveQuartoWithPreciseDecimal() {
        // Arrange
        QuartoModel quarto = criarQuarto(1301, TipoQuarto.CASAL, new BigDecimal("149.99"), QuartoStatus.DISPONIVEL);

        // Act
        QuartoModel salvo = quartoRepository.save(quarto);
        entityManager.flush();
        entityManager.clear();

        // Assert
        Optional<QuartoModel> recuperado = quartoRepository.findByNumeroQuarto(1301);
        assertThat(recuperado).isPresent();
        assertThat(recuperado.get().getPrecoPorNoite()).isEqualByComparingTo(new BigDecimal("149.99"));
    }

    @Test
    @DisplayName("Deve salvar quartos de todos os tipos")
    void saveQuartosAllTipos() {
        // Arrange & Act
        persistirQuarto(criarQuarto(1401, TipoQuarto.SOLTEIRO, new BigDecimal("80.00"), QuartoStatus.DISPONIVEL));
        persistirQuarto(criarQuarto(1402, TipoQuarto.CASAL, new BigDecimal("120.00"), QuartoStatus.DISPONIVEL));
        persistirQuarto(criarQuarto(1403, TipoQuarto.TRIPLA, new BigDecimal("180.00"), QuartoStatus.DISPONIVEL));

        // Assert
        List<QuartoModel> todos = quartoRepository.findAll();
        assertThat(todos).extracting(QuartoModel::getTipoQuarto)
                .contains(TipoQuarto.SOLTEIRO, TipoQuarto.CASAL, TipoQuarto.TRIPLA);
    }

    @Test
    @DisplayName("Deve salvar quartos com todos os status")
    void saveQuartosAllStatus() {
        // Arrange & Act
        persistirQuarto(criarQuarto(1501, TipoQuarto.SOLTEIRO, new BigDecimal("100.00"), QuartoStatus.DISPONIVEL));
        persistirQuarto(criarQuarto(1502, TipoQuarto.CASAL, new BigDecimal("150.00"), QuartoStatus.OCUPADO));
        persistirQuarto(criarQuarto(1503, TipoQuarto.TRIPLA, new BigDecimal("200.00"), QuartoStatus.MANUTENÇÃO));

        // Assert
        assertThat(quartoRepository.findByQuartoStatus(QuartoStatus.DISPONIVEL)).isNotEmpty();
        assertThat(quartoRepository.findByQuartoStatus(QuartoStatus.OCUPADO)).isNotEmpty();
        assertThat(quartoRepository.findByQuartoStatus(QuartoStatus.MANUTENÇÃO)).isNotEmpty();
    }

    // ========== MÉTODOS AUXILIARES ==========

    private QuartoModel criarQuarto(Integer numero, TipoQuarto tipo, BigDecimal preco, QuartoStatus status) {
        QuartoModel quarto = new QuartoModel();
        quarto.setNumeroQuarto(numero);
        quarto.setTipoQuarto(tipo);
        quarto.setPrecoPorNoite(preco);
        quarto.setQuartoStatus(status);
        return quarto;
    }

    private QuartoModel persistirQuarto(QuartoModel quarto) {
        entityManager.persist(quarto);
        entityManager.flush();
        entityManager.clear();
        return quarto;
    }
}