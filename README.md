amqp
    和http一样，是一种传输协议
    http是浏览器和服务器的协议
    amqp是消息队列协议

性能
    连接rabbitmq是通过tcp连接
    同一个tcp内的生产者和消费者通过管道通信，提高性能


连接rabbitmq
    spring.rabbitmq.host=localhost
    spring.rabbitmq.port=5672
    spring.rabbitmq.username=hmall
    spring.rabbitmq.password=yee23
    spring.rabbitmq.virtual-host=/hmall

生产者
    rabbitTemplate.convertAndSend()
    直接给队列发送消息
    给交换机发送消息

消费者
    @RabbitListener(queues = "queue.name")
    监听队列消息



prefetch