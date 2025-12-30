package dev.YanAlmeida.SistemaDeGestaoDePousada.dto.hospede;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HospedeCreateDTO {

    @NotBlank(message = "Nome do hóspede é obrigatório")
    private String nome;

    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;

    @NotBlank(message = "CPF é obrigatório")
    @CPF(message = "CPF inválido") // ← Validação automática!
    private String cpf;
}