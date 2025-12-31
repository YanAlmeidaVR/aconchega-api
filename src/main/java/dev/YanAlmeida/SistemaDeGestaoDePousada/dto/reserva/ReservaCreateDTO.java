package dev.YanAlmeida.SistemaDeGestaoDePousada.dto.reserva;

import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.reserva.MetodoPagamento;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor

public class ReservaCreateDTO {

    @NotNull(message = "ID do hóspede é obrigatório")
    private Long hospedeId;

    @NotNull(message = "Número do quarto é obrigatório")
    private Integer numeroQuarto;

    @NotNull(message = "Data de check-in é obrigatória")
    private LocalDate dataCheckIn;

    @NotNull(message = "Data de check-out é obrigatória")
    private LocalDate dataCheckOut;

    @NotNull(message = "Método de pagamento é obrigatório")
    private MetodoPagamento metodoPagamento;

    public Long getHospedeId() {
        return hospedeId;
    }

    public void setHospedeId(Long hospedeId) {
        this.hospedeId = hospedeId;
    }

    public Integer getNumeroQuarto() {
        return numeroQuarto;
    }

    public void setNumeroQuarto(Integer numeroQuarto) {
        this.numeroQuarto = numeroQuarto;
    }

    public LocalDate getDataCheckIn() {
        return dataCheckIn;
    }

    public void setDataCheckIn(LocalDate dataCheckIn) {
        this.dataCheckIn = dataCheckIn;
    }

    public LocalDate getDataCheckOut() {
        return dataCheckOut;
    }

    public void setDataCheckOut(LocalDate dataCheckOut) {
        this.dataCheckOut = dataCheckOut;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }
}