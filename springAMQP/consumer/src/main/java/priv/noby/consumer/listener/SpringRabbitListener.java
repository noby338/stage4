package priv.noby.consumer.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Map;

@Component
public class SpringRabbitListener {

    //region BasicQueue 没有交换机(该注解不会创建队列，需在配置类中定义队列注入bean)
    @RabbitListener(queues = "simple")
    public void listenSimpleQueue(String msg) {
        System.out.println("消费者接收到simple的消息：【" + msg + "】");
    }
    //endregion


    //region WorkQueue 没有交换机
    @RabbitListener(queues = "work")
    public void listenWorkQueue1(String msg) throws InterruptedException {
        System.out.println("workQueue消费者1接收到消息：【" + msg + "】" + LocalTime.now());
        Thread.sleep(20);
    }
    @RabbitListener(queues = "work")
    public void listenWorkQueue2(String msg) throws InterruptedException {
        System.err.println("workQueue消费者2........接收到消息：【" + msg + "】" + LocalTime.now());
        Thread.sleep(200);
    }
    //endregion


    //region Fanout 使用配置文件方式配置路由
    @RabbitListener(queues = "fanout.queue1")
    public void listenFanoutQueue1(String msg) {
        System.out.println("消费者接收到fanout.queue1的消息：【" + msg + "】");
    }

    @RabbitListener(queues = "fanout.queue2")
    public void listenFanoutQueue2(String msg) {
        System.out.println("消费者接收到fanout.queue2的消息：【" + msg + "】");
    }
    //endregion

    //region Direct 使用注解方式配置路由（该注解同时定义队列，交换机，绑定关系。可省略配置类的配置）
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue1"),
            exchange = @Exchange(name = "direct", type = ExchangeTypes.DIRECT),
            key = {"red", "blue"}
    ))
    public void listenDirectQueue1(String msg) {
        System.out.println("消费者接收到direct.queue1的消息：【" + msg + "】");
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue2"),
            exchange = @Exchange(name = "direct", type = ExchangeTypes.DIRECT),
            key = {"red", "yellow"}
    ))
    public void listenDirectQueue2(String msg) {
        System.out.println("消费者接收到direct.queue2的消息：【" + msg + "】");
    }
    //endregion


    //region Topic 使用注解的方式配置路由
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue1"),
            exchange = @Exchange(name = "topic", type = ExchangeTypes.TOPIC),
            key = "china.#"
    ))
    public void listenTopicQueue1(String msg) {
        System.out.println("消费者接收到topic.queue1的消息：【" + msg + "】");
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.queue2"),
            exchange = @Exchange(name = "topic", type = ExchangeTypes.TOPIC),
            key = "#.news"
    ))
    public void listenTopicQueue2(String msg) {
        System.out.println("消费者接收到topic.queue2的消息：【" + msg + "】");
    }
    //endregion


    //region BasicQueue 传输对象时将jdk序列化方式配置为json序列化方式，在配置类中配置
    @RabbitListener(queues = "simple")
    public void listenObjectQueue(Map<String, Object> msg) {
        System.out.println("接收到simple的object消息：" + msg);
    }
    //endregion

}
