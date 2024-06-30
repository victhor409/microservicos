package io.github.curso.msavaliadorcredito.application.AvaliadorCreditoService;

import feign.FeignException;
import io.github.curso.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import io.github.curso.msavaliadorcredito.application.ex.ErroComunicacaoMicroservicesException;
import io.github.curso.msavaliadorcredito.application.ex.ErrorSolicitacaoCartaoException;
import io.github.curso.msavaliadorcredito.domain.model.SituacaoCliente.*;
import io.github.curso.msavaliadorcredito.infra.clients.CartoesResourceClient;
import io.github.curso.msavaliadorcredito.infra.clients.ClienteResourceClient;
import io.github.curso.msavaliadorcredito.infra.clients.infra.mq.SolicitacaoEmissaoCartao;
import io.github.curso.msavaliadorcredito.infra.mqueue.SolicitacaoEmissaoCartaoPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {


    private final ClienteResourceClient clienteResourceClient;
    private final CartoesResourceClient cartoesCliente;
    private final SolicitacaoEmissaoCartaoPublisher solicitacaoEmissaoCartaoPublisher;


    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {

        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.dadosCliente(cpf);
            ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesCliente.getCartoesByCliente(cpf);

            return SituacaoCliente.builder()
                    .cliente(dadosClienteResponse.getBody())
                    .cartoes(cartoesResponse.getBody())
                    .build();
        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }

    }

    public RetornoAvaliacaoCliente retornoAvaliacaoCliente(String cpf,Long renda ) throws ErroComunicacaoMicroservicesException, DadosClienteNotFoundException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clienteResourceClient.dadosCliente(cpf);
            ResponseEntity<List<Cartao>> cartoesResponse = cartoesCliente.getCartoesRendaAteh(renda);

            List<Cartao> cartoes = cartoesResponse.getBody();
            var listaCartoesAprovados = cartoes.stream().map(cartao -> {

                DadosCliente dadosCliente =  dadosClienteResponse.getBody();

                BigDecimal limiteBasico = cartao.getLimiteBasico();
                BigDecimal rendaBD = BigDecimal.valueOf(renda);
                BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());
                var fator = idadeBD.divide(BigDecimal.valueOf(10));
                BigDecimal limiteAprovado = fator.multiply(limiteBasico);


                CartaoAprovado aprovado = new CartaoAprovado();
                aprovado.setCartao(cartao.getNome());
                aprovado.setBandeira(cartao.getBandeira());
                aprovado.setValorAprovado(limiteAprovado);

                return aprovado;
            }).collect(Collectors.toList());

            return new RetornoAvaliacaoCliente(listaCartoesAprovados);

        }catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }


    }

    public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dados){
        try{
            solicitacaoEmissaoCartaoPublisher.solicitarCartao(dados);
            var protocolo = UUID.randomUUID().toString();
            return new ProtocoloSolicitacaoCartao(protocolo);
        }catch (Exception e){
            throw new ErrorSolicitacaoCartaoException(e.getMessage());
        }

    }

}


