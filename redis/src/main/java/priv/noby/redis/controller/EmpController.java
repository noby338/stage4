package priv.noby.redis.controller;

import priv.noby.redis.entity.Emp;
import priv.noby.redis.service.EmpService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Emp)表控制层
 *
 * @author makejava
 * @since 2022-07-29 21:37:10
 */
@RestController
@RequestMapping("emp")
public class EmpController {
    /**
     * 服务对象
     */
    @Resource
    private EmpService empService;

    /**
     * 分页查询
     *
     * @param emp 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<Emp>> queryByPage(Emp emp, PageRequest pageRequest) {
        return ResponseEntity.ok(this.empService.queryByPage(emp, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Emp> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.empService.queryById(id));
    }
    /**
     * 新增数据
     *
     * @param emp 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Emp> add(@RequestBody Emp emp) {
        return ResponseEntity.ok(this.empService.insert(emp));
    }

    /**
     * 编辑数据
     *
     * @param emp 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<Emp> edit(Emp emp) {
        return ResponseEntity.ok(this.empService.update(emp));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.empService.deleteById(id));
    }

}

