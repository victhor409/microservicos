package io.github.curso.msavaliadorcredito.application.ex;

public class ErroComunicacaoMicroservicesException extends Exception{

    private Integer status;

    public ErroComunicacaoMicroservicesException(String mensagem, Integer status){
        super(mensagem);
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}
