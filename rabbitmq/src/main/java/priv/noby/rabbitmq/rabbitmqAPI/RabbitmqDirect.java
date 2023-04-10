package priv.noby.rabbitmq.rabbitmqAPI;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.time.LocalTime;
import java.util.concurrent.TimeoutException;

/**
 * 4.Direct(Routing)
 * 使用 Rabbit 的原生 API 生产队列和消费队列
 * 有交换机，有路由，多个管道，多个消费者 Routing 模式 Direct
 */
public class RabbitmqDirect {
    public static void main(String[] args) throws IOException, TimeoutException {

        consumer(1000);
        consumer2(1000);
        produce(6,10,"routingWrong");
//        produce(6,10,"routingError");
//        produce(6,10,"routingAll");
    }

    /**
     * 生产队列
     *
     * @throws IOException
     * @throws TimeoutException
     */
    static void produce(int times, long delay,String routing) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.122.128");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        //简单模式发送消息
//        channel.basicPublish("", "queue", null, info.getBytes());
        //Fanout模式：交换机
        //路由key:
//        channel.basicPublish("exchangeFanout","", null,info.getBytes());
        //routing模式：指定交换机 和 路由
        for (int i = 1; i <= times; i++) {
            String info = "info"+i;
            if ("routingWrong".equals(routing)) {
                channel.basicPublish("exchangeDirect", "routingWrong", null, info.getBytes());
                System.out.println("生产者发送给routingWrong:" + info);
            } else if ("routingError".equals(routing)) {
                channel.basicPublish("exchangeDirect", "routingError", null, info.getBytes());
                System.out.println("生产者发送给routingError:" + info);
            } else {
//            channel.basicPublish("exchangeDirect", "routingWrong", null, info.getBytes());
//            channel.basicPublish("exchangeDirect", "routingError", null, info.getBytes());
                channel.basicPublish("exchangeDirect", "routingAll", null, info.getBytes());
                System.out.println("生产者发送给routingWrong和routingError:" + info);
            }
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //关闭资源
        channel.close();
        connection.close();
    }

    /**
     * 消费队列1
     *
     * @throws IOException
     * @throws TimeoutException
     */
    static void consumer(long delay) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.122.128");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();


        //定义交换机为 DIRECT
        channel.exchangeDeclare("exchangeDirect", BuiltinExchangeType.DIRECT);
        //定义队列
        channel.queueDeclare("queueWrong", true, false, false, null);
        //绑定队列和交换机 交换机负责将消息安装路由投递到队列 routing模式:指定一个具体的路由
        //String queue,     队列名
        //String exchange, 交换机名
        //String routingKey 路由key
        channel.queueBind("queueWrong", "exchangeDirect", "routingWrong");
        channel.queueBind("queueWrong", "exchangeDirect", "routingAll");
        //处理消息
        channel.basicConsume("queueWrong", true, new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(LocalTime.now()+" 消费者queueWrong监听:"+new String(body));
            }
        });
    }

    /**
     * 消费队列2
     *
     * @throws IOException
     * @throws TimeoutException
     */
    static void consumer2(long delay) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.122.128");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();


        //定义交换机为 DIRECT
        channel.exchangeDeclare("exchangeDirect", BuiltinExchangeType.DIRECT);
        //定义队列
        channel.queueDeclare("queueError", true, false, false, null);
        //绑定队列和交换机 交换机负责将消息安装路由投递到队列 routing模式:指定一个具体的路由
        //String queue,     队列名
        //String exchange, 交换机名
        //String routingKey 路由key
//        channel.queueBind("queueError", "exchangeDirect", "");
        channel.queueBind("queueError", "exchangeDirect", "routingError");
        channel.queueBind("queueError", "exchangeDirect", "routingAll");
        //处理消息
        channel.basicConsume("queueError", true, new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(LocalTime.now()+" 消费者queueError监听:"+new String(body));
            }
        });
    }
}
