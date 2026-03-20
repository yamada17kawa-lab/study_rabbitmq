package com.nuliyang.consumer.simple_consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static java.lang.Thread.sleep;


@Service
public class WorkConsumer {

    @RabbitListener(queues = "work.queue")
    void testWorkConsumer1(String msg) {
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("消费者1号：" + msg);
    }

    @RabbitListener(queues = "work.queue")
    void testWorkConsumer2(String msg) {
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("        消费者2号：" + msg);
    }
}
