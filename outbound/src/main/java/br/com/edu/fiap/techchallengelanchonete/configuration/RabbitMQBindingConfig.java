package br.com.edu.fiap.techchallengelanchonete.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQBindingConfig {

    @Bean
    public Binding bindingPagamento(FanoutExchange fanoutExchange, Queue queuePedidosParaParamento) {
        return BindingBuilder.bind(queuePedidosParaParamento).to(fanoutExchange);
    }

    @Bean
    public Binding bindingPreparacao(FanoutExchange fanoutExchange, Queue queuePedidosParaPreparacao) {
        return BindingBuilder.bind(queuePedidosParaPreparacao).to(fanoutExchange);
    }
}