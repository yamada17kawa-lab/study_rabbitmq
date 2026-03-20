package com.nuliyang.consumer;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConsumerApplicationTests {

    @Test
    @RabbitListener(queues = "simple.queue")
    void contextLoads(String msg) {

        System.out.println("消费者接收到的消息是：" + msg);
    }

}
