package dev.YanAlmeida.SistemaDeGestaoDePousada.entity;

import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.reserva.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_reserva")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReservaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_hospede", nullable = false)
    private String hospede;

    @Column(name = "numero_quarto", nullable = false)
    private Integer quarto;

    @Column(name = "data_check_in")
    private LocalDate dataCheckIn;

    @Column(name = "data_check_out")
    private LocalDate dataCheckOut;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_reserva")
    private StatusReserva statusReserva;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pagamento")
    private StatusPagamento statusPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pagamento")
    private MetodoPagamento metodoPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_chave")
    private StatusChave statusChave;
}