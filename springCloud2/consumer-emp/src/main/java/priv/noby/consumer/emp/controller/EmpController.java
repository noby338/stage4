package priv.noby.consumer.emp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import priv.noby.common.entity.Emp;
import priv.noby.common.entity.ResponseResult;

import java.util.List;

/**
 * 调用其他模块的controller
 */
@RestController
@RequestMapping("emp")
public class EmpController {
    //region 使用OpenFeignClient注释该区域代码
    @Autowired
    RestTemplate restTemplate;
    //eureka 寻找生产者ip的端口所需的类
    @Autowired
    DiscoveryClient discoveryClient;

    public static final String URL = "http://localhost:8081/emp/";

    //eureka DiscoveryClient 寻找生产者ip的端口的方法
    String getUrlByDiscoveryClient() {
        //根据服务名，到EurekaServer中获取服务信息，因为Eureka支持集群，所以一个服务可以有多个节点
        List<ServiceInstance> instances = discoveryClient.getInstances("provider-emp");
        ServiceInstance instance = instances.get(0);
        return "http://" + instance.getHost() + ":" + instance.getPort() + "/emp/";
    }

    //eureka Ribbon 寻找生产者ip的端口的方法
    //使用方法：Ribbon集成在Eureka中，在启动类添加注解@EnableEurekaClient
    String getUrlByRibbon(){
        return "http://provider-emp/emp/";
    }

    /**
     * 通过RestTemplate调用其他模块的controller查询，手动输入IP和端口
     */
    @GetMapping("{eid}")
    public ResponseResult<Emp> queryById(@PathVariable("eid") Integer eid) {
        return restTemplate.getForObject(URL + eid, ResponseResult.class);
    }

    /**
     * 通过RestTemplate调用其他模块的controller添加，手动输入IP和端口
     */
    @PostMapping
    public ResponseResult<Emp> add(@RequestBody Emp emp) {
        return restTemplate.postForObject(getUrlByDiscoveryClient(), emp, ResponseResult.class);
    }

    /**
     * 通过RestTemplate调用其他模块的controller，eureka寻找注册中心生产者的ip和端口
     */
    @GetMapping("/queryByIdEurekaDiscoveryClient/{eid}")
    public ResponseResult<Emp> queryByIdEurekaDiscoveryClient(@PathVariable("eid") Integer eid) {
        return restTemplate.getForObject(getUrlByDiscoveryClient() + eid, ResponseResult.class);
    }


    /**
     * 通过RestTemplate调用其他模块的controller，eureka寻找注册中心生产者的ip和端口
     */
    @GetMapping("/queryByIdEurekaRibbon/{eid}")
    public ResponseResult<Emp> queryByIdEurekaRibbon(@PathVariable("eid") Integer eid) {
        return restTemplate.getForObject(getUrlByRibbon() + eid, ResponseResult.class);
    }
    //endregion
//    @Autowired
//    EmpApi empApi;
//    /**
//     * 通过OpenFeign调用其他模块的controller
//     * RestTemplate使用的情况下不可使用OpenFeign，否则会出现映射冲突
//     */
//    @GetMapping("/queryByIdOpenFeign/{eid}")
//    public ResponseResult<Emp> queryByIdOpenFeign(@PathVariable("eid") Integer eid) {
//        return empApi.queryById0(eid);
//    }
//
//    /**
//     * 模拟超时，OpenFeign默认超时时间为1s
//     * SocketTimeoutException
//     */
//    @GetMapping("/timeoutTest/{time}")
//    public void timeoutTest(@PathVariable("time") Integer time) {
//        empApi.timeout(time);
//    }
}

