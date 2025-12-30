package dev.YanAlmeida.SistemaDeGestaoDePousada.dto.quarto;

import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.quarto.TipoQuarto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuartoCreateDTO {

    @NotNull(message = "Número do quarto é obrigatório")
    private Integer numero;

    @NotNull(message = "Tipo do quarto é obrigatório")
    private TipoQuarto tipo;

    @NotNull(message = "Preço por noite é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "Preço deve ser maior que zero")
    private BigDecimal precoPorNoite;
}