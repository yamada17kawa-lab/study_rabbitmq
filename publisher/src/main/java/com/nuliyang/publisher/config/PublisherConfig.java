package com.nuliyang.publisher.config;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j(topic = "PublisherConfig")
public class PublisherConfig {

    final RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        //Confirm回调
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if(!ack){
                log.error("消息发送到交换机失败: {}", cause);
                if (correlationData != null){
                    log.error("消息失败的id: {}", correlationData.getId());
                }
            }
        });

        //Return回调
        rabbitTemplate.setReturnsCallback(returnMessage -> {
            log.error("""
                            消息发送到队列失败，返回code：{}，
                            返回内容：{}，
                            目标交换机：{}，
                            发送的消息：{}，
                            当前routingKey：{}""",
                    returnMessage.getReplyCode(),
                    returnMessage.getReplyText(),
                    returnMessage.getExchange(),
                    returnMessage.getMessage(),
                    returnMessage.getRoutingKey());
        });
    }

}
