package priv.noby.publisher.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringAmqpTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * BasicQueue
     */
    @Test
    public void testSendMessage2SimpleQueue() {
        String queueName = "simple";
        String message = "hello, spring amqp!";
        rabbitTemplate.convertAndSend(queueName, message);
    }

    /**
     * WorkQueue
     */
    @Test
    public void testSendMessage2WorkQueue() throws InterruptedException {
        String queueName = "work";
        String message = "hello, message__";
        for (int i = 1; i <= 50; i++) {
            rabbitTemplate.convertAndSend(queueName, message + i);
            Thread.sleep(20);
        }
    }

    /**
     * Fanout
     */
    @Test
    public void testSendFanoutExchange() {
        // 交换机名称
        String exchangeName = "fanout";
        // 消息
        String message = "hello, every one!";
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, "", message);
    }

    /**
     * Direct
     */
    @Test
    public void testSendDirectExchange() {
        // 交换机名称
        String exchangeName = "direct";
        // 消息
        String message = "hello, red!";
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, "red", message);
//        rabbitTemplate.convertAndSend(exchangeName, "yellow", message);
//        rabbitTemplate.convertAndSend(exchangeName, "blue", message);
    }

    /**
     * Topic
     */
    @Test
    public void testSendTopicExchange() {
        // 交换机名称
        String exchangeName = "topic";
        // 消息
        String message = "今天天气不错，我的心情好极了!";
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, "china.weather", message);
//        rabbitTemplate.convertAndSend(exchangeName, "china.news", message);
//        rabbitTemplate.convertAndSend(exchangeName, "japan.news", message);
    }

    /**
     * BasicQueue 对象的序列化
     */
    @Test
    public void testSendObject() {
        String queueName = "simple";
        Map<String,String> message = new HashMap<>();
        message.put("name", "noby");
        message.put("gender","male");
        rabbitTemplate.convertAndSend(queueName, message);
    }
}
