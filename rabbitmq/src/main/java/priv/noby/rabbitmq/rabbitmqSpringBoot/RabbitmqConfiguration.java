package priv.noby.rabbitmq.rabbitmqSpringBoot;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消费者的配置类
 */
@Configuration
public class RabbitmqConfiguration {

    //region 定义交换机
    @Bean
    public TopicExchange exchangeTopic(){
        return new TopicExchange("exchangeTopic");
    }
    //endregion


    //region 定义队列
    @Bean
    public Queue queueWechat(){
        return new Queue("queueWechat");
    }

    @Bean
    public Queue queueQQ(){
        return new Queue("queueQQ");
    }

    @Bean
    public Queue queuePC(){
        return new Queue("queuePC");
    }

    @Bean
    public Queue queueMobile(){
        return new Queue("queueMobile");
    }
    //endregion


    //region 绑定队列到交换机，指定路由
    @Bean
    public Binding bindingWechat(TopicExchange exchange, Queue queueWechat){
        return BindingBuilder.bind(queueWechat).to(exchange).with("#.routingWechat.#");
    }
    @Bean
    public Binding bindingQQ(TopicExchange exchange,Queue queueQQ){
        return BindingBuilder.bind(queueQQ).to(exchange).with("#.routingQQ.#");
    }
    @Bean
    public Binding bindingPC(TopicExchange exchange,Queue queuePC){
        return BindingBuilder.bind(queuePC).to(exchange).with("#.routingPC.#");
    }
    @Bean
    public Binding bindingMobile(TopicExchange exchange,Queue queueMobile){
        return BindingBuilder.bind(queueMobile).to(exchange).with("#.routingMobile.#");
    }
    //endregion

    //region 发送对象时使用的序列化方式配置
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    //endregion


}
