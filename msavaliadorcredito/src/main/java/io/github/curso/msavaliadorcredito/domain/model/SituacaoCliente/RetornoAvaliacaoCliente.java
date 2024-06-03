package io.github.curso.msavaliadorcredito.domain.model.SituacaoCliente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RetornoAvaliacaoCliente {

    private List<CartaoAprovado> cartaoAprovados;


}

