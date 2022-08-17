package priv.noby.rabbitmq.rabbitmqSpringBoot;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * 定义消费责，监听队列，处理消息
 */
@Component
public class Consumer {
    //@RabbitListener注解指定当前方法监听的队列
    //可以添加三个参数：顺序和数量没有要求
    @RabbitListener(queues = "queueQQ")
    public void receiveQQ(String msg, Message message, Channel channel) throws IOException {
        System.out.println("queueQQ:" + msg);
        //region 消费端手动确认消息
        if ("error".equals(msg)) {
            //手动确认：失败
            //long deliveryTag,     投递标记
            //boolean multiple,     是否同时确认多条
            //boolean requeue 是否重回队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        } else {
            //手动确认：成功
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
//        try{
//            //处理消息的业务逻辑
//            //Ack();
//        }catch (Exception e){
//            //NAck();
//        }
        //endregion 消费端手动确认消息

    }

    @RabbitListener(queues = "queueWechat")
    public void receiveWechat(String msg, Message message, Channel channel) throws IOException {
        //模拟处理消费消息需要的时间
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("queueWechat:" + msg);
        //手动确认和限流使用
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = "queuePC")
    public void receivePC(String msg, Message message, Channel channel) throws IOException {
        System.out.println("queuePC:" + msg);
        //附加的属性
        System.out.println("a:"+message.getMessageProperties().getHeader("a").toString());
        //手动确认和限流使用
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

    }

    @RabbitListener(queues = "queueMobile")
    public void receiveMobile(String msg, Message message, Channel channel) {
        System.out.println("queueMobile:" + msg);

    }

    //region 死信
    @RabbitListener(queues = "queueOrderDLX")
    public void receiveOrderDLX(String msg, Message message, Channel channel) throws IOException{
        System.out.println("死信队列处理时间:"+new Date());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
    //endregion

    //region 延迟队列
    @RabbitListener(queues = "queueDelayPlugin")
    public void receiveDelayPlugin(String msg, Message message, Channel channel) throws IOException{
        System.out.println("延迟队列处理时间:"+new Date());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
    //endregion
}
