package priv.noby.publisher2;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringAmqpTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 配置消息发送时未投递到交换机的处理方式
     * @throws InterruptedException
     */
    @Test
    public void testSendMessage2SimpleQueue() throws InterruptedException {
        // 1.准备消息
        String message = "hello, spring amqp!";
        // 2.准备CorrelationData
        // 2.1.消息ID
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        // 2.2.准备ConfirmCallback
        correlationData.getFuture().addCallback(new ListenableFutureCallback<CorrelationData.Confirm>() {
            @Override
            public void onSuccess(CorrelationData.Confirm result) {
                if (result.isAck()) {
                    // ACK
                    log.debug("消息成功投递到交换机！消息ID: {}", correlationData.getId());
                } else {
                    // NACK
                    log.error("消息投递到交换机失败！消息ID：{}", correlationData.getId());
                    // 重发消息
                }
            }

            @Override
            public void onFailure(Throwable ex) {
                // 记录日志
                log.error("消息发送失败！", ex);
                // 重发消息
            }
        });
        // 3.发送消息
        //模拟成功，amq.topic是rabbitmq自带的交换机，在web管控台手动绑定simple队列，路由到simple
//        rabbitTemplate.convertAndSend("amq.topic", "simple", message, correlationData);
        //模拟未投递到交换机
//        rabbitTemplate.convertAndSend("111amq.topic", "simple", message, correlationData);
//        //模拟交换机未投递到队列
        rabbitTemplate.convertAndSend("amq.topic", "111simple", message, correlationData);
    }

    /**
     * 消息的持久化
     */
    @Test
    public void testDurableMessage() {
        // 1.准备消息
        Message message = MessageBuilder.withBody("hello, spring".getBytes(StandardCharsets.UTF_8))
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .build();
        // 2.发送消息
        rabbitTemplate.convertAndSend("simple", message);
    }

    /**
     * 死信交换机（收集死信的交换机，配合队列实现）
     * 一个队列中的消息变为死信分为三种：时间过期、被消费者返回nack并且没有设置重新入队、队列已满
     *
     * 该代码演示队列中的消息过期到达死信交换机
     * 发送到ttl.queue后因为超时未被处理，最终到达dl.queue
     */
    @Test
    public void testTTLMessage() {
        // 1.准备消息
        Message message = MessageBuilder
                .withBody("hello, ttl message".getBytes(StandardCharsets.UTF_8))
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .setExpiration("5000")
                .build();
        // 2.发送消息
        rabbitTemplate.convertAndSend("ttl.direct", "ttl", message);
        // 3.记录日志
        log.info("消息已经成功发送！");
    }

    /**
     * 通过插件实现延迟队列（是上一种方法实现延迟的简化）
     * 插件的延迟交换机 x-delayed-message 是三种基本交换机之外的另一种特殊交换机，可以将消息存储在内存中，当时间结束则投递给队列
     *
     * @throws InterruptedException
     */
    @Test
    public void testSendDelayMessage() throws InterruptedException {
        // 1.准备消息
        Message message = MessageBuilder
                .withBody("hello, ttl message".getBytes(StandardCharsets.UTF_8))
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .setHeader("x-delay", 5000)
                .build();
        // 2.准备CorrelationData
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        // 3.发送消息
        rabbitTemplate.convertAndSend("delay.direct", "delay", message, correlationData);

        log.info("发送消息成功");
    }

    //region 惰性队列相较普通队列的稳定性优势

    /**
     * 内存直接写入磁盘（队列消息不会造成阻塞）
     * @throws InterruptedException
     */
    @Test
    public void testLazyQueue() throws InterruptedException {
        long b = System.nanoTime();
        for (int i = 0; i < 1000000; i++) {
            // 1.准备消息
            Message message = MessageBuilder
                    .withBody("hello, Spring".getBytes(StandardCharsets.UTF_8))
                    .setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT)
                    .build();
            // 2.发送消息
            rabbitTemplate.convertAndSend("lazy.queue", message);
        }
        long e = System.nanoTime();
        System.out.println(e - b);
    }

    /**
     * 传统模式写入内存，当内存中的数据满后会转换为磁盘存储（此时队列拒收消息造成阻塞）
     * @throws InterruptedException
     */
    @Test
    public void testNormalQueue() throws InterruptedException {
        long b = System.nanoTime();
        for (int i = 0; i < 1000000; i++) {
            // 1.准备消息
            Message message = MessageBuilder
                    .withBody("hello, Spring".getBytes(StandardCharsets.UTF_8))
                    .setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT)
                    .build();
            // 2.发送消息
            rabbitTemplate.convertAndSend("normal.queue", message);
        }
        long e = System.nanoTime();
        System.out.println(e - b);
    }
    //endregion

}
