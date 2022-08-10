package priv.noby.redis.dao;

import priv.noby.redis.entity.Emp;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (Emp)表数据库访问层
 *
 * @author makejava
 * @since 2022-07-29 21:37:11
 */
public interface EmpDao {

    /**
     * 通过ID查询单条数据
     *
     * @param eid 主键
     * @return 实例对象
     */
    Emp queryById(Integer eid);

    /**
     * 查询指定行数据
     *
     * @param emp 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<Emp> queryAllByLimit(Emp emp, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param emp 查询条件
     * @return 总行数
     */
    long count(Emp emp);

    /**
     * 新增数据
     *
     * @param emp 实例对象
     * @return 影响行数
     */
    int insert(Emp emp);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Emp> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Emp> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Emp> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Emp> entities);

    /**
     * 修改数据
     *
     * @param emp 实例对象
     * @return 影响行数
     */
    int update(Emp emp);

    /**
     * 通过主键删除数据
     *
     * @param eid 主键
     * @return 影响行数
     */
    int deleteById(Integer eid);

}

