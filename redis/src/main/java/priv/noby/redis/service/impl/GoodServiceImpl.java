package priv.noby.redis.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import priv.noby.redis.dao.GoodDao;
import priv.noby.redis.entity.Good;
import priv.noby.redis.service.GoodService;

import javax.annotation.Resource;

/**
 * 模拟秒杀时，redis中的数据被消费，而数据库因为数据压力写入失败
 * GetConnectionTimeoutException
 * 需要使用消息队列解决
 */
@Service("goodService")
public class GoodServiceImpl implements GoodService {
    @Resource
    private GoodDao goodDao;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Autowired
    RabbitTemplate rabbitTemplate;


    /**
     * 初始化秒杀数据量
     */
    public void start(){
        ListOperations<String, Object> opsForList = redisTemplate.opsForList();
        for(int i=1;i<=1000;i++){
            opsForList.leftPush("list",i);
        }
    }

    /**
     * 秒杀
     * 存在数据库同一时刻数据写入量过大，连接超时的问题
     * 可以通过消息队列解决
     */
    public void second(){
        ListOperations<String, Object> opsForList = redisTemplate.opsForList();
        Object item = opsForList.rightPop("list");
        if(item==null){
            System.out.println("over");
        }else{
            //模拟添加订单
            int itemid = Integer.parseInt(item + "");
            goodDao.insert(new Good(itemid,"ittm"+itemid,1));
            System.out.println("ok--"+item);
        }
    }

    /**
     * 秒杀
     * 通过消息队列解决数据库连接超时的问题
     */
    public void second2(){
        ListOperations<String, Object> opsForList = redisTemplate.opsForList();
        Object item = opsForList.rightPop("list");
        if(item==null){
            System.out.println("over");
        }else{
            //模拟添加订单
            int itemid = Integer.parseInt(item + "");
            Good good = new Good(itemid, "ittm" + itemid, 1);
            rabbitTemplate.convertAndSend("exchangeGood", "routingGood",good);
        }
    }
}
