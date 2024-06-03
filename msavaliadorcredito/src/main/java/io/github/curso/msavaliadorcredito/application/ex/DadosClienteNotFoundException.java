package io.github.curso.msavaliadorcredito.application.ex;

public class DadosClienteNotFoundException extends Exception{

    public DadosClienteNotFoundException(){
        super("Dados cliente nao encontrados");
    }
}
