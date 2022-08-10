package priv.noby.rabbitmq.rabbitmqSpringBoot;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 生产者发送消息的service
 */
@Service
public class ProduceSend {
    @Autowired
    RabbitTemplate rabbitTemplate;
    public String send(String exchange, String routing, String info) {
        rabbitTemplate.convertAndSend(exchange, routing,info);
        return "send:"+routing+":"+info;
    }
}
