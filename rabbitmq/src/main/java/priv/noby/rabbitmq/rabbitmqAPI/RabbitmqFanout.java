package priv.noby.rabbitmq.rabbitmqAPI;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 使用 Rabbit 的原生 API 生产队列和消费队列
 * 有交换机，无路由，多个管道，多个消费者 publish/subscribe(发布/订阅模式) fanout
 */
public class RabbitmqFanout {
    public static void main(String[] args) throws IOException, TimeoutException {
        consumer();
        consumer2();
        for (int i = 0; i < 4; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            produce("info"+i);
        }
    }

    /**
     * 生产队列
     *
     * @throws IOException
     * @throws TimeoutException
     */
    private static void produce(String info) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.122.128");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        //发送消息
//        channel.basicPublish("", "queueNoby", null, info.getBytes());
        //Fanout模式：交换机
        //路由key:
        channel.basicPublish("exchangeFanout","", null,info.getBytes());
        System.out.println("生产者发送:"+info);
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
    private static void consumer() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.122.128");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        //定义交换机为 Fanout
        channel.exchangeDeclare("exchangeFanout", BuiltinExchangeType.FANOUT);
        //定义队列
        channel.queueDeclare("queueMessage", true, false, false, null);
        //绑定队列和交换机 交换机负责将消息安装路由投递到队列
        //String queue,     队列名
        //String exchange, 交换机名
        //String routingKey 路由key,发布订阅模式路由写"",只要将队列绑定交换机，交换机就会将信息发到队列中
        channel.queueBind("queueMessage", "exchangeFanout","");
        //处理消息
        channel.basicConsume("queueMessage", true, new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body){
                System.out.println("消费者Message监听:"+new String(body));
            }
        });
    }

    /**
     * 消费队列2
     *
     * @throws IOException
     * @throws TimeoutException
     */
    private static void consumer2() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.122.128");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        //定义交换机为 Fanout
        channel.exchangeDeclare("exchangeFanout", BuiltinExchangeType.FANOUT);
        //定义队列
        channel.queueDeclare("queueEmail", true, false, false, null);
        //绑定队列和交换机 交换机负责将消息安装路由投递到队列
        channel.queueBind("queueEmail", "exchangeFanout","");
        //处理消息
        channel.basicConsume("queueEmail", true, new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body){
                System.out.println("消费者Email监听:"+new String(body));
            }
        });
    }
}
