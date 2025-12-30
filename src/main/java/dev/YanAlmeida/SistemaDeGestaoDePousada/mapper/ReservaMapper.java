package dev.YanAlmeida.SistemaDeGestaoDePousada.mapper;

import dev.YanAlmeida.SistemaDeGestaoDePousada.dto.reserva.ReservaResponseDTO;
import dev.YanAlmeida.SistemaDeGestaoDePousada.entity.ReservaModel;

public class ReservaMapper {

    // Converte Entity -> ResponseDTO
    public static ReservaResponseDTO toResponseDTO(ReservaModel reserva) {
        ReservaResponseDTO dto = new ReservaResponseDTO();

        dto.setId(reserva.getId());
        dto.setNomeHospede(reserva.getNomeHospede());
        dto.setCpfHospede(reserva.getCpfHospede());
        dto.setTelefoneHospede(reserva.getTelefoneHospede());
        dto.setNumeroQuarto(reserva.getNumeroQuarto());
        dto.setTipoQuarto(reserva.getTipoQuarto());
        dto.setDataCheckIn(reserva.getDataCheckIn());
        dto.setDataCheckOut(reserva.getDataCheckOut());
        dto.setValorTotal(reserva.getValorTotal());
        dto.setStatusReserva(reserva.getStatusReserva());
        dto.setMetodoPagamento(reserva.getMetodoPagamento());
        dto.setStatusPagamento(reserva.getStatusPagamento());
        dto.setStatusChave(reserva.getStatusChave());

        return dto;
    }
}