package priv.noby.consumer.emp.openFeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import priv.noby.common.entity.Emp;
import priv.noby.common.entity.Order;
import priv.noby.common.entity.ResponseResult;

/**
 * OpenFeign的接口
 * 该接口可映射到Eureka服务注册中心的生产者
 * 该接口的路径必须和生产者的controller一致(方法名可自定义)
 */
@FeignClient("provider-emp")
public interface EmpApi {
    /**
     * 通过OpenFeign调用其他生产者模块的controller
     */
    @GetMapping("/emp/{eid}")
    ResponseResult<Emp> queryById0(@PathVariable("eid") Integer eid);
    /**
     * 模拟超时，OpenFeign默认超时时间为1s
     */
    @GetMapping("/emp/timeout/{time}")
    void timeout(@PathVariable("time") Integer time);

    @PostMapping("/order")
    ResponseEntity<Order> add(@RequestBody Order order);
}
