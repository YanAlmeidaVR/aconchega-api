package dev.YanAlmeida.SistemaDeGestaoDePousada.dto.reserva;

import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.quarto.TipoQuarto;
import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.reserva.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservaResponseDTO {

    private Long id;

    private String nomeHospede;
    private String cpfHospede;
    private String telefoneHospede;

    private Integer numeroQuarto;
    private TipoQuarto tipoQuarto;

    private LocalDate dataCheckIn;
    private LocalDate dataCheckOut;
    private BigDecimal valorTotal;

    private StatusReserva statusReserva;
    private MetodoPagamento metodoPagamento;
    private StatusPagamento statusPagamento;
    private StatusChave statusChave;

}