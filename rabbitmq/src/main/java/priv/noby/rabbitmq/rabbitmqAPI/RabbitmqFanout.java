package priv.noby.rabbitmq.rabbitmqAPI;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.time.LocalTime;
import java.util.concurrent.TimeoutException;

/**
 * 3.Fanout(Publish/Subscribe一共有三种，3,4,5都属于Publish/Subscribe)
 * 使用 Rabbit 的原生 API 生产队列和消费队列
 * 有交换机，无路由，多个队列，多个消费者 publish/subscribe(发布/订阅模式) fanout
 * 该种模式交换机会将消息路由到所有的队列
 */
public class RabbitmqFanout {
    public static void main(String[] args) throws IOException, TimeoutException {
        //两个消费者轮流消费
        consumer(1000);
        consumer2(1000);
        produce(6,10);
    }

    /**
     * 生产队列
     *
     * @throws IOException
     * @throws TimeoutException
     */
    private static void produce(int times ,long delay) throws IOException, TimeoutException {
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

        for (int i = 1; i <= times; i++) {
            String info = "info"+i;
            //发送消息
            //String exchange,    交换机，简单工作模式，使用的是默认交换机
            //String routingKey, 路由就是队列的名字
            //BasicProperties props, 消息的附件属性
            //byte[] body             消息内容
            channel.basicPublish("exchangeFanout", "", null, info.getBytes());
            System.out.println(LocalTime.now()+" 生产者生产:"+info);
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
    private static void consumer(long delay) throws IOException, TimeoutException {
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
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(LocalTime.now()+" 消费者Message监听:"+new String(body));
            }
        });
    }

    /**
     * 消费队列2
     *
     * @throws IOException
     * @throws TimeoutException
     */
    private static void consumer2(long delay) throws IOException, TimeoutException {
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
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(LocalTime.now()+" 消费者Email监听:"+new String(body));
            }
        });
    }
}
