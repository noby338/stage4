RabbitmqNote

* 使用 Rabbit 的原生 API 生产队列和消费队列(rabbitmqAPI)
  * 导入依赖 amqp-client
  * 无交换机，一个管道，一个消费者
  * 有交换机，无路由，多个管道，多个消费者 publish/subscribe(发布/订阅模式) fanout
  * 有交换机，有路由，多个管道，多个消费者 Routing 模式 Direct
  * 有交换机，有路由，多个管道，多个消费者 Topics 模式(路由支持通配符)
  * 无交换机，一个管道，多个消费者 work queues 模式
* 使用 Rabbit Springboot 生产队列和消费队列(rabbitmqSpringBoot)
  * 导入依赖 spring-boot-starter-amqp
  * 启动类添加 @EnableRabbit
  * 添加 yml spring:rabbitmq
  * 消费者配置类(RabbitmqConfiguration)
    * 定义交换机
    * 定义队列
    * 绑定队列、交换机、路由
    * 发送对象时使用的序列化方式
  * 序列化配置
    * 生产者 ProducerCallbackConfiguration
    * 消费者 RabbitmqConfiguration
  * 手动消息确认
    * 生产者 
      * yml 配置 
      * 生产者的消息确认配置(ProducerCallbackConfiguration)
    * 消费者
      * yml 配置
      * 生产者的消息确认配置(ConsumerReceive)
  
* 五种工作模式
  交换机			路由		
  helloworld				默认交换机		队列名
  work queues											多个消费者处理同一个队列的消息
  publish/subscrib		    fanout			""			
  routing					direct			"routingkey"
  topics					topic			统配：*	#
* 消息确认机制 
  * 生产者确认
    * confirm
      * ack	true false 
        * 消息是否进入交换机
    * return 
      * 是否路由到队列
  * 消费者确认
    * 自动确认
    * 手动确认+限流
      * ack:	从队列中删除消息
      * nack:
        * 重回队列
          * 配置了死信队列：进入死信队列
          * 不重回队列+没有配置死信队列：丢失
* 消息附件属性
  * 持久化 
  * 过期时间 
  * 自定义头信息 
  * 延迟属性
  * 消息转换
    * MessageConvertor