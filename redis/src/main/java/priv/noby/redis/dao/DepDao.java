package priv.noby.redis.dao;

import priv.noby.redis.entity.Dep;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (Dep)表数据库访问层
 *
 * @author makejava
 * @since 2022-07-29 21:37:09
 */
public interface DepDao {

    /**
     * 通过ID查询单条数据
     *
     * @param did 主键
     * @return 实例对象
     */
    Dep queryById(Integer did);

    /**
     * 查询指定行数据
     *
     * @param dep 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<Dep> queryAllByLimit(Dep dep, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param dep 查询条件
     * @return 总行数
     */
    long count(Dep dep);

    /**
     * 新增数据
     *
     * @param dep 实例对象
     * @return 影响行数
     */
    int insert(Dep dep);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Dep> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Dep> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Dep> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Dep> entities);

    /**
     * 修改数据
     *
     * @param dep 实例对象
     * @return 影响行数
     */
    int update(Dep dep);

    /**
     * 通过主键删除数据
     *
     * @param did 主键
     * @return 影响行数
     */
    int deleteById(Integer did);

}

