package dev.YanAlmeida.SistemaDeGestaoDePousada.repository;

import dev.YanAlmeida.SistemaDeGestaoDePousada.entity.ReservaModel;
import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.reserva.StatusReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaModel, Long> {

    List<ReservaModel> findByCpfHospede(String cpfHospede);

    List<ReservaModel> findByNumeroQuarto(Integer numeroQuarto);

    List<ReservaModel> findByStatusReserva(StatusReserva statusReserva);

    List<ReservaModel> findByDataCheckIn(LocalDate dataCheckIn);

    List<ReservaModel> findByDataCheckOut(LocalDate dataCheckOut);

}