package priv.noby.redis.controller;

import priv.noby.redis.entity.Dep;
import priv.noby.redis.service.DepService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Dep)表控制层
 *
 * @author makejava
 * @since 2022-07-29 21:37:08
 */
@RestController
@RequestMapping("dep")
public class DepController {
    /**
     * 服务对象
     */
    @Resource
    private DepService depService;

    /**
     * 分页查询
     *
     * @param dep 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<Dep>> queryByPage(Dep dep, PageRequest pageRequest) {
        return ResponseEntity.ok(this.depService.queryByPage(dep, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Dep> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.depService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param dep 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Dep> add(Dep dep) {
        return ResponseEntity.ok(this.depService.insert(dep));
    }

    /**
     * 编辑数据
     *
     * @param dep 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<Dep> edit(Dep dep) {
        return ResponseEntity.ok(this.depService.update(dep));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.depService.deleteById(id));
    }

}

