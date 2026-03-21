package com.nuliyang.consumer.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class JavaRabbitConfig1 {

    @Bean
    public Queue configQueue1() {
        return new Queue("config.queue1");
    }

    @Bean
    public DirectExchange configExchange1() {
        return new DirectExchange("config.exchange1");
    }

    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(configQueue1()).to(configExchange1()).with("config.1");
    }

}
