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

public class QuartoResponseDTO {

    private Long id;
    private Integer numero;
    private TipoQuarto tipo;
    private BigDecimal precoPorNoite;
    private QuartoStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public QuartoStatus getStatus() {
        return status;
    }

    public void setStatus(QuartoStatus status) {
        this.status = status;
    }
}
