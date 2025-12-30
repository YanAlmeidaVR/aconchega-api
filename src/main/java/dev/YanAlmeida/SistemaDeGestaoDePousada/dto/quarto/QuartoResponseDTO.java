package dev.YanAlmeida.SistemaDeGestaoDePousada.dto.quarto;

import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.quarto.QuartoStatus;
import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.quarto.TipoQuarto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuartoResponseDTO {

    private Long id;
    private Integer numero;
    private TipoQuarto tipo;
    private BigDecimal precoPorNoite;
    private QuartoStatus status;
}
