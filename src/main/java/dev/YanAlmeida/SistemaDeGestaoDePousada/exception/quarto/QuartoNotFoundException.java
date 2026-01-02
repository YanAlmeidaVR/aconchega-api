package dev.YanAlmeida.SistemaDeGestaoDePousada.exception.quarto;

public class QuartoNotFoundException extends RuntimeException {

    public QuartoNotFoundException(Integer numero) {
        super("Quarto não encontrado: " + numero);
    }
}

