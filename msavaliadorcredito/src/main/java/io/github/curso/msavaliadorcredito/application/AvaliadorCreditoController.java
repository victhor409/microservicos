package io.github.curso.msavaliadorcredito.application;

import io.github.curso.msavaliadorcredito.application.AvaliadorCreditoService.AvaliadorCreditoService;


import io.github.curso.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import io.github.curso.msavaliadorcredito.application.ex.ErroComunicacaoMicroservicesException;
import io.github.curso.msavaliadorcredito.domain.model.SituacaoCliente.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorCreditoController {

    private final AvaliadorCreditoService avaliadorCreditoService;



    @GetMapping
    public String status(){
        return "ok";
    }


    @GetMapping(value = "situacao-cliente", params = "cpf")
    public ResponseEntity consultaSituacaoCliente(@RequestParam("cpf") String cpf){

        try{
            SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
            return ResponseEntity.ok(situacaoCliente);
        }catch(DadosClienteNotFoundException e){
            e.getStackTrace();
            return ResponseEntity.notFound().build();
        }catch (ErroComunicacaoMicroservicesException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }

    }

    @PostMapping
    public ResponseEntity realizarAvaliacaoCredito(@RequestBody DadosAvaliacao dados){

        try{
            RetornoAvaliacaoCliente retornoAvaliacaoCliente = avaliadorCreditoService.retornoAvaliacaoCliente(dados.getCpf(), dados.getRenda());
            return ResponseEntity.ok(retornoAvaliacaoCliente);
        }catch(DadosClienteNotFoundException e){
            e.getStackTrace();
            return ResponseEntity.notFound().build();
        }catch (ErroComunicacaoMicroservicesException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping("solicitacoes-cartao")
    public ResponseEntity solicitarCartao(@RequestBody DadosSolicitacaoEmissaoCartao dados){
        try{
            ProtocoloSolicitacaoCartao protocoloSolicitacaoCartao = avaliadorCreditoService.solicitarEmissaoCartao(dados);
            return ResponseEntity.ok().body(protocoloSolicitacaoCartao);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


}
