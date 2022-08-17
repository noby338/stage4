package priv.noby.provider.emp.controller;

import org.springframework.web.bind.annotation.*;
import priv.noby.common.entity.Emp;
import priv.noby.common.entity.ResponseResult;
import priv.noby.provider.emp.service.EmpService;

import javax.annotation.Resource;

/**
 * (Emp)表控制层
 *
 * @author Noby
 * @since 2022-08-12 13:57:14
 */
@RestController
@RequestMapping("emp")
public class EmpController {
    @Resource
    private EmpService empService;


    @GetMapping("{eid}")
    public ResponseResult<Emp> queryById(@PathVariable("eid") Integer eid) {
        Emp emp = empService.queryById(eid);
        return new ResponseResult(200,"ok",emp);
    }

    @PostMapping
    public ResponseResult<Emp> add(@RequestBody Emp emp) {
        empService.insert(emp);
        return new ResponseResult<>(200, "ok", null);
    }

    /**
     * 模拟超时，OpenFeign默认超时时间为1s
     */
    @GetMapping("timeout/{time}")
    public void timeout(@PathVariable("time") Integer time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

