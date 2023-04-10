package priv.noby.consumer2.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息堆积的处理方式
 *
 * 使用惰性队列
 * - 接收到消息后直接存入磁盘而非内存
 * - 消费者要消费消息时才会从磁盘中读取并加载到内存
 * - 支持数百万条的消息存储
 *
 * @author Noby
 * @since 2023/3/31 13:20
 */
@Configuration
public class LazyConfig {

    @Bean
    public Queue lazyQueue() {
        return QueueBuilder.durable("lazy.queue")
                .lazy()
                .build();
    }

    @Bean
    public Queue normalQueue() {
        return QueueBuilder.durable("normal.queue")
                .build();
    }
}
