package com.nuliyang.consumer.test_consumer;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Service
public class SimpleConsumer {



    @RabbitListener(queues = "simple.queue")
    void testSimpleConsumer(String msg) {

        System.out.println("消费者接收到的消息是：" + msg);
    }
}
