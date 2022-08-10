package priv.noby.easycode.service;

import priv.noby.easycode.entity.Emp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (Emp)表服务接口
 *
 * @author Noby
 * @since 2022-08-09 11:34:44
 */
public interface EmpService {

    /**
     * 通过ID查询单条数据
     *
     * @param eid 主键
     * @return 实例对象
     */
    Emp queryById(Integer eid);

    /**
     * 分页查询
     *
     * @param emp 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<Emp> queryByPage(Emp emp, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param emp 实例对象
     * @return 实例对象
     */
    Emp insert(Emp emp);

    /**
     * 修改数据
     *
     * @param emp 实例对象
     * @return 实例对象
     */
    Emp update(Emp emp);

    /**
     * 通过主键删除数据
     *
     * @param eid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer eid);

}
