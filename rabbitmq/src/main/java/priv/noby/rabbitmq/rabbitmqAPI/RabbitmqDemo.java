package priv.noby.rabbitmq.rabbitmqAPI;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 使用 Rabbit 的原生 API 生产队列和消费队列
 * 无交换机，一个管道，一个消费者
 */
public class RabbitmqDemo {
    public static void main(String[] args) throws IOException, TimeoutException {
        consumer();
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
        //定义队列
        // 一般情况下，消费端和生成端只有一端定义队列，当有两端同时定义时，应该属性相同，否则报错
        // 先定义的一方服务优先开启，否则发送信息将失败，大多情况为消费端定义
        //String queue,     队列名
        //boolean durable,  是否是持久化队列，一般使用true,持久化队列，重启服务器，不会删除队列
        //boolean exclusive, 独占队列：只有当前连接和信道才能消费队列中的数据，一般都是false
        //boolean autoDelete,自动删除，队列不使用时，自动删除，一般都是false
        //Map<String, Object> arguments，队列的属性参数设置
//        channel.queueDeclare("queueNoby", true, false, false, null);
        //发送消息
        //String exchange,    交换机，简单工作模式，使用的是默认交换机
        //String routingKey, 路由就是队列的名字
        //BasicProperties props, 消息的附件属性
        //byte[] body             消息内容
        channel.basicPublish("", "queueNoby", null, info.getBytes());
        System.out.println("生产者发送:"+info);
        //关闭资源
        channel.close();
        connection.close();
    }

    /**
     * 消费队列
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
        channel.queueDeclare("queueNoby", true, false, false, null);
        //处理消息
        //String queue,         监听队列名
        //boolean autoAck,      是否自动确认
        //                              自动确认：消费者接受消息，队列自动删除，今天用这种
        //                              手动确认：实现项目中使用这种
        // Consumer callback    处理消息的回调函数：实现接口中的方法，定义如何处理接受的消息
        channel.basicConsume("queueNoby", true, new DefaultConsumer(channel) {
            //String consumerTag,       标记
            //Envelope envelope,       封信：封装和消息相关的信息
            //AMQP.BasicProperties properties,  消息附加属性
            //byte[] body           消息体，消息正文
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body){
                System.out.println("消费者监听:"+new String(body));
            }
        });
        //消费者不用关闭连接和信道，一直处于监听状态
    }
}
