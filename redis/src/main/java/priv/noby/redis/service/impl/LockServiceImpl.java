package priv.noby.redis.service.impl;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import priv.noby.redis.dao.StudentDao;
import priv.noby.redis.entity.Student;
import priv.noby.redis.service.LockService;

import javax.annotation.Resource;
/**
 * 分布式锁
 * 缓存穿透、缓存雪崩、缓存击穿
 */
@Service
public class LockServiceImpl implements LockService {
    @Resource
    RedisTemplate<String, Object> redisTemplate;
    @Resource
    RedissonClient redissonClient;
    @Resource
    StudentDao studentDao;

    /**
     * 模拟缓存击穿
     * 在没有缓存的情况下同一时间来了大量数据的请求，那么所有的数据查询都落到db，此时的数据库压力过大，我们称为缓存击穿。
     */
    @Override
    public Student queryById(Integer id) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //查看缓存中是否有数据
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        String studentStr = String.format("student:%s", id);
        Object rData = opsForValue.get(studentStr);
        if (rData != null) {
            System.out.println("读取 redis 中的数据");
            return ((Student) rData);
        } else {
            System.out.println("读取 mysql 中的数据");
            Student student = studentDao.queryById(id);
            opsForValue.set(studentStr, student);
            return student;
        }
    }

    /**
     * redis自定义操作的多线程
     * 单服务器有问题 使用同步锁可以解决
     * 集群环境有问题 使用分布式锁
     */
    @Override
    public void incr() {
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        Object num = opsForValue.get("num");
        if (num == null) {
            opsForValue.set("num", 1);
        } else {
            opsForValue.set("num", ((int)opsForValue.get("num"))+1);
        }
        System.out.println("num = " + num);
    }


    /**
     * redis自定义操作的多线程
     * 单服务器有问题 使用同步锁解决
     */
    @Override
    public synchronized void incr2() {
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        Object num = opsForValue.get("num");
        if (num == null) {
            opsForValue.set("num", 1);
        } else {
            opsForValue.set("num", ((int)opsForValue.get("num"))+1);
        }
        System.out.println("num = " + num);
    }

    /**
     * redis自定义操作的多线程
     * 集群环境有问题 使用分布式锁
     */
    @Override
    public void incr3() {
        //通过RedissonClient获取分布式锁
        RLock lock = redissonClient.getLock("lock");
        //加锁
        lock.lock();
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        Object num = opsForValue.get("num");
        if (num == null) {
            opsForValue.set("num", 1);
        } else {
            opsForValue.set("num", ((int)opsForValue.get("num"))+1);
        }
        System.out.println("num = " + num);
        //解锁
        lock.unlock();
    }

    /**
     * redis指令的多线程
     * 单服务器无问题
     * 集群环境没问题
     */
    @Override
    public void incrRedis() {
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        //redis6引入了多线程用在底层的通讯，执行指令还是单线程
        System.out.println(opsForValue.increment("num", 1));
    }

}
