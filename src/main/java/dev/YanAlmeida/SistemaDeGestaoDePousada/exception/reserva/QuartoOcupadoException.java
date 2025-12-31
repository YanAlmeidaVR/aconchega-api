package dev.YanAlmeida.SistemaDeGestaoDePousada.exception.reserva;

import java.time.LocalDate;

public class QuartoOcupadoException extends BusinessException {

  public QuartoOcupadoException(Integer numeroQuarto, LocalDate inicio, LocalDate fim){
    super(
            "Quarto " + numeroQuarto +
                    " já está ocupado entre " + inicio + " e " + fim
    );
  }
}
