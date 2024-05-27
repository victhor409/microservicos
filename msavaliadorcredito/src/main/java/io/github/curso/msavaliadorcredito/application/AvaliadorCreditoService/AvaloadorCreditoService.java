package io.github.curso.msavaliadorcredito.application.AvaliadorCreditoService;

import io.github.curso.msavaliadorcredito.domain.model.SituacaoCliente.DadosCliente;
import io.github.curso.msavaliadorcredito.domain.model.SituacaoCliente.SituacaoCliente;
import io.github.curso.msavaliadorcredito.infra.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaloadorCreditoService {


    private final ClienteResourceClient clienteResourceClient;


    public SituacaoCliente obterSituacaoCliente(String cpf){

        ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.dadosCliente(cpf);

        return SituacaoCliente.builder()
                .cliente(dadosClienteResponse.getBody())
                .build();
    }

}
