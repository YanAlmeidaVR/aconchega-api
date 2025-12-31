package dev.YanAlmeida.SistemaDeGestaoDePousada.dto.reserva;

import dev.YanAlmeida.SistemaDeGestaoDePousada.enums.reserva.MetodoPagamento;

public class PagamentoDTO {
    private MetodoPagamento metodoPagamento;

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }
}