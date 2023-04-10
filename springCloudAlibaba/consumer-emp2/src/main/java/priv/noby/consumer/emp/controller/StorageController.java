package priv.noby.consumer.emp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.noby.common.entity.Storage;
import priv.noby.consumer.emp.service.StorageService;

import javax.annotation.Resource;

/**
 * seata管理的分布式事务
 * 减少某库存数量，同时通过调用生产者的controller添加一个订单，再调用本微服务修改库存
 */
@RestController
@RequestMapping("storage")
public class StorageController {
    @Resource
    private StorageService storageService;
    @PutMapping("/buy/{productId}/{count}")
    public ResponseEntity<Storage> Buy(@PathVariable Long productId,@PathVariable Integer count) {
        return ResponseEntity.ok(storageService.buy(productId,count));
    }
}

