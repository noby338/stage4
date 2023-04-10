package priv.noby.rabbitmq2.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 消费者的配置类
 */
@Configuration
public class ConsumerConfiguration {

    //region 定义交换机
    @Bean
    public TopicExchange exchangeTopic() {
        return new TopicExchange("exchangeTopic");
    }
    //endregion

    //region 定义队列
    @Bean
    public Queue queueWechat() {
        return new Queue("queueWechat");
    }

    @Bean
    public Queue queueQQ() {
        return new Queue("queueQQ");
    }

    @Bean
    public Queue queuePC() {
        return new Queue("queuePC");
    }

    @Bean
    public Queue queueMobile() {
        return new Queue("queueMobile");
    }


    //endregion

    //region 绑定队列到交换机，指定路由
    @Bean
    public Binding bindingWechat(TopicExchange exchangeTopic, Queue queueWechat) {
        return BindingBuilder.bind(queueWechat).to(exchangeTopic).with("#.routingWechat.#");
    }

    @Bean
    public Binding bindingQQ(TopicExchange exchangeTopic, Queue queueQQ) {
        return BindingBuilder.bind(queueQQ).to(exchangeTopic).with("#.routingQQ.#");
    }

    @Bean
    public Binding bindingPC(TopicExchange exchangeTopic, Queue queuePC) {
        return BindingBuilder.bind(queuePC).to(exchangeTopic).with("#.routingPC.#");
    }

    @Bean
    public Binding bindingMobile(TopicExchange exchangeTopic, Queue queueMobile) {
        return BindingBuilder.bind(queueMobile).to(exchangeTopic).with("#.routingMobile.#");
    }

    //endregion

    //region 死信
    @Bean
    public TopicExchange exchangeOrderDelay() {
        return new TopicExchange("exchangeOrderDelay");
    }

    @Bean
    public Queue queueOrderDelay() {
        //设置队列属性
        Map<String, Object> arguments = new HashMap<>();
        //给队列中的消息设置统一的过期时间
        arguments.put("x-message-ttl", 10000);
        //指定死信交换机和死信路由，没有这个死信就丢了
        arguments.put("x-dead-letter-exchange", "exchangeOrderDLX");
        arguments.put("x-dead-letter-routing-key", "routingOrderDLX");
        return new Queue("queueOrderDelay", true, false, false, arguments);
    }

    @Bean
    public Binding bindingOrderDelay(TopicExchange exchangeOrderDelay, Queue queueOrderDelay) {
        return BindingBuilder.bind(queueOrderDelay).to(exchangeOrderDelay).with("#.routingOrderDelay.#");
    }

    @Bean
    public TopicExchange exchangeOrderDLX() {
        return new TopicExchange("exchangeOrderDLX");
    }

    @Bean
    public Queue queueOrderDLX() {
        return new Queue("queueOrderDLX");
    }

    @Bean
    public Binding bindingOrderDLX(TopicExchange exchangeOrderDLX, Queue queueOrderDLX) {
        return BindingBuilder.bind(queueOrderDLX).to(exchangeOrderDLX).with("#.routingOrderDLX.#");
    }
    //endregion

    //region 延迟插件
    @Bean
    public TopicExchange exchangeDelayPlugin() {
        TopicExchange exchangeDelayPlugin = new TopicExchange("exchangeDelayPlugin");
        //交换机启用延迟
        exchangeDelayPlugin.setDelayed(true);
        return exchangeDelayPlugin;
    }

    @Bean
    public Queue queueDelayPlugin() {
        return new Queue("queueDelayPlugin");
    }

    @Bean
    public Binding bindingDelayPlugin(TopicExchange exchangeDelayPlugin, Queue queueDelayPlugin) {
        return BindingBuilder.bind(queueDelayPlugin).to(exchangeDelayPlugin).with("#.routingDelayPlugin.#");
    }
    //endregion 

    //region 发送对象时使用的序列化方式配置
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    //endregion
}
