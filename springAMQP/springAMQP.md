# springAMQP

* 使用 Rabbit 的原生 API 生产队列和消费队列(rabbitmqAPI)
    * 导入依赖 amqp-client
* 使用 Rabbit Springboot 生产队列和消费队列(rabbitmqSpringBoot)
    * 导入依赖 spring-boot-starter-amqp
    * 启动类添加 @EnableRabbit
    * 消息的限流（配置文件）
    * 配置类的使用
        * 交换机，配置队列，路由，绑定关系
    * 注解的使用
        * 交换机，配置队列，路由，绑定关系
    * 对象序列化的方式（配置类）
    * 5中工作模式
        - 无交换机，一个管道，一个消费者 simple queue 模式
        - 无交换机，一个管道，多个消费者 work queues 模式
        - publish/subscribe(发布/订阅模式)
            - 有交换机，无路由，多个管道，多个消费者 fanout
            - 有交换机，有路由，多个管道，多个消费者 Direct(Routing)
            - 有交换机，有路由，多个管道，多个消费者 Topics 模式(路由支持通配符)

    
