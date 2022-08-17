package priv.noby.consumer.emp.service.impl;

import priv.noby.common.entity.Storage;
import priv.noby.consumer.emp.dao.StorageDao;
import priv.noby.consumer.emp.service.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (Storage)表服务实现类
 *
 * @author Noby
 * @since 2022-08-15 20:50:08
 */
@Service("storageService")
public class StorageServiceImpl implements StorageService {
    @Resource
    private StorageDao storageDao;

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
