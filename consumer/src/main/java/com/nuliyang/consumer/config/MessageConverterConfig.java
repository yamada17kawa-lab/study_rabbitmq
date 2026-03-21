package com.nuliyang.consumer.config;


import com.nuliyang.consumer.entity.MessageEntity;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConverterConfig {

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "message.queue1"),
            exchange = @Exchange(value = "message.exchange1", type = ExchangeTypes.TOPIC),
            key = "message.1.#"
    ))
    void testConfigConsumer2(MessageEntity msg) {
        System.out.println("自定义消息消费者收到message.queue1：" + msg);
    }
}
