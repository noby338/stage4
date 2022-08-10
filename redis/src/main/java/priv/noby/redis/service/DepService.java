package priv.noby.redis.service;

import priv.noby.redis.entity.Dep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (Dep)表服务接口
 *
 * @author makejava
 * @since 2022-07-29 21:37:10
 */
public interface DepService {

    /**
     * 通过ID查询单条数据
     *
     * @param did 主键
     * @return 实例对象
     */
    Dep queryById(Integer did);

    /**
     * 分页查询
     *
     * @param dep 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<Dep> queryByPage(Dep dep, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param dep 实例对象
     * @return 实例对象
     */
    Dep insert(Dep dep);

    /**
     * 修改数据
     *
     * @param dep 实例对象
     * @return 实例对象
     */
    Dep update(Dep dep);

    /**
     * 通过主键删除数据
     *
     * @param did 主键
     * @return 是否成功
     */
    boolean deleteById(Integer did);

}
