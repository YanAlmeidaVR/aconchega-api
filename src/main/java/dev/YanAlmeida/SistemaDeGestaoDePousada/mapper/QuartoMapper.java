package dev.YanAlmeida.SistemaDeGestaoDePousada.mapper;

import dev.YanAlmeida.SistemaDeGestaoDePousada.dto.quarto.QuartoCreateDTO;
import dev.YanAlmeida.SistemaDeGestaoDePousada.dto.quarto.QuartoResponseDTO;
import dev.YanAlmeida.SistemaDeGestaoDePousada.entity.QuartoModel;
import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.quarto.QuartoStatus;

public class QuartoMapper {

    // Converte CreateDTO -> Entity
    public static QuartoModel toEntity(QuartoCreateDTO dto){
        QuartoModel quarto = new QuartoModel();
        quarto.setNumeroQuarto(dto.getNumero());
        quarto.setTipoQuarto(dto.getTipo());
        quarto.setPreçoPorNoite(dto.getPrecoPorNoite());
        quarto.setQuartoStatus(QuartoStatus.DISPONIVEL); // Status padrão
        return quarto;
    }

    // Converte Entity -> ResponseDTO
    public static QuartoResponseDTO toResponseDTO(QuartoModel quarto){
        QuartoResponseDTO dto = new QuartoResponseDTO();
        dto.setId(quarto.getId());
        dto.setNumero(quarto.getNumeroQuarto());
        dto.setTipo(quarto.getTipoQuarto());
        dto.setPrecoPorNoite(quarto.getPreçoPorNoite());
        dto.setStatus(quarto.getQuartoStatus());
        return dto;
    }
}
