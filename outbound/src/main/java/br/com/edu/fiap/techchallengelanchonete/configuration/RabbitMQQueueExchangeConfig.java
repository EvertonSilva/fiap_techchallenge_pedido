package br.com.edu.fiap.techchallengelanchonete.configuration;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQQueueExchangeConfig {

    @Bean
    public Queue queuePedidosParaParamento() {
        return new Queue("NOVOS_PEDIDOS_PARA_PAGAMENTO", false);
    }

    @Bean
    public Queue queuePedidosParaPreparacao() {
        return new Queue("NOVOS_PEDIDOS_PARA_PREPARACAO", false);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("NOVOS_PEDIDOS_EXCHANGE");
    }
}