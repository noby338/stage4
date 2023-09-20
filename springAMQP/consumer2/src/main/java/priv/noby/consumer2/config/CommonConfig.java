package priv.noby.consumer2.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 持久化交换机和队列的定义
 *
 * @author Noby
 * @since 2023/7/6
 */
@Configuration
public class CommonConfig {
    /**
     * 交换机的持久化
     * @return
     */
    @Bean
    public DirectExchange simpleDirect(){
        // 队列名称，是否持久化，队列为空时是否自动删除
        return new DirectExchange("simple",true,false);
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
