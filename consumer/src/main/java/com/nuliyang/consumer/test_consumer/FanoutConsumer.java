package com.nuliyang.consumer.test_consumer;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutConsumer {


    @RabbitListener(queues = "fanout.queue1")
    void testFanoutConsumer1(String msg) {
        System.out.println("消费者1号收到fanout.queue1：" + msg);
    }

    @RabbitListener(queues = "fanout.queue2")
    void testFanoutConsumer2(String msg) {
        System.out.println("消费者2号fanout.queue2：" + msg);
    }
}
