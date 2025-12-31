package dev.YanAlmeida.SistemaDeGestaoDePousada.entity;

import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.quarto.QuartoStatus;
import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.quarto.TipoQuarto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_quarto")
@AllArgsConstructor
@NoArgsConstructor

public class QuartoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_quarto", nullable = false, unique = true)
    private Integer numeroQuarto;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_de_quarto", nullable = false)
    private TipoQuarto tipoQuarto;

    @Column(name = "preco_por_noite", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoPorNoite;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_quarto", nullable = false)
    private QuartoStatus quartoStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroQuarto() {
        return numeroQuarto;
    }

    public void setNumeroQuarto(Integer numeroQuarto) {
        this.numeroQuarto = numeroQuarto;
    }

    public TipoQuarto getTipoQuarto() {
        return tipoQuarto;
    }

    public void setTipoQuarto(TipoQuarto tipoQuarto) {
        this.tipoQuarto = tipoQuarto;
    }

    public BigDecimal getPrecoPorNoite() {
        return precoPorNoite;
    }

    public void setPrecoPorNoite(BigDecimal precoPorNoite) {
        this.precoPorNoite = precoPorNoite;
    }

    public QuartoStatus getQuartoStatus() {
        return quartoStatus;
    }

    public void setQuartoStatus(QuartoStatus quartoStatus) {
        this.quartoStatus = quartoStatus;
    }
}
