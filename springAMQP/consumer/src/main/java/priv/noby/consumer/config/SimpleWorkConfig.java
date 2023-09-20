package priv.noby.consumer.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 没有交换机的simple和work模式的队列的定义
 *
 * @author Noby
 * @since 2023/3/30 12:01
 */
@Configuration
public class SimpleWorkConfig {
    @Bean
    public Queue simpleQueue(){
        return new Queue("simple");
    }

    @Bean
    public Queue workQueue(){
        return new Queue("work");
    }

}
