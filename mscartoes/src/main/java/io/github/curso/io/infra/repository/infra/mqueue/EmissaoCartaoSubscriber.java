package io.github.curso.io.infra.repository.infra.mqueue;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.curso.io.domain.Cartao;
import io.github.curso.io.domain.ClienteCartao;
import io.github.curso.io.domain.DadosSolicitacaoEmissaoCartao;
import io.github.curso.io.infra.repository.CartaoRepository;
import io.github.curso.io.infra.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmissaoCartaoSubscriber {

    private final CartaoRepository cartaoRepository;
    private final ClienteCartaoRepository clienteCartaoRepository;

    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")
    public void receberSolicitacaoEmissao(@Payload String payload){

        try{
            var mapper = new ObjectMapper();
          DadosSolicitacaoEmissaoCartao dados = mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);
          Cartao cartao = cartaoRepository.findById(dados.getId()).orElseThrow();
            ClienteCartao clienteCartao = new ClienteCartao();
            clienteCartao.setCartao(cartao);
            clienteCartao.setCpf(dados.getCpf());
            clienteCartao.setLimite(dados.getLimiteCartao());

            clienteCartaoRepository.save(clienteCartao);

        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
    }
}
