package priv.noby.easycode.service;

import priv.noby.easycode.entity.Dep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (Dep)表服务接口
 *
 * @author Noby
 * @since 2022-08-09 11:34:44
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
