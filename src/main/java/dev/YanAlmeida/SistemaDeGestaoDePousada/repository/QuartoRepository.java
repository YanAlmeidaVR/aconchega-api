package dev.YanAlmeida.SistemaDeGestaoDePousada.repository;

import dev.YanAlmeida.SistemaDeGestaoDePousada.entity.QuartoModel;
import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.quarto.QuartoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuartoRepository extends JpaRepository<QuartoModel, Long> {

    // Buscar por número
    Optional<QuartoModel> findByNumeroQuarto(Integer numeroQuarto);

    // Verificar se já existe
    boolean existsByNumeroQuarto(Integer numeroQuarto);

    // Buscar por status
    List<QuartoModel> findByQuartoStatus(QuartoStatus status);
}