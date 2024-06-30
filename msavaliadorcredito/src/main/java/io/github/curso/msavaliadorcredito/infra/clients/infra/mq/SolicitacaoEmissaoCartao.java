package io.github.curso.msavaliadorcredito.infra.clients.infra.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.curso.msavaliadorcredito.domain.model.SituacaoCliente.DadosSolicitacaoEmissaoCartao;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SolicitacaoEmissaoCartao {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queueEmissaoCartao;



    public void solicitarCartao(DadosSolicitacaoEmissaoCartao dados) throws JsonProcessingException {
        var json = convertToJson(dados);
        rabbitTemplate.convertAndSend(queueEmissaoCartao.getName(), json);
    }

    private String convertToJson(DadosSolicitacaoEmissaoCartao dados) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(dados);
        return json;
    }
}
