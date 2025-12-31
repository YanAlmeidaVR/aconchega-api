package dev.YanAlmeida.SistemaDeGestaoDePousada.dto.hospede;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@AllArgsConstructor
@NoArgsConstructor
public class HospedeCreateDTO {

    @NotBlank(message = "Nome do hóspede é obrigatório")
    private String nome;

    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;

    @NotBlank(message = "CPF é obrigatório")
    @CPF(message = "CPF inválido") // ← Validação automática!
    private String cpf;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}