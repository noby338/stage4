package priv.noby.consumer.emp.service.impl;

import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import priv.noby.common.entity.Order;
import priv.noby.common.entity.Storage;
import priv.noby.consumer.emp.dao.StorageDao;
import priv.noby.consumer.emp.openFeignClient.EmpApi;
import priv.noby.consumer.emp.service.StorageService;

import javax.annotation.Resource;

/**
 * seata管理的分布式事务
 * 减少某库存数量，同时通过调用生产者的controller添加一个订单，再调用本微服务修改库存
 * 需要配置DataSourceConfiguration数据源代理，并在启动类排除自动配置
 * @author Noby
 * @since 2022-08-15 20:50:08
 */
@Service("storageService")
public class StorageServiceImpl implements StorageService {
    @Resource
    private StorageDao storageDao;
    @Resource
    private EmpApi empApi;

    @Override
    @GlobalTransactional
//    @GlobalTransactional(name = "springCloud",rollbackFor = Exception.class)
    public Storage buy(Long productId, Integer count) {
        //添加订单
        Order order = new Order();
        order.setCount(count);
        order.setUserId(3L);
        order.setProductId(productId);
        order.setMoney(count*10.0);
        order.setStatus(1);
        empApi.add(order);
        int i = 1 / 0;//模拟一个异常使得事务发生异常(若没配置全局事务，订单添加成功，但没有更新库存)
        //修改库存
        Storage storage = queryByStorageId(productId);
        storage.setResidue(storage.getResidue() - count);
        storage.setUsed(storage.getUsed() + count);
        update(storage);
        return storage;
    }

    /**
     * 通过ID查询单条数据
     *
     * @param storageId 主键
     * @return 实例对象
     */
    @Override
    public Storage queryByStorageId(Long storageId) {
        return this.storageDao.queryByProductId(storageId);
    }
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Storage queryById(Long id) {
        return this.storageDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param storage 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<Storage> queryByPage(Storage storage, PageRequest pageRequest) {
        long total = this.storageDao.count(storage);
        return new PageImpl<>(this.storageDao.queryAllByLimit(storage, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param storage 实例对象
     * @return 实例对象
     */
    @Override
    public Storage insert(Storage storage) {
        this.storageDao.insert(storage);
        return storage;
    }

    /**
     * 修改数据
     *
     * @param storage 实例对象
     * @return 实例对象
     */
    @Override
    public Storage update(Storage storage) {
        this.storageDao.update(storage);
        return this.queryById(storage.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.storageDao.deleteById(id) > 0;
    }
}
