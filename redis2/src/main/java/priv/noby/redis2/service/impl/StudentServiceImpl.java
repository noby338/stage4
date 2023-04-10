package priv.noby.redis2.service.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import priv.noby.redis2.dao.StudentDao;
import priv.noby.redis2.entity.Student;
import priv.noby.redis2.service.StudentService;

import javax.annotation.Resource;

/**
 * (Student)表服务实现类
 *
 * @author Noby
 * @since 2022-11-11 21:48:40
 */
//统一指定该service的所有缓存名(在未@Cacheable中未指定value的情况下)
//@CacheConfig(cacheNames = "student")
@Service("studentService")
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentDao studentDao;

    /**
     * 通过ID查询单条数据
     *
     * value 为缓存名(为固定值)，和key组合成为整体的Redis中的key
     * key 为缓存名(为请求中的变动值)，和value组成为整体的Redis中的key
     * condition 表示满足条件的使用缓存，否则使用数据库
     *
     * @param id 主键
     * @return 实例对象
     */
    //    @Cacheable(key = "#id", condition = "#id>3")
    @Cacheable(value = "student:id", key = "#id", condition = "#id>3")
    @Override
    public Student queryById(Integer id) {
        return this.studentDao.queryById(id);
    }

    /**
     * 分页查询
     * 再次演示key的作用
     * 来自请求参数student中的gender
     * 通过配置类指定该缓存的ttl
     *
     * @param student 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Cacheable(value = "student:gender", key = "#student.gender")
    @Override
    public Page<Student> queryByPage(Student student, PageRequest pageRequest) {
        long total = this.studentDao.count(student);
        return new PageImpl<>(this.studentDao.queryAllByLimit(student, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     * 将返回的值传入缓存
     * mybatis已经将插入时的生成的参数id返回给了student对象
     *
     * @param student 实例对象
     * @return 实例对象
     */
    @CachePut(value = "student:id", key = "#student.id")
    @Override
    public Student insert(Student student) {
        this.studentDao.insert(student);
        return student;
    }

    /**
     * 修改数据
     *
     * @param student 实例对象
     * @return 实例对象
     */
    @CachePut(value = "student:id", key = "#student.id")
    @Override
    public Student update(Student student) {
        this.studentDao.update(student);
        return this.queryById(student.getId());
    }

    /**
     * 通过主键删除数据
     * beforeInvocation:默认值false：在主业务执行之后执行,true在主业务之前执行
     *
     * @param id 主键
     * @return 是否成功
     */
    @CacheEvict(value = "student:id",key = "#id",beforeInvocation = true)
    @Override
    public boolean deleteById(Integer id) {
        return this.studentDao.deleteById(id) > 0;
    }
}
