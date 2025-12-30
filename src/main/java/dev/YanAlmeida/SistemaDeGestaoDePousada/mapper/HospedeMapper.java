package dev.YanAlmeida.SistemaDeGestaoDePousada.mapper;

import dev.YanAlmeida.SistemaDeGestaoDePousada.dto.hospede.HospedeCreateDTO;
import dev.YanAlmeida.SistemaDeGestaoDePousada.dto.hospede.HospedeResponseDTO;
import dev.YanAlmeida.SistemaDeGestaoDePousada.entity.HospedeModel;

public class HospedeMapper {

    // Converte CreateDTO -> Entity
    public static HospedeModel toEntity(HospedeCreateDTO dto){
        HospedeModel hospede = new HospedeModel();
        hospede.setNomeHospede(dto.getNome());
        hospede.setTelefone(dto.getTelefone());
        hospede.setCpf(dto.getCpf());
        return hospede;
    }

    // Converte Entity -> ResponseDTO
    public static HospedeResponseDTO toResponseDTO(HospedeModel hospede){
        HospedeResponseDTO dto = new HospedeResponseDTO();
        dto.setId(hospede.getId());
        dto.setNome(hospede.getNomeHospede());
        dto.setTelefone(hospede.getTelefone());
        dto.setCpf(hospede.getCpf());
        return dto;
    }
}