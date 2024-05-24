package io.github.curso.io.application.representation;

import io.github.curso.io.domain.BandeiraCartao;
import io.github.curso.io.domain.Cartao;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartaoSaveRequest {

    private String nome;
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limite;


    public Cartao toModel(){
        return new Cartao(nome, bandeira, renda, limite);
    }



}
