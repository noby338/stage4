package priv.noby.redis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.noby.redis.entity.Student;
import priv.noby.redis.service.LockService;

import javax.annotation.Resource;

/**
 * 分布式锁
 * 缓存穿透、缓存雪崩、缓存击穿
 */
@RestController
@RequestMapping("lock")
public class LockController {
    @Resource
    private LockService lockService;

    /**
     * 缓存击穿
     */
    @RequestMapping("/breakdown")
    public Student breakdown(){
        Student student = lockService.queryById(1);
        return student;
    }

    /**
     * 自定义多线程
     * 单服务器有问题
     */
    @RequestMapping("/customizeThread")
    public void customizeThread() {
        lockService.incr();
    }

    /**
     * 自定义多线程
     * 单服务器有问题
     * 使用同步锁解决线程问题
     */
    @RequestMapping("/customizeThread2")
    public void customizeThread2() {
        lockService.incr2();
    }

    /**
     * 自定义多线程
     * 集群有问题
     * 使用分布式锁解决线程问题
     */
    @RequestMapping("/customizeThread3")
    public void customizeThread3() {
        lockService.incr3();
    }

    /**
     * redis多线程无问题
     * 单服务器无问题
     * 集群无问题
     */
    @RequestMapping("/redisThread")
    public void redisThread() {
        lockService.incrRedis();
    }

}
