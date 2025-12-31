package dev.YanAlmeida.SistemaDeGestaoDePousada.dto.reserva;

import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.quarto.TipoQuarto;
import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.reserva.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor

public class ReservaResponseDTO {

    private Long id;

    private String nomeHospede;
    private String cpfHospede;
    private String telefoneHospede;

    private Integer numeroQuarto;
    private TipoQuarto tipoQuarto;

    private LocalDate dataCheckIn;
    private LocalDate dataCheckOut;
    private BigDecimal valorTotal;

    private StatusReserva statusReserva;
    private MetodoPagamento metodoPagamento;
    private StatusPagamento statusPagamento;
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

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(StatusPagamento statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public StatusChave getStatusChave() {
        return statusChave;
    }

    public void setStatusChave(StatusChave statusChave) {
        this.statusChave = statusChave;
    }
}