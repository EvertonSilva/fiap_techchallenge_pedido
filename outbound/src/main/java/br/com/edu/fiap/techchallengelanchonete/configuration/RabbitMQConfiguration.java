package br.com.edu.fiap.techchallengelanchonete.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Bean
    public Queue myQueue() {
        return new Queue("NOVOS_PEDIDOS", false);
    }
}