package priv.noby.rabbitmq.rabbitmqSpringBoot;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 生产者接收请求的controller
 */
@RestController
public class ProduceController {
    //SpringBoot整合操作rabbitmq的核心类
    @Autowired
    RabbitTemplate rabbitTemplate;

    @RequestMapping("/send/{routing}/{info}")
    public void send(@PathVariable String routing, @PathVariable String info) {
        rabbitTemplate.convertAndSend("exchangeTopic", routing, info);
    }

    @RequestMapping("/sendWrongExchange")
    public void sendWrongExchange() {
        rabbitTemplate.convertAndSend("x", "routingPC", "info");
    }

    @RequestMapping("/sendWrongRouting")
    public void sendWrongRouting() {
        rabbitTemplate.convertAndSend("exchangeTopic", "x", "info");
    }

    @RequestMapping("/limiting")
    public void limiting() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("exchangeTopic", "routingWechat", "info");
        }
    }

    @RequestMapping("/additional")
    public void additional() {
        rabbitTemplate.convertAndSend("exchangeTopic", "routingPC", "info"
                //消息的附加属性
                , new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        //设置持久化
                        //message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        //设置延迟发送，配合延迟插件使用
                        //message.getMessageProperties().setDelay(2000);
                        //过期时间
                        //message.getMessageProperties().setExpiration("10000");
                        //消息头
                        message.getMessageProperties().setHeader("a", "aa");
                        return message;
                    }
                }
        );
    }

    //region 死信
    @RequestMapping("/orderDelay")
    public String orderDelay(){
        for (int i=1;i<=5;i++) {
            System.out.println("发送时间："+new Date());
            rabbitTemplate.convertAndSend("exchangeOrderDelay", "routingOrderDelay", "order"+i);
        }
        return "orderDelay ok";
    }
    //endregion

    //region 延迟队列
    @RequestMapping("/delayPlugin")
    public String delayPlugin(){
        for (int i=1;i<=5;i++) {
            System.out.println("发送时间："+new Date());
            rabbitTemplate.convertAndSend("exchangeDelayPlugin", "routingDelayPlugin", "delayPlugin" + i,
                    new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    //设置消息属性：延迟
                    message.getMessageProperties().setDelay(10000);
                    return message;
                }
            });
        }
        return "delayPlugin ok";
    }
    //endregion

}
