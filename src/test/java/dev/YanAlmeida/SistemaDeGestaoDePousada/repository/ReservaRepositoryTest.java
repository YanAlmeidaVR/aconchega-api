package dev.YanAlmeida.SistemaDeGestaoDePousada.repository;

import dev.YanAlmeida.SistemaDeGestaoDePousada.entity.ReservaModel;
import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.quarto.TipoQuarto;
import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.reserva.MetodoPagamento;
import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.reserva.StatusChave;
import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.reserva.StatusPagamento;
import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.reserva.StatusReserva;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ReservaRepositoryTest {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private EntityManager entityManager;

    // ========== TESTES PARA findByCpfHospede ==========

    @Test
    @DisplayName("Deve retornar reservas quando CPF do hóspede existe")
    void findByCpfHospedeSuccess(){
        // Arrange
        String cpf = "123.456.789-00";
        persistirReserva(criarReserva("João Silva", cpf, "11987654321", 101,
                LocalDate.of(2025, 1, 10), LocalDate.of(2025, 1, 15), StatusReserva.ATIVA));
        persistirReserva(criarReserva("João Silva", cpf, "11987654321", 102,
                LocalDate.of(2025, 2, 10), LocalDate.of(2025, 2, 15), StatusReserva.FINALIZADA));

        // Act
        List<ReservaModel> resultado = reservaRepository.findByCpfHospede(cpf);

        // Assert
        assertThat(resultado).hasSize(2);
        assertThat(resultado).allMatch(r -> r.getCpfHospede().equals(cpf));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando CPF não tem reservas")
    void findByCpfHospedeNotFound() {
        // Act
        List<ReservaModel> resultado = reservaRepository.findByCpfHospede("999.999.999-99");

        // Assert
        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar apenas reservas do CPF especificado")
    void findByCpfHospedeFilterCorrectly() {
        // Arrange
        persistirReserva(criarReserva("João Silva", "111.111.111-11", "11111111111", 101,
                LocalDate.of(2025, 1, 10), LocalDate.of(2025, 1, 15), StatusReserva.ATIVA));
        persistirReserva(criarReserva("Maria Santos", "222.222.222-22", "22222222222", 102,
                LocalDate.of(2025, 1, 10), LocalDate.of(2025, 1, 15), StatusReserva.ATIVA));

        // Act
        List<ReservaModel> resultado = reservaRepository.findByCpfHospede("111.111.111-11");

        // Assert
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNomeHospede()).isEqualTo("João Silva");
    }

    // ========== TESTES PARA findByNumeroQuarto ==========

    @Test
    @DisplayName("Deve retornar reservas para um número de quarto específico")
    void findByNumeroQuartoSuccess() {
        // Arrange
        Integer numeroQuarto = 101;
        persistirReserva(criarReserva("João Silva", "111.111.111-11", "11111111111", numeroQuarto,
                LocalDate.of(2025, 1, 10), LocalDate.of(2025, 1, 15), StatusReserva.ATIVA));
        persistirReserva(criarReserva("Maria Santos", "222.222.222-22", "22222222222", numeroQuarto,
                LocalDate.of(2025, 2, 10), LocalDate.of(2025, 2, 15), StatusReserva.FINALIZADA));

        // Act
        List<ReservaModel> resultado = reservaRepository.findByNumeroQuarto(numeroQuarto);

        // Assert
        assertThat(resultado).hasSize(2);
        assertThat(resultado).allMatch(r -> r.getNumeroQuarto().equals(numeroQuarto));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando quarto não tem reservas")
    void findByNumeroQuartoNotFound() {
        // Act
        List<ReservaModel> resultado = reservaRepository.findByNumeroQuarto(999);

        // Assert
        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar reservas de diferentes status para o mesmo quarto")
    void findByNumeroQuartoMultipleStatus() {
        // Arrange
        Integer numeroQuarto = 201;
        persistirReserva(criarReserva("Hospede 1", "111.111.111-11", "11111111111", numeroQuarto,
                LocalDate.of(2025, 1, 10), LocalDate.of(2025, 1, 15), StatusReserva.ATIVA));
        persistirReserva(criarReserva("Hospede 2", "222.222.222-22", "22222222222", numeroQuarto,
                LocalDate.of(2025, 2, 10), LocalDate.of(2025, 2, 15), StatusReserva.CANCELADA));
        persistirReserva(criarReserva("Hospede 3", "333.333.333-33", "33333333333", numeroQuarto,
                LocalDate.of(2025, 3, 10), LocalDate.of(2025, 3, 15), StatusReserva.FINALIZADA));

        // Act
        List<ReservaModel> resultado = reservaRepository.findByNumeroQuarto(numeroQuarto);

        // Assert
        assertThat(resultado).hasSize(3);
        assertThat(resultado).extracting(ReservaModel::getStatusReserva)
                .containsExactlyInAnyOrder(StatusReserva.ATIVA, StatusReserva.CANCELADA, StatusReserva.FINALIZADA);
    }

    // ========== TESTES PARA findByStatusReserva ==========

    @Test
    @DisplayName("Deve retornar todas as reservas ativas")
    void findByStatusReservaAtiva() {
        // Arrange
        persistirReserva(criarReserva("Hospede 1", "111.111.111-11", "11111111111", 101,
                LocalDate.of(2025, 1, 10), LocalDate.of(2025, 1, 15), StatusReserva.ATIVA));
        persistirReserva(criarReserva("Hospede 2", "222.222.222-22", "22222222222", 102,
                LocalDate.of(2025, 1, 10), LocalDate.of(2025, 1, 15), StatusReserva.ATIVA));
        persistirReserva(criarReserva("Hospede 3", "333.333.333-33", "33333333333", 103,
                LocalDate.of(2025, 1, 10), LocalDate.of(2025, 1, 15), StatusReserva.CANCELADA));

        // Act
        List<ReservaModel> resultado = reservaRepository.findByStatusReserva(StatusReserva.ATIVA);

        // Assert
        assertThat(resultado).hasSize(2);
        assertThat(resultado).allMatch(r -> r.getStatusReserva() == StatusReserva.ATIVA);
    }

    @Test
    @DisplayName("Deve retornar todas as reservas canceladas")
    void findByStatusReservaCancelada(){
        // Arrange
        persistirReserva(criarReserva("Hospede 1", "111.111.111-11", "11111111111", 101,
                LocalDate.of(2025, 1, 10), LocalDate.of(2025, 1, 15), StatusReserva.CANCELADA));
        persistirReserva(criarReserva("Hospede 2", "222.222.222-22", "22222222222", 102,
                LocalDate.of(2025, 1, 10), LocalDate.of(2025, 1, 15), StatusReserva.NAO_APARECEU));

        // Act
        List<ReservaModel> resultado = reservaRepository.findByStatusReserva(StatusReserva.CANCELADA);

        // Assert
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getStatusReserva()).isEqualTo(StatusReserva.CANCELADA);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há reservas com o status especificado")
    void findByStatusReservaNotFound(){
        // Arrange
        persistirReserva(criarReserva("Hospede 1", "111.111.111-11", "11111111111", 101,
                LocalDate.of(2025, 1, 10), LocalDate.of(2025, 1, 15), StatusReserva.ATIVA));

        // Act
        List<ReservaModel> resultado = reservaRepository.findByStatusReserva(StatusReserva.NAO_APARECEU);

        // Assert
        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar reservas finalizadas")
    void findByStatusReservaFinalizada() {
        // Arrange
        persistirReserva(criarReserva("Hospede 1", "111.111.111-11", "11111111111", 101,
                LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 10), StatusReserva.FINALIZADA));
        persistirReserva(criarReserva("Hospede 2", "222.222.222-22", "22222222222", 102,
                LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 10), StatusReserva.FINALIZADA));
        persistirReserva(criarReserva("Hospede 3", "333.333.333-33", "33333333333", 103,
                LocalDate.of(2025, 2, 1), LocalDate.of(2025, 2, 10), StatusReserva.ATIVA));

        // Act
        List<ReservaModel> resultado = reservaRepository.findByStatusReserva(StatusReserva.FINALIZADA);

        // Assert
        assertThat(resultado).hasSize(2);
    }

    // ========== TESTES PARA findByDataCheckIn ==========

    @Test
    @DisplayName("Deve retornar reservas com data de check-in específica")
    void findByDataCheckInSuccess(){
        // Arrange
        LocalDate dataCheckIn = LocalDate.of(2025, 1, 15);
        persistirReserva(criarReserva("Hospede 1", "111.111.111-11", "11111111111", 101,
                dataCheckIn, LocalDate.of(2025, 1, 20), StatusReserva.ATIVA));
        persistirReserva(criarReserva("Hospede 2", "222.222.222-22", "22222222222", 102,
                dataCheckIn, LocalDate.of(2025, 1, 20), StatusReserva.ATIVA));
        persistirReserva(criarReserva("Hospede 3", "333.333.333-33", "33333333333", 103,
                LocalDate.of(2025, 1, 16), LocalDate.of(2025, 1, 20), StatusReserva.ATIVA));

        // Act
        List<ReservaModel> resultado = reservaRepository.findByDataCheckIn(dataCheckIn);

        // Assert
        assertThat(resultado).hasSize(2);
        assertThat(resultado).allMatch(r -> r.getDataCheckIn().equals(dataCheckIn));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há check-ins na data especificada")
    void findByDataCheckInNotFound() {
        // Act
        List<ReservaModel> resultado = reservaRepository.findByDataCheckIn(LocalDate.of(2025, 12, 31));

        // Assert
        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("Deve buscar check-ins de hoje")
    void findByDataCheckInToday() {
        // Arrange
        LocalDate hoje = LocalDate.now();
        persistirReserva(criarReserva("Hospede Hoje", "111.111.111-11", "11111111111", 101,
                hoje, hoje.plusDays(5), StatusReserva.ATIVA));

        // Act
        List<ReservaModel> resultado = reservaRepository.findByDataCheckIn(hoje);

        // Assert
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getDataCheckIn()).isEqualTo(hoje);
    }

    // ========== TESTES PARA findByDataCheckOut ==========

    @Test
    @DisplayName("Deve retornar reservas com data de check-out específica")
    void findByDataCheckOutSuccess(){
        // Arrange
        LocalDate dataCheckOut = LocalDate.of(2025, 1, 20);
        persistirReserva(criarReserva("Hospede 1", "111.111.111-11", "11111111111", 101,
                LocalDate.of(2025, 1, 15), dataCheckOut, StatusReserva.ATIVA));
        persistirReserva(criarReserva("Hospede 2", "222.222.222-22", "22222222222", 102,
                LocalDate.of(2025, 1, 15), dataCheckOut, StatusReserva.ATIVA));
        persistirReserva(criarReserva("Hospede 3", "333.333.333-33", "33333333333", 103,
                LocalDate.of(2025, 1, 15), LocalDate.of(2025, 1, 21), StatusReserva.ATIVA));

        // Act
        List<ReservaModel> resultado = reservaRepository.findByDataCheckOut(dataCheckOut);

        // Assert
        assertThat(resultado).hasSize(2);
        assertThat(resultado).allMatch(r -> r.getDataCheckOut().equals(dataCheckOut));
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há check-outs na data especificada")
    void findByDataCheckOutNotFound() {
        // Act
        List<ReservaModel> resultado = reservaRepository.findByDataCheckOut(LocalDate.of(2025, 12, 31));

        // Assert
        assertThat(resultado).isEmpty();
    }

    @Test
    @DisplayName("Deve buscar check-outs de hoje")
    void findByDataCheckOutToday() {
        // Arrange
        LocalDate hoje = LocalDate.now();
        persistirReserva(criarReserva("Hospede Check-out Hoje", "111.111.111-11", "11111111111", 101,
                hoje.minusDays(5), hoje, StatusReserva.ATIVA));

        // Act
        List<ReservaModel> resultado = reservaRepository.findByDataCheckOut(hoje);

        // Assert
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getDataCheckOut()).isEqualTo(hoje);
    }

    // ========== TESTES DE INTEGRAÇÃO E EDGE CASES ==========

    @Test
    @DisplayName("Deve salvar reserva completa com todos os campos")
    void saveShouldPersistReservaCompleta() {
        // Arrange
        ReservaModel reserva = criarReservaCompleta();

        // Act
        ReservaModel salva = reservaRepository.save(reserva);

        // Assert
        assertThat(salva.getId()).isNotNull();
        assertThat(salva.getNomeHospede()).isEqualTo("João Silva");
        assertThat(salva.getValorTotal()).isEqualByComparingTo(new BigDecimal("500.00"));
        assertThat(salva.getMetodoPagamento()).isEqualTo(MetodoPagamento.CARTAO_CREDITO);
        assertThat(salva.getStatusPagamento()).isEqualTo(StatusPagamento.PAGO);
        assertThat(salva.getStatusChave()).isEqualTo(StatusChave.NAO_DEVOLVIDA);
    }

    @Test
    @DisplayName("Deve atualizar status da reserva")
    void updateStatusReserva() {
        // Arrange
        ReservaModel reserva = criarReserva("João Silva", "111.111.111-11", "11111111111", 101,
                LocalDate.of(2025, 1, 10), LocalDate.of(2025, 1, 15), StatusReserva.ATIVA);
        reserva = persistirReserva(reserva);

        // Act
        reserva.setStatusReserva(StatusReserva.FINALIZADA);
        reserva.setStatusPagamento(StatusPagamento.PAGO);
        reservaRepository.save(reserva);
        entityManager.flush();
        entityManager.clear();

        // Assert
        ReservaModel atualizada = reservaRepository.findById(reserva.getId()).orElseThrow();
        assertThat(atualizada.getStatusReserva()).isEqualTo(StatusReserva.FINALIZADA);
        assertThat(atualizada.getStatusPagamento()).isEqualTo(StatusPagamento.PAGO);
    }

    @Test
    @DisplayName("Deve buscar todas as reservas")
    void findAllShouldReturnAllReservas() {
        // Arrange
        persistirReserva(criarReserva("Hospede 1", "111.111.111-11", "11111111111", 101,
                LocalDate.of(2025, 1, 10), LocalDate.of(2025, 1, 15), StatusReserva.ATIVA));
        persistirReserva(criarReserva("Hospede 2", "222.222.222-22", "22222222222", 102,
                LocalDate.of(2025, 1, 10), LocalDate.of(2025, 1, 15), StatusReserva.FINALIZADA));
        persistirReserva(criarReserva("Hospede 3", "333.333.333-33", "33333333333", 103,
                LocalDate.of(2025, 1, 10), LocalDate.of(2025, 1, 15), StatusReserva.CANCELADA));

        // Act
        List<ReservaModel> reservas = reservaRepository.findAll();

        // Assert
        assertThat(reservas).hasSize(3);
    }

    @Test
    @DisplayName("Deve deletar reserva por ID")
    void deleteShouldRemoveReserva() {
        // Arrange
        ReservaModel reserva = criarReserva("João Silva", "111.111.111-11", "11111111111", 101,
                LocalDate.of(2025, 1, 10), LocalDate.of(2025, 1, 15), StatusReserva.CANCELADA);
        reserva = persistirReserva(reserva);
        Long id = reserva.getId();

        // Act
        reservaRepository.deleteById(id);
        entityManager.flush();

        // Assert
        assertThat(reservaRepository.findById(id)).isEmpty();
    }

    // ========== MÉTODOS AUXILIARES ==========

    private ReservaModel criarReserva(String nome, String cpf, String telefone, Integer numeroQuarto,
                                      LocalDate checkIn, LocalDate checkOut, StatusReserva status) {
        ReservaModel reserva = new ReservaModel();
        reserva.setNomeHospede(nome);
        reserva.setCpfHospede(cpf);
        reserva.setTelefoneHospede(telefone);
        reserva.setNumeroQuarto(numeroQuarto);
        reserva.setTipoQuarto(TipoQuarto.CASAL);
        reserva.setDataCheckIn(checkIn);
        reserva.setDataCheckOut(checkOut);
        reserva.setValorTotal(new BigDecimal("300.00"));
        reserva.setStatusReserva(status);
        reserva.setStatusPagamento(StatusPagamento.PENDENTE);
        reserva.setMetodoPagamento(MetodoPagamento.PIX);
        reserva.setStatusChave(StatusChave.NAO_DEVOLVIDA);
        return reserva;
    }

    private ReservaModel criarReservaCompleta() {
        ReservaModel reserva = new ReservaModel();
        reserva.setNomeHospede("João Silva");
        reserva.setCpfHospede("123.456.789-00");
        reserva.setTelefoneHospede("11987654321");
        reserva.setNumeroQuarto(101);
        reserva.setTipoQuarto(TipoQuarto.TRIPLA);
        reserva.setDataCheckIn(LocalDate.of(2025, 1, 10));
        reserva.setDataCheckOut(LocalDate.of(2025, 1, 15));
        reserva.setValorTotal(new BigDecimal("500.00"));
        reserva.setStatusReserva(StatusReserva.ATIVA);
        reserva.setStatusPagamento(StatusPagamento.PAGO);
        reserva.setMetodoPagamento(MetodoPagamento.CARTAO_CREDITO);
        reserva.setStatusChave(StatusChave.NAO_DEVOLVIDA);
        return reserva;
    }

    private ReservaModel persistirReserva(ReservaModel reserva) {
        entityManager.persist(reserva);
        entityManager.flush();
        entityManager.clear();
        return reserva;
    }
}