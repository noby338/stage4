package priv.noby.rabbitmq.rabbitmqAPI;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 使用 Rabbit 的原生 API 生产队列和消费队列
 * 无交换机，一个管道，多个消费者 work queues 模式
 */
public class RabbitmqWorkQueues {
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
        //设置要访问的虚拟主机:类似于MySQL中的一个具体数据库
        connectionFactory.setVirtualHost("/");
        //创建连接对象
        Connection connection = connectionFactory.newConnection();
        //创建信道
        Channel channel = connection.createChannel();
        //发送消息
        channel.basicPublish("", "queueNoby", null, info.getBytes());
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
        //定义队列
        channel.queueDeclare("queueNoby", true, false, false, null);
        //处理消息
        channel.basicConsume("queueNoby", true, new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body){
                System.out.println("消费者1监听:"+new String(body));
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
        //定义队列
        channel.queueDeclare("queueNoby", true, false, false, null);
        //处理消息
        channel.basicConsume("queueNoby", true, new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body){
                System.out.println("消费者2监听:"+new String(body));
            }
        });
    }
}
