package priv.noby.redis.dao;

import priv.noby.redis.entity.Good;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (Good)表数据库访问层
 *
 * @author Noby
 * @since 2022-08-11 16:32:27
 */
public interface GoodDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Good queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param good 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<Good> queryAllByLimit(@Param("good") Good good, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param good 查询条件
     * @return 总行数
     */
    long count(Good good);

    /**
     * 新增数据
     *
     * @param good 实例对象
     * @return 影响行数
     */
    int insert(Good good);

    /**
     * 修改数据
     *
     * @param good 实例对象
     * @return 影响行数
     */
    int update(Good good);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

