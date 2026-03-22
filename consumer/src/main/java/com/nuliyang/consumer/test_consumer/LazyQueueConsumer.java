package com.nuliyang.consumer.test_consumer;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Slf4j(topic = "LazyQueueConsumer")
public class LazyQueueConsumer {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(
                    value = "lazy.queue",
                    arguments = @Argument(name = "x-queue-mode", value = "lazy")
            ),
            exchange = @Exchange(value = "lazy.exchange", type = "direct"),
            key = "lazy.key"
    ))
    void testLazyQueueConsumer(Message msg) {
        log.info("消费者收到lazy.queue：{}", msg);
    }
}
