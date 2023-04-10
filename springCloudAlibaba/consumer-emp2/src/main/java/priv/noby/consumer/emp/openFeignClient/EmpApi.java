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
 * fallback = EmpApiFallback.class表示降级方法，当发生异常时调用EmpApiFallback的方法
 */
@FeignClient(value = "provider-emp", fallback = EmpApiFallback.class)
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
    ResponseResult<String> timeout(@PathVariable("time") Integer time);

    @PostMapping("/order")
    ResponseEntity<Order> add(@RequestBody Order order);

    /**
     * 熔断降级
     */
    @GetMapping("/downgrade")
    ResponseResult<String> downgrade();

}
