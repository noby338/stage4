package priv.noby.consumer2.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 延迟交换机和延迟队列(和普通队列的区别在于添加了内部消息的过期时间，并绑定了死信交换机)
 * 该代码演示队列中的消息过期到达死信交换机
 */
@Configuration
public class TTLMessageConfig {

    @Bean
    public DirectExchange ttlDirectExchange() {
        return new DirectExchange("ttl.direct");
    }

    @Bean
    public Queue ttlQueue() {
        return QueueBuilder
                .durable("ttl.queue")
                .ttl(10000)//设置队列中的消息的过期时间，也可在消息中设置
                .deadLetterExchange("dl.direct")
                .deadLetterRoutingKey("dl")
                .build();
    }

    @Bean
    public Binding ttlBinding() {
        return BindingBuilder.bind(ttlQueue()).to(ttlDirectExchange()).with("ttl");
    }
}
