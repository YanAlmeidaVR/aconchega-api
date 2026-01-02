package dev.YanAlmeida.SistemaDeGestaoDePousada.exception.quarto;

public class NumeroQuartoJaCadastradoException extends RuntimeException {

    public NumeroQuartoJaCadastradoException(Integer numero) {
        super("Número de quarto já cadastrado: " + numero);
    }
}

