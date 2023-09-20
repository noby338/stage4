package priv.noby.consumer2.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置该类和配置类中的retry后，所有队列的消费者处理失败的消息都会发送到error.direct交换机中，然后发送到其绑定的队列中
 *
 * @author Noby
 * @since 2023/3/30 22:47
 */
@Configuration
public class ErrorMessageConfig {

    @Bean
    public DirectExchange errorMessageExchange(){
        return new DirectExchange("error.direct");
    }

    @Bean
    public Queue errorQueue(){
        return new Queue("error.queue");
    }

    @Bean
    public Binding errorMessageBinding(){
        return BindingBuilder.bind(errorQueue()).to(errorMessageExchange()).with("error");
    }

    /**
     * 覆盖默认的消息恢复器，使用此恢复器将消息发送到自定义的error.direct交换机中
     * 在开启重试模式后，重试次数耗尽，如果消息依然失败，则需要有MessageRecovery接口来处理，它包含三种不同的实现：
     * - RejectAndDontRequeueRecoverer：重试耗尽后，直接reject，丢弃消息。默认就是这种方式
     * - ImmediateRequeueMessageRecoverer：重试耗尽后，返回nack，消息重新入队
     * - RepublishMessageRecoverer：重试耗尽后，将失败消息投递到指定的交换机
     * @param rabbitTemplate
     * @return
     */
    @Bean
    public MessageRecoverer republishMessageRecoverer(RabbitTemplate rabbitTemplate){
        return new RepublishMessageRecoverer(rabbitTemplate, "error.direct", "error");
    }
}
