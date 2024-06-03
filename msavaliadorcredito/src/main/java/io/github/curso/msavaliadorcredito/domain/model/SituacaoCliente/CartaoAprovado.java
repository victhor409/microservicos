package io.github.curso.msavaliadorcredito.domain.model.SituacaoCliente;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoAprovado {

    private String cartao;
    private String bandeira;
    private BigDecimal valorAprovado;
}
