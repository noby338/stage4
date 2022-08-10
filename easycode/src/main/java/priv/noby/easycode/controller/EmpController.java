package priv.noby.easycode.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import priv.noby.easycode.entity.Emp;
import priv.noby.easycode.service.EmpService;

import javax.annotation.Resource;

/**
 * (Emp)表控制层
 *
 * @author Noby
 * @since 2022-08-09 11:34:44
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
     * @param page 页码
     * @return 查询结果
     */
    @GetMapping("queryByPage/{page}")
    public ResponseEntity<Page<Emp>> queryByPage(@RequestBody Emp emp, @PathVariable("page") int page) {
        PageRequest pageRequest = PageRequest.of(page, 4);
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
    public ResponseEntity<Emp> add(Emp emp) {
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

