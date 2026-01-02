package dev.YanAlmeida.SistemaDeGestaoDePousada.repository;

import dev.YanAlmeida.SistemaDeGestaoDePousada.entity.HospedeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HospedeRepository extends JpaRepository<HospedeModel, Long> {

    // Buscar por CPF
    Optional<HospedeModel> findByCpf(String cpf);

    // Verificar se CPF existe
    boolean existsByCpf(String cpf);

}