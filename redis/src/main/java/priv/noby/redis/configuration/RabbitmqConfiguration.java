//package priv.noby.redis.configuration;
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * 消费者的配置类
// */
//@Configuration
//public class RabbitmqConfiguration {
//
//    //region 定义交换机
//    @Bean
//    public TopicExchange exchangeGood(){
//        return new TopicExchange("exchangeGood");
//    }
//    //endregion
//
//
//    //region 定义队列
//    @Bean
//    public Queue queueGood(){
//        return new Queue("queueGood");
//    }
//    //endregion
//
//
//    //region 绑定队列到交换机，指定路由
//    @Bean
//    public Binding bindingGood(TopicExchange exchange,Queue queueGood){
//        return BindingBuilder.bind(queueGood).to(exchange).with("#.routingGood.#");
//    }
//    //endregion
//
//    //region 发送对象时使用的序列化方式配置
//    @Bean
//    public MessageConverter messageConverter(){
//        return new Jackson2JsonMessageConverter();
//    }
//    //endregion
//
//}
