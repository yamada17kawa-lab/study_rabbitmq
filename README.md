# 🐇 RabbitMQ 核心开发笔记

### 1. 协议：AMQP
* **定义**：Advanced Message Queuing Protocol（高级消息队列协议）。
* **对比**：
    * **HTTP**：浏览器与服务器之间的短连接通信协议。
    * **AMQP**：应用服务与消息队列之间的长连接二进制协议。

### 2. 性能：连接模型 (Multiplexing)
* **物理层**：通过 **TCP** 与 RabbitMQ 服务器建立长连接（Connection）。
* **逻辑层**：在同一个 TCP 连接内部，通过 **Channel（信道/管道）** 进行通信。
* **核心优势**：
    * 降低资源损耗：避免了频繁创建和销毁 TCP 连接的巨大开销。
    * 提高并发：一个 Connection 可以支持成千上万个 Channel。

---

### 3. Spring Boot 基础配置
在 `application.yaml` 中配置连接信息：

```yaml
spring:
  rabbitmq:
    host: localhost           # 服务器地址
    port: 5672                # 服务端口（AMQP默认端口）
    username: hmall           # 用户名
    password: yee23           # 密码
    virtual-host: /hmall      # 虚拟主机（用于逻辑隔离，类似数据库名）
    
```

### 4. 流量控制：Prefetch (预取值)
* **背景**：默认情况下，MQ 会将队列消息全部推给消费者，可能导致消费者内存溢出（OOM）。

* **作用**：控制消费者在发送 确认（Ack） 之前，能接收的消息最大上限。

* **最佳实践**：设置 prefetch: 1。

* **实现“能者多劳”**：处理快的线程拿得多，处理慢的拿得少，避免负载不均。

* **保护机制**：确保当前消息没处理完之前，MQ 不会再给该线程推送新消息。

```yaml
spring:
  rabbitmq:
    listener:
      simple:
        prefetch: 1  # 每次拉取/预取的消息数量上限
```

### 5. 生产者 (Producer)
使用 `RabbitTemplate` 发送消息，主要有两种场景：

* **直连队列**：直接指定队列名发送（简单模式）。
  ```java
  rabbitTemplate.convertAndSend("queue.name", "hello");
  ```

* **发给交换机**：通过交换机路由到不同队列（主流模式）。
  ```java
  // 参数：交换机名, 路由键, 消息内容
  rabbitTemplate.convertAndSend("exchange.name", "routing.key", "hello");
  ```

### 6. 消费者 (Consumer)
* **使用 @RabbitListener 监听队列**。注意：类上必须加 @Component 才能被 Spring 扫描。

  ```java

  @Component
  public class SimpleConsumer {

      @RabbitListener(queues = "simple.queue")
      public void testSimpleConsumer(String msg) {
          System.out.println("消费者接收到的消息是：" + msg);
      }
  }
  ```


### 7. 交换机路由模型
交换机（Exchange）决定消息如何流转到队列，主要有三种模式：

* **Fanout (广播)**：

  逻辑：将消息发送到所有绑定到该交换机的队列。

  场景：同一个消息需要被多个微服务同时处理。

* **Direct (定向)**：

  逻辑：根据确定的 RoutingKey 进行匹配，只有 Key 完全一致时，消息才进入对应队列。

  场景：根据消息级别（如 error/info）路由到不同队列。

* **Topic (话题)**：

  逻辑：根据通配符匹配 RoutingKey。# 匹配一个或多个词，* 仅匹配一个词。

  场景：最常用，支持灵活的分类订阅（如 china.news 和 china.weather）。  


### 8. java中声明队列、交换机、绑定关系
* **第一种方法**：分别注册队列、交换机、绑定关系的bean


* **第二种方法**：使用 @RabbitListener 注解声明。
  
  ```java
  @RabbitListener(bindings = @QueueBinding(
    value = @Queue(value = "config.queue2"),
    exchange = @Exchange(value = "config.exchange2", type = ExchangeTypes.TOPIC),
    key = "config.2.#"
  ))
  ```
  
### 9. 消息转换器
* **默认转换器**：将消息转换成字节数组，默认使用 `SimpleMessageConverter`。
* **自定义转换器**：实现 `MessageConverter` 接口，并注册为 Spring Bean，或者将现成的转换器注册为 `@Bean`。

