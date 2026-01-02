package dev.YanAlmeida.SistemaDeGestaoDePousada.exception.hospede;

public class CpfJaCadastradoException extends RuntimeException {

  public CpfJaCadastradoException(String cpf) {
    super("CPF já cadastrado: " + cpf);
  }
}
