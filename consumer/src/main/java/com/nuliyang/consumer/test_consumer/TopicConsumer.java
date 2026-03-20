package com.nuliyang.consumer.test_consumer;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TopicConsumer {


    @RabbitListener(queues = "japan.topic.queue")
    void testJapanTopicConsumer(String msg) {
        System.out.println("日本消费者收到japan.topic.queue：" + msg);
    }

    @RabbitListener(queues = "china.topic.queue")
    void testChinaTopicConsumer(String msg) {
        System.out.println("中国消费者收到china.topic.queue：" + msg);
    }

    @RabbitListener(queues = "weather.topic.queue")
    void testWeatherTopicConsumer(String msg) {
        System.out.println("天气消费者收到weather.topic.queue：" + msg);
    }

}
