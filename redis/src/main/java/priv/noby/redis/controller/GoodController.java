package priv.noby.redis.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.noby.redis.service.GoodService;

import javax.annotation.Resource;

/**
 * 模拟秒杀时，redis中的数据被消费，而数据库因为数据压力写入失败
 * GetConnectionTimeoutException
 */
@RestController
@RequestMapping("good")
public class GoodController {

    @Resource
    private GoodService goodService;

    @RequestMapping("/start")
    public String start(){
        goodService.start();
        return "start ok";
    }

    @RequestMapping("/second")
    public String second(){
        goodService.second();
        return "second ok";
    }
    @RequestMapping("/second2")
    public String second2(){
        goodService.second2();
        return "second ok";
    }
}

