package priv.noby.consumer2.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SpringRabbitListener {

    /**
     * 消费者的失败重试机制
     * @param msg
     */
    @RabbitListener(queues = "simple")
    public void listenSimpleQueue(String msg) {
        log.debug("消费者接收到simple的消息：【" + msg + "】");
        System.out.println(1 / 0);
        log.info("消费者处理消息成功！");
    }

    /**
     * 死信交换机（收集死信的交换机，配合队列实现）
     * 一个队列中的消息变为死信分为三种：时间过期、被消费者返回nack并且没有设置重新入队、队列已满
     *
     * 该代码演示队列中的消息过期到达死信交换机
     * 这里的消息来自TTLMessageConfig中的ttl.queue队列
     * @param msg
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "dl.queue", durable = "true"),
            exchange = @Exchange(name = "dl.direct"),
            key = "dl"
    ))
    public void listenDlQueue(String msg) {
        log.info("消费者接收到了dl.queue的延迟消息：【" + msg + "】");
    }

    /**
     * 通过插件实现延迟队列（是上一种方法实现延迟的简化）
     * delayed = "true"标记交换机为延迟交换机
     * @param msg
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "delay.queue", durable = "true"),
            exchange = @Exchange(name = "delay.direct", delayed = "true"),
            key = "delay"
    ))
    public void listenDelayExchange(String msg) {
        log.info("消费者接收到了delay.queue的延迟消息：【" + msg + "】");
    }
}
