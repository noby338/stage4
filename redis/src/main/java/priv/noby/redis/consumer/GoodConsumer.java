package priv.noby.redis.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import priv.noby.redis.dao.GoodDao;
import priv.noby.redis.entity.Good;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 定义消费责，监听队列，处理消息
 */
@Component
public class GoodConsumer {
    @Resource
    GoodDao goodDao;
    //@RabbitListener注解指定当前方法监听的队列
    //可以添加三个参数：顺序和数量没有要求
    @RabbitListener(queues = "queueGood")
    public void receiveQQ(Good good, Message message, Channel channel) throws IOException {
        int insert = goodDao.insert(good);
        if (insert>0) {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
