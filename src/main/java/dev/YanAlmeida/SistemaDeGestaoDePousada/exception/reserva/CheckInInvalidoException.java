package dev.YanAlmeida.SistemaDeGestaoDePousada.exception.reserva;

public class CheckInInvalidoException extends BusinessException {
    public CheckInInvalidoException(String motivo){
        super("Check-in inválido: " + motivo);
    }
}
