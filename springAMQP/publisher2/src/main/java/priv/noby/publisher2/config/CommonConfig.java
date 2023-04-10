package priv.noby.publisher2.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * 配置消息发送时投递到交换机，但交换机未投递到队列的处理方式
 * 所有交换机未投递到队列的消息的处理方式都相同，所以在配置类中统一配置
 *
 * @author Noby
 * @since 2023/3/30 19:09
 */
@Slf4j
@Configuration
public class CommonConfig implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 获取RabbitTemplate对象
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);
        // 配置ReturnCallback
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                // 判断是否是延迟消息
                Integer receivedDelay = message.getMessageProperties().getReceivedDelay();
                if (receivedDelay != null && receivedDelay > 0) {
                    // 是一个延迟消息，忽略这个错误提示
                    return;
                }
                // 记录日志
                log.error("消息发送到队列失败，响应码：{}, 失败原因：{}, 交换机: {}, 路由key：{}, 消息: {}",
                        replyCode, replyText, exchange, routingKey, message.toString());
                // 如果有需要的话，重发消息
            }
        });
    }
}
