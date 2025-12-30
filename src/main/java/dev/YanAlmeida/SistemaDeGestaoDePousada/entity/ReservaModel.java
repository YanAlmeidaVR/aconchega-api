package dev.YanAlmeida.SistemaDeGestaoDePousada.entity;

import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.reserva.*;
import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.quarto.TipoQuarto;
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
    private String nomeHospede;

    @Column(name = "cpf_hospede", nullable = false, length = 14)
    private String cpfHospede;

    @Column(name = "telefone_hospede")
    private String telefoneHospede;

    @Column(name = "numero_quarto", nullable = false)
    private Integer numeroQuarto;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_quarto", nullable = false)
    private TipoQuarto tipoQuarto;

    @Column(name = "data_check_in", nullable = false)
    private LocalDate dataCheckIn;

    @Column(name = "data_check_out", nullable = false)
    private LocalDate dataCheckOut;

    @Column(name = "valor_total", precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_reserva", nullable = false)
    private StatusReserva statusReserva;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pagamento", nullable = false)
    private StatusPagamento statusPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pagamento")
    private MetodoPagamento metodoPagamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_chave", nullable = false)
    private StatusChave statusChave;
}