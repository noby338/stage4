package priv.noby.easycode.dao;

import priv.noby.easycode.entity.Dep;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (Dep)表数据库访问层
 *
 * @author Noby
 * @since 2022-08-09 11:34:44
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
    List<Dep> queryAllByLimit(@Param("dep") Dep dep, @Param("pageable") Pageable pageable);

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

