package priv.noby.redis2.dao;

import priv.noby.redis2.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (Student)表数据库访问层
 *
 * @author Noby
 * @since 2022-11-11 21:48:40
 */
public interface StudentDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Student queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param student 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<Student> queryAllByLimit(@Param("student") Student student, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param student 查询条件
     * @return 总行数
     */
    long count(Student student);

    /**
     * 新增数据
     *
     * @param student 实例对象
     * @return 影响行数
     */
    int insert(Student student);

    /**
     * 修改数据
     *
     * @param student 实例对象
     * @return 影响行数
     */
    int update(Student student);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

