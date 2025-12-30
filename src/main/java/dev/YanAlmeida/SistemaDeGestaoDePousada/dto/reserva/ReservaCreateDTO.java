package dev.YanAlmeida.SistemaDeGestaoDePousada.dto.reserva;

import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.reserva.MetodoPagamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservaCreateDTO {

    @NotBlank(message = "Nome do hóspede é obrigatório")
    private String nomeHospede;

    @NotBlank(message = "CPF do hóspede é obrigatório")
    private String cpfHospede;

    @NotNull(message = "Número do quarto é obrigatório")
    private Integer numeroQuarto;

    @NotNull(message = "Data de check-in é obrigatória")
    private LocalDate dataCheckIn;

    @NotNull(message = "Data de check-out é obrigatória")
    private LocalDate dataCheckOut;

    @NotNull(message = "Método de pagamento é obrigatório")
    private MetodoPagamento metodoPagamento;
}
