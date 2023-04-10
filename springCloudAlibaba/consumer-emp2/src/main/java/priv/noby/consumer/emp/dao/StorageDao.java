package priv.noby.consumer.emp.dao;

import priv.noby.common.entity.Storage;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (Storage)表数据库访问层
 *
 * @author Noby
 * @since 2022-08-15 20:50:03
 */
public interface StorageDao {
    /**
     * 通过productId查询单条数据
     *
     * @param productId
     * @return 实例对象
     */
    Storage queryByProductId(Long productId);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Storage queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param storage 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<Storage> queryAllByLimit(@Param("storage") Storage storage, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param storage 查询条件
     * @return 总行数
     */
    long count(Storage storage);

    /**
     * 新增数据
     *
     * @param storage 实例对象
     * @return 影响行数
     */
    int insert(Storage storage);

    /**
     * 修改数据
     *
     * @param storage 实例对象
     * @return 影响行数
     */
    int update(Storage storage);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

