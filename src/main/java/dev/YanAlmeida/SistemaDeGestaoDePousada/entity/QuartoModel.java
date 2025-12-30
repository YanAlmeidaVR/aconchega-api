package dev.YanAlmeida.SistemaDeGestaoDePousada.entity;

import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.quarto.QuartoStatus;
import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.quarto.TipoQuarto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_quarto")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuartoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_quarto")
    private Integer numeroQuarto;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoDeQuarto")
    private TipoQuarto tipoQuarto;

    @Column(name = "preço_por_noite")
    private BigDecimal preçoPorNoite;

    @Enumerated(EnumType.STRING)
    @Column(name = "statusQuarto")
    private QuartoStatus quartoStatus;


}
