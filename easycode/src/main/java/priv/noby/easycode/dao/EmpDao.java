package priv.noby.easycode.dao;

import priv.noby.easycode.entity.Emp;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (Emp)表数据库访问层
 *
 * @author Noby
 * @since 2022-08-09 11:34:44
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
    List<Emp> queryAllByLimit(@Param("emp") Emp emp, @Param("pageable") Pageable pageable);

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

