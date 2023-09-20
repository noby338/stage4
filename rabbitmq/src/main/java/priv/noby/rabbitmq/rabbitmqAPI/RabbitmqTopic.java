package priv.noby.rabbitmq.rabbitmqAPI;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.time.LocalTime;
import java.util.concurrent.TimeoutException;

/**
 * 5.Topic
 * 使用 Rabbit 的原生 API 生产队列和消费队列
 * 有交换机，有路由，多个管道，多个消费者 Topics 模式(路由支持通配符)，#表示多个或单个单词，*表示单个单词
 */
public class RabbitmqTopic {
    public static void main(String[] args) throws IOException, TimeoutException {
        consumer(1000);
        consumer2(1000);
        consumer3(1000);
        consumer4(1000);
        produce(24, 10, 1);
//        produce(24, 10, 2);
//        produce(24, 10, 3);
//        produce(24, 10, 4);

    }

    /**
     * 生产队列
     *
     * @throws IOException
     * @throws TimeoutException
     */
    static void produce(int times, long delay, int type) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        //基础模式发送消息
//        channel.basicPublish("", "queue", null, info.getBytes());
        //Fanout模式：交换机
        //路由key:
//        channel.basicPublish("exchangeFanout","", null,info.getBytes());
        //routing模式：指定交换机 和 路由
        for (int i = 1; i <= times; i++) {
            String info = "info" + i;
            switch (type) {
                case 1:
                    channel.basicPublish("exchangeTopic", "routingMobile.routingQQ", null, info.getBytes());
                    System.out.println(LocalTime.now() + "生产者发送给routingMobile.QQ:" + info);
                    break;
                case 2:
                    channel.basicPublish("exchangeTopic", "routingPC.routingQQ", null, info.getBytes());
                    System.out.println(LocalTime.now() + "生产者发送给routingPC.routingQQ:" + info);
                    break;
                case 3:
                    channel.basicPublish("exchangeTopic", "routingMobile.routingQQ.routingWechat", null, info.getBytes());
                    System.out.println(LocalTime.now() + "生产者发送给routingMobile.routingQQ.routingWechat:" + info);
                    break;
                default:
                    System.out.println(LocalTime.now() + "类型错误");
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
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();


        //定义交换机为 TOPIC
        channel.exchangeDeclare("exchangeTopic", BuiltinExchangeType.TOPIC);
        //定义队列
        channel.queueDeclare("queueMobile", true, false, false, null);
        //绑定队列和交换机 交换机负责将消息安装路由投递到队列 routing模式:指定一个具体的路由
        //String queue,     队列名
        //String exchange, 交换机名
        //String routingKey 路由key
        channel.queueBind("queueMobile", "exchangeTopic", "#.routingMobile.#");
        //处理消息
        channel.basicConsume("queueMobile", true, new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(LocalTime.now() + " 消费者queueMobile监听:" + new String(body));
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
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();


        //定义交换机为 TOPIC
        channel.exchangeDeclare("exchangeTopic", BuiltinExchangeType.TOPIC);
        //定义队列
        channel.queueDeclare("queuePC", true, false, false, null);
        //绑定队列和交换机 交换机负责将消息安装路由投递到队列 routing模式:指定一个具体的路由
        //String queue,     队列名
        //String exchange, 交换机名
        //String routingKey 路由key
        channel.queueBind("queuePC", "exchangeTopic", "#.routingPC.#");
        //处理消息
        channel.basicConsume("queuePC", true, new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(LocalTime.now() + " 消费者queuePC监听:" + new String(body));
            }
        });
    }

    /**
     * 消费队列3
     *
     * @throws IOException
     * @throws TimeoutException
     */
    static void consumer3(long delay) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();


        //定义交换机为 TOPIC
        channel.exchangeDeclare("exchangeTopic", BuiltinExchangeType.TOPIC);
        //定义队列
        channel.queueDeclare("queueQQ", true, false, false, null);
        //绑定队列和交换机 交换机负责将消息安装路由投递到队列 routing模式:指定一个具体的路由
        //String queue,     队列名
        //String exchange, 交换机名
        //String routingKey 路由key
        channel.queueBind("queueQQ", "exchangeTopic", "#.routingQQ.#");
        //处理消息
        channel.basicConsume("queueQQ", true, new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(LocalTime.now() + " 消费者queueQQ监听:" + new String(body));
            }
        });
    }

    /**
     * 消费队列3
     *
     * @throws IOException
     * @throws TimeoutException
     */
    static void consumer4(long delay) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();


        //定义交换机为 TOPIC
        channel.exchangeDeclare("exchangeTopic", BuiltinExchangeType.TOPIC);
        //定义队列
        channel.queueDeclare("queueWechat", true, false, false, null);
        //绑定队列和交换机 交换机负责将消息安装路由投递到队列 routing模式:指定一个具体的路由
        //String queue,     队列名
        //String exchange, 交换机名
        //String routingKey 路由key
        channel.queueBind("queueWechat", "exchangeTopic", "#.routingWechat.#");
        //处理消息
        channel.basicConsume("queueWechat", true, new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(LocalTime.now() + " 消费者queueWechat监听:" + new String(body));
            }
        });
    }
}
