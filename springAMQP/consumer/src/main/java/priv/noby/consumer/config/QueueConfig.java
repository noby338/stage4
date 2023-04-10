package priv.noby.consumer.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

/**
 * 队列的定义
 *
 * @author Noby
 * @since 2023/3/30 12:01
 */
public class QueueConfig {
    @Bean
    public Queue simpleQueue(){
        return new Queue("simple");
    }

    @Bean
    public Queue workQueue(){
        return new Queue("work");
    }

}
