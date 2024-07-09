package br.com.edu.fiap.techchallengelanchonete.messaging;

import br.com.edu.fiap.techchallengelanchonete.adapter.PedidoAdapter;
import br.com.edu.fiap.techchallengelanchonete.domain.Pedido;
import br.com.edu.fiap.techchallengelanchonete.infrastructure.IPedidoNovoPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class PedidoNovoPublisher implements IPedidoNovoPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private PedidoAdapter pedidoAdapter;
    @Override
    @Retryable(
            value = {Exception.class},
            maxAttempts = 4,
            backoff = @Backoff(delay = 2000, multiplier = 2)
    )
    public void publica(Pedido pedido) {
        var objectMapper = new ObjectMapper();
        var pedidoDTO = this.pedidoAdapter.toDTO(pedido);
        try {
            var mensagem = objectMapper.writeValueAsString(pedidoDTO);
            rabbitTemplate.convertAndSend("NOVOS_PEDIDOS_EXCHANGE", "", mensagem);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    @Recover
    public void falhaRetentativas(Exception e, Pedido pedido) {
        throw new RuntimeException("Falha ao gerar o pedido, serviço temporariamente indisponível");
    }
}
