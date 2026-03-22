package com.nuliyang.publisher;

import com.nuliyang.publisher.entity.MessageEntity;
import com.nuliyang.publisher.entity.People;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
@Slf4j(topic = "PublisherApplicationTests")
class PublisherApplicationTests {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    void testSimple() {
        String message = "我靠了老铁，什么时候可以去日本啊";

        String queueName = "simple.queue";


        rabbitTemplate.convertAndSend(queueName, message);
        log.info("消息发送成功：{}", message);
    }


    @Test
    void testWork() {

        String queueName = "work.queue";

        for (int i = 0; i < 50; i++) {
            int count = i+1;
            String message = "第" + count + "次来日本";
            rabbitTemplate.convertAndSend(queueName, message);
            log.info("消息发送成功：{}", message);
        }
    }

    @Test
    void testFanout() {
        String exchangeName = "fanout.exchange";

        for (int i = 0; i < 50; i++) {
            int count = i+1;
            String message = "第" + count + "次来日本";
            rabbitTemplate.convertAndSend(exchangeName, "", message);
            log.info("消息发送成功：{}", message);
        }
    }


    @Test
    void testDirect() {
        String exchangeName = "direct.exchange";

        //红色发给两个队列
        String redMessage = "我要去日本，red";

        //蓝色发给一个队列
        String blueMessage = "我要去日本，blue";

        rabbitTemplate.convertAndSend(exchangeName, "red", redMessage);
        log.info("红色消息发送成功：{}", redMessage);
        rabbitTemplate.convertAndSend(exchangeName, "blue", blueMessage);
        log.info("蓝色消息发送成功：{}", blueMessage);

    }

    @Test
    void testTopic() {
        String exchangeName = "topic.exchange";


        String message = "我要去日本";
        rabbitTemplate.convertAndSend(exchangeName, "japan.news", message);
        log.info("日本消息发送成功：{}", message);

        message = "我要去中国";
        rabbitTemplate.convertAndSend(exchangeName, "china.news", message);
        log.info("中国消息发送成功：{}", message);

        message = "今日は天気がいいですね、散歩しましょう！";
        rabbitTemplate.convertAndSend(exchangeName, "good.weather", message);
        log.info("天气消息发送成功：{}", message);
    }

    @Test
    void testRabbitMQConfig2() {

        String message = "这是RabbitM配置2的消息";

        String exchangeName = "config.exchange2";

        rabbitTemplate.convertAndSend(exchangeName, "config.2.wangwang", message);
        log.info("RabbitM配置2的消息发送成功：{}", message);
    }

    @Test
    void testMessageConverter() {
        People people = new People()
                .setAge(18)
                .setName("旺旺")
                .setAddress("小本子县")
                .setSex("男");

        MessageEntity message = new MessageEntity()
                .setId(100001L)
                .setMsg("这是自定义的消息，わかってるの？")
                .setPeople(people);

        String exchangeName = "message.exchange1";
        rabbitTemplate.convertAndSend(exchangeName, "message.1.wangwang", message);
        log.info("MessageConverter消息发送成功：{}", message);
    }

    @Test
    void testConfirmCallback(){
        String message = "这是ConfirmCallBack的消息";

        CorrelationData cd = new CorrelationData(UUID.randomUUID().toString());


        String exchangeName = "confirm.exchange";
        rabbitTemplate.convertAndSend(exchangeName, "confirm.wangwang", message,cd);
        log.info("ConfirmCallBack消息发送成功：{}", message);



    }

    @Test
    void testReturnCallback(){
        String message = "这是ReturnCallBack的消息";

        CorrelationData cd = new CorrelationData(UUID.randomUUID().toString());



        String exchangeName2 = "direct.exchange";
        rabbitTemplate.convertAndSend(exchangeName2, "nothing", message,cd);
        log.info("ReturnCallBack消息发送成功：{}", message);
    }

}
