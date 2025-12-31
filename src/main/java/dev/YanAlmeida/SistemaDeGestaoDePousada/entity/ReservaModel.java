package dev.YanAlmeida.SistemaDeGestaoDePousada.entity;

import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.reserva.*;
import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.quarto.TipoQuarto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_reserva")
@AllArgsConstructor
@NoArgsConstructor

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeHospede() {
        return nomeHospede;
    }

    public void setNomeHospede(String nomeHospede) {
        this.nomeHospede = nomeHospede;
    }

    public String getCpfHospede() {
        return cpfHospede;
    }

    public void setCpfHospede(String cpfHospede) {
        this.cpfHospede = cpfHospede;
    }

    public String getTelefoneHospede() {
        return telefoneHospede;
    }

    public void setTelefoneHospede(String telefoneHospede) {
        this.telefoneHospede = telefoneHospede;
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

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public StatusReserva getStatusReserva() {
        return statusReserva;
    }

    public void setStatusReserva(StatusReserva statusReserva) {
        this.statusReserva = statusReserva;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(StatusPagamento statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public StatusChave getStatusChave() {
        return statusChave;
    }

    public void setStatusChave(StatusChave statusChave) {
        this.statusChave = statusChave;
    }
}