package priv.noby.redis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import priv.noby.redis.dao.StudentDao;
import priv.noby.redis.entity.Student;
import priv.noby.redis.service.StudentService;

import javax.annotation.Resource;

/**
 * 缓存在业务中的应用
 */
@Service("studentService")
public class StudentServiceImpl implements StudentService {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Resource
    private StudentDao studentDao;

    /**
     * 编程式缓存的操作
     */
    @Override
    public Student queryById(Integer id) {
        //查看缓存中是否有数据
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        String studentStr = String.format("student:%s", id);
        Object rData = opsForValue.get(studentStr);
        if (rData != null) {
            System.out.println("读取 redis 中的数据");
            return ((Student) rData);
        } else {
            System.out.println("读取 mysql 中的数据");
            Student student = studentDao.queryById(id);
            opsForValue.set(studentStr, student);
            return student;
        }
    }

    /**
     * Springboot redis 声明式的缓存操作 查询
     * value redis中的组
     * key redis中的键
     * condition 添加缓存的条件
     */
//    @Cacheable(value = "student", key = "#id", condition = "#id>3")
    @Cacheable(value = "student", key = "#id")
    @Override
    public Student queryById2(Integer id) {
        Student student = studentDao.queryById(id);
        return student;
    }

    /**
     * 添加的缓存操作
     * @CachePut 添加后存入缓存
     */
    @CachePut(value = "student",key = "#student.id")
    @Override
    public Student insert(Student student) {
        this.studentDao.insert(student);
        return student;
    }

    /**
     * 修改的缓存操作
     * @CachePut 修改后存入缓存
     */
    @CachePut(value = "student",key="#student.id")
    @Override
    public Student update(Student student) {
        this.studentDao.update(student);
        return student;
    }

    /**
     * 删除的缓存操作
     * @CacheEvict 删除数据的同时删除缓存
     * beforeInvocation = true 在主业务执行之前执行，一般设置之后执行
     */
    @CacheEvict(value = "student",key = "#id",beforeInvocation = false)
    @Override
    public boolean deleteById(Integer id) {
        return this.studentDao.deleteById(id) > 0;
    }
}
