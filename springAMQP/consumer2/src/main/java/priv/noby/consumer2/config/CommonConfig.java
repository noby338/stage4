package priv.noby.consumer2.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {
    @Bean
    public DirectExchange simpleDirect(){
        return new DirectExchange("simple");
    }

    /**
     * 队列的持久化
     * @return
     */
    @Bean
    public Queue simpleQueue(){
        return QueueBuilder.durable("simple").build();
//        return new Queue("simple");//默认也是持久化的SpringAMQP
    }
}
