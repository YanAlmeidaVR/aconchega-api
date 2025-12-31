package dev.YanAlmeida.SistemaDeGestaoDePousada.exception.reserva;

public class ReservaNotFoundExceptionById extends BusinessException {

  public ReservaNotFoundExceptionById(Long id) {
    super("Reserva não encontrada. ID: " + id);
  }
}
