package dev.YanAlmeida.SistemaDeGestaoDePousada.exception.reserva;

public class ReservaJaCanceladaException extends BusinessException {

    public ReservaJaCanceladaException(){
        super("Reserva já está cancelada");
    }

}