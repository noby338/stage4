package priv.noby.consumer.emp.controller;

import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import priv.noby.common.entity.Order;
import priv.noby.common.entity.Storage;
import priv.noby.consumer.emp.openFeignClient.EmpApi;
import priv.noby.consumer.emp.service.StorageService;

import javax.annotation.Resource;

/**
 * 减少某库存数量，同时通过调用生产者的controller添加一个订单
 */
@RestController
@RequestMapping("storage")
public class StorageController {
    @Resource
    private StorageService storageService;
    @Autowired
    EmpApi empApi;
    /**
     */
    @PutMapping("/buy/{productId}/{count}")
    @GlobalTransactional(name = "springCloud",rollbackFor = Exception.class)
    public ResponseEntity<Storage> Buy(@PathVariable Long productId,@PathVariable Integer count) {
        Storage storage = storageService.queryByStorageId(productId);
        storage.setResidue(storage.getResidue() - count);
        storage.setUsed(storage.getUsed() + count);
        Order order = new Order();
        order.setCount(count);
        order.setUserId(3L);
        order.setProductId(productId);
        order.setMoney(count*10.0);
        order.setStatus(1);
        ResponseEntity<Order> add = empApi.add(order);
        int i = 1 / 0;//模拟一个异常使得事务发生异常(订单添加成功，单没有更新库存)
        return ResponseEntity.ok(this.storageService.update(storage));
    }

    /**
     * 分页查询
     *
     * @param storage 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<Storage>> queryByPage(Storage storage, PageRequest pageRequest) {
        return ResponseEntity.ok(this.storageService.queryByPage(storage, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Storage> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.storageService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param storage 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Storage> add(Storage storage) {
        return ResponseEntity.ok(this.storageService.insert(storage));
    }

    /**
     * 编辑数据
     *
     * @param storage 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<Storage> edit(Storage storage) {
        return ResponseEntity.ok(this.storageService.update(storage));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.storageService.deleteById(id));
    }

}

