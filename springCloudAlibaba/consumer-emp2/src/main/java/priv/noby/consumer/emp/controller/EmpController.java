package priv.noby.consumer.emp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.noby.common.entity.Emp;
import priv.noby.common.entity.ResponseResult;
import priv.noby.consumer.emp.openFeignClient.EmpApi;

/**
 * 调用其他模块的controller
 */
@RestController
@RequestMapping("emp")
public class EmpController {
    //region 使用OpenFeignClient注释该区域代码
//    @Autowired
//    RestTemplate restTemplate;
//
//    /**
//     * 通过RestTemplate调用其他模块的controller查询，手动输入IP和端口
//     */
//    @GetMapping("/queryById/{eid}")
//    public ResponseResult<Emp> queryById(@PathVariable("eid") Integer eid) {
//        return restTemplate.getForObject("http://localhost:8081/emp/" + eid, ResponseResult.class);
//    }
//
//    /**
//     * 通过RestTemplate调用其他模块的controller查询，通过nacos中注册的服务名
//     */
//    @GetMapping("/queryById2/{eid}")
//    public ResponseResult<Emp> queryById2(@PathVariable("eid") Integer eid) {
//        return restTemplate.getForObject("http://provider-emp/emp/" + eid, ResponseResult.class);
//    }
    //endregion


    @Autowired
    EmpApi empApi;
    /**
     * 通过OpenFeign调用其他模块的controller
     * RestTemplate使用的情况下不可使用OpenFeign，否则会出现映射冲突
     */
    @GetMapping("/queryByIdOpenFeign/{eid}")
    public ResponseResult<Emp> queryByIdOpenFeign(@PathVariable("eid") Integer eid) {
        return empApi.queryById0(eid);
    }

    /**
     * 模拟超时，OpenFeign默认超时时间为1s
     * SocketTimeoutException
     */
    @GetMapping("/timeoutTest/{time}")
    public ResponseResult<String> timeoutTest(@PathVariable("time") Integer time) {
        return empApi.timeout(time);
    }

    /**
     * 模拟熔断降级
     */
    @GetMapping("/error/{num}")
    public ResponseResult<String> error(@PathVariable("num") Integer num) {
        if (num==1) {
            throw new RuntimeException("异常 num==1");
        } else if(num==2){
            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("无异常");
        }
        return new ResponseResult<String>(200,"ok",null);
    }

    /**
     * 熔断降级
     */
    @GetMapping("/downgrade")
    public ResponseResult<String> downgrade() {
        return empApi.downgrade();
    }

}

