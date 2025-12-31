package dev.YanAlmeida.SistemaDeGestaoDePousada.dto.quarto;

import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.quarto.TipoQuarto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor

public class QuartoCreateDTO {

    @NotNull(message = "Número do quarto é obrigatório")
    private Integer numero;

    @NotNull(message = "Tipo do quarto é obrigatório")
    private TipoQuarto tipo;

    @NotNull(message = "Preço por noite é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "Preço deve ser maior que zero")
    private BigDecimal precoPorNoite;

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public TipoQuarto getTipo() {
        return tipo;
    }

    public void setTipo(TipoQuarto tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getPrecoPorNoite() {
        return precoPorNoite;
    }

    public void setPrecoPorNoite(BigDecimal precoPorNoite) {
        this.precoPorNoite = precoPorNoite;
    }
}