package dev.YanAlmeida.SistemaDeGestaoDePousada.repository;

import dev.YanAlmeida.SistemaDeGestaoDePousada.entity.ReservaModel;
import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.reserva.StatusReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<ReservaModel, Long> {

    List<ReservaModel> findByCpfHospede(String cpfHospede);

    List<ReservaModel> findByNumeroQuarto(Integer numeroQuarto);

    List<ReservaModel> findByStatusReserva(StatusReserva statusReserva);

    @Query("SELECT r FROM ReservaModel r WHERE r.dataCheckIn = :dataCheckIn")
    List<ReservaModel> findByDataCheckIn(@Param("dataCheckIn") LocalDate dataCheckIn);

    @Query("SELECT r FROM ReservaModel r WHERE r.dataCheckOut = :dataCheckOut")
    List<ReservaModel> findByDataCheckOut(@Param("dataCheckOut") LocalDate dataCheckOut);
}