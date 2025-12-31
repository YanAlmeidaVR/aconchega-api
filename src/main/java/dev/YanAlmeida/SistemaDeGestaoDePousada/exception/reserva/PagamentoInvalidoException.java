package dev.YanAlmeida.SistemaDeGestaoDePousada.exception.reserva;

public class PagamentoInvalidoException extends BusinessException {

    public PagamentoInvalidoException(String motivo) {
        super("Pagamento inválido: " + motivo);
    }

}
