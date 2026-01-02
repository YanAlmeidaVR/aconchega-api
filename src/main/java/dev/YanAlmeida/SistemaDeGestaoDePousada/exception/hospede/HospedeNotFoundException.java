package dev.YanAlmeida.SistemaDeGestaoDePousada.exception.hospede;

public class HospedeNotFoundException extends RuntimeException {

  public HospedeNotFoundException(Long id) {
    super("Hóspede não encontrado com id: " + id);
  }
}
