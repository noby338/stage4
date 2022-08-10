package priv.noby.rabbitmq.rabbitmqSpringBoot;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

/**
 * 生产者的消息确认配置
 */
//@Configuration
public class ProducerCallbackConfiguration implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    RabbitTemplate rabbitTemplate;

    //按照Spring的声明周期进行控制
    //<bean id="" class="" init-method=""   destroied-method="">
    //@PostConstruct      @PreDestroy
    //setup teardown
    @PostConstruct
    public void setup() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    /**
     * confirm可以监听消息是否进入了交换机
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("confirm" + "  消息的唯一标志：" + correlationData +
                "  确认结果：" + ack + "  失败原因：" + cause);
    }

    /**
     * return可以监听消息是否投递到了队列
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("return" + "  消息对象：" + message + "  回复编码：" + replyCode +
                "  回复内容：" + replyText + "  交换机：" + exchange + "  路由key：" + routingKey);
    }

    /**
     * 发送对象时使用的序列化方式配置
     */
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
