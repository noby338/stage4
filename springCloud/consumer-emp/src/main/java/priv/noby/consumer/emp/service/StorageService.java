package priv.noby.consumer.emp.service;

import priv.noby.common.entity.Storage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (Storage)表服务接口
 *
 * @author Noby
 * @since 2022-08-15 20:50:07
 */
public interface StorageService {

    /**
     * 通过ID查询单条数据
     *
     * @param storageId 主键
     * @return 实例对象
     */
    Storage queryByStorageId(Long storageId);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Storage queryById(Long id);

    /**
     * 分页查询
     *
     * @param storage 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<Storage> queryByPage(Storage storage, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param storage 实例对象
     * @return 实例对象
     */
    Storage insert(Storage storage);

    /**
     * 修改数据
     *
     * @param storage 实例对象
     * @return 实例对象
     */
    Storage update(Storage storage);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
