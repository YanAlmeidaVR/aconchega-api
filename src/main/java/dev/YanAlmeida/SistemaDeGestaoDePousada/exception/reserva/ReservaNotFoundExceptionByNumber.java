package dev.YanAlmeida.SistemaDeGestaoDePousada.exception.reserva;

public class ReservaNotFoundExceptionByNumber extends RuntimeException {
    public ReservaNotFoundExceptionByNumber(Integer number) {
      super("Número de quarto não encontrado. Número: " + number);
    }
}
