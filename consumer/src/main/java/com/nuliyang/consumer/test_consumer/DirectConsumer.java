package com.nuliyang.consumer.test_consumer;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class DirectConsumer {


    @RabbitListener(queues = "direct.queue1")
    void testDirectConsumer1(String msg) {
        System.out.println("消费者1号收到direct.queue1：" + msg);
    }

    @RabbitListener(queues = "direct.queue2")
    void testDirectConsumer2(String msg) {
        System.out.println("消费者2号收到direct.queue2：" + msg);
    }
}
