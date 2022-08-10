package priv.noby.redis.service.impl;

import priv.noby.redis.entity.Emp;
import priv.noby.redis.dao.EmpDao;
import priv.noby.redis.service.EmpService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (Emp)表服务实现类
 *
 * @author makejava
 * @since 2022-07-29 21:37:11
 */
@Service("empService")
public class EmpServiceImpl implements EmpService {
    @Resource
    private EmpDao empDao;

    /**
     * 通过ID查询单条数据
     *
     * @param eid 主键
     * @return 实例对象
     */
    @Override
    public Emp queryById(Integer eid) {
        return this.empDao.queryById(eid);
    }

    /**
     * 分页查询
     *
     * @param emp 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<Emp> queryByPage(Emp emp, PageRequest pageRequest) {
        long total = this.empDao.count(emp);
        return new PageImpl<>(this.empDao.queryAllByLimit(emp, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param emp 实例对象
     * @return 实例对象
     */
    @Override
    public Emp insert(Emp emp) {
        this.empDao.insert(emp);
        return emp;
    }

    /**
     * 修改数据
     *
     * @param emp 实例对象
     * @return 实例对象
     */
    @Override
    public Emp update(Emp emp) {
        this.empDao.update(emp);
        return this.queryById(emp.getEid());
    }

    /**
     * 通过主键删除数据
     *
     * @param eid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer eid) {
        return this.empDao.deleteById(eid) > 0;
    }
}
