package priv.noby.redis.service.impl;

import priv.noby.redis.entity.Dep;
import priv.noby.redis.dao.DepDao;
import priv.noby.redis.service.DepService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * (Dep)表服务实现类
 *
 * @author makejava
 * @since 2022-07-29 21:37:10
 */
@Service("depService")
public class DepServiceImpl implements DepService {
    @Resource
    private DepDao depDao;

    /**
     * 通过ID查询单条数据
     *
     * @param did 主键
     * @return 实例对象
     */
    @Override
    public Dep queryById(Integer did) {
        return this.depDao.queryById(did);
    }

    /**
     * 分页查询
     *
     * @param dep 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<Dep> queryByPage(Dep dep, PageRequest pageRequest) {
        long total = this.depDao.count(dep);
        return new PageImpl<>(this.depDao.queryAllByLimit(dep, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param dep 实例对象
     * @return 实例对象
     */
    @Override
    public Dep insert(Dep dep) {
        this.depDao.insert(dep);
        return dep;
    }

    /**
     * 修改数据
     *
     * @param dep 实例对象
     * @return 实例对象
     */
    @Override
    public Dep update(Dep dep) {
        this.depDao.update(dep);
        return this.queryById(dep.getDid());
    }

    /**
     * 通过主键删除数据
     *
     * @param did 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer did) {
        return this.depDao.deleteById(did) > 0;
    }
}
