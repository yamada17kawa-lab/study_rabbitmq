package com.nuliyang.consumer.config;


import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class JavaRabbitConfig2 {


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "config.queue2"),
            exchange = @Exchange(value = "config.exchange2", type = ExchangeTypes.TOPIC),
            key = "config.2.#"
    ))
    void testConfigConsumer2(String msg) {
        System.out.println("消费者2号收到config.queue2：" + msg);
    }
}
