package io.github.curso.msavaliadorcredito.domain.model.SituacaoCliente;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DadosAvaliacao {

    private String cpf;
    private Long renda;
}
