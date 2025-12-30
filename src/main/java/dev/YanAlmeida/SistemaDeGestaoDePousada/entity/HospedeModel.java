package dev.YanAlmeida.SistemaDeGestaoDePousada.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_hospede")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HospedeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome_hospede")
    private String nomeHospede;

    @Column(name = "telefone_hospede")
    private String telefone;

    @Column(name = "cpf",unique = true,length = 14)
    private String cpf;
}
