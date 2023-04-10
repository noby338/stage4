package priv.noby.redis2.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import priv.noby.redis2.entity.Student;

import javax.annotation.Resource;

/**
 * @author Noby
 * @since 2022/11/11
 */
@SpringBootTest
class StudentServiceTest {
    @Resource
    StudentService studentService;

    @Test
    void queryById() {
        System.out.println("第一次查询");
        System.out.println("studentService.queryById(4) = " + studentService.queryById(4));
        System.out.println("第二次查询");
        System.out.println("studentService.queryById(4) = " + studentService.queryById(4));
    }

    /**
     * 不满足 condition 条件直接去数据库查询
     */
    @Test
    void queryById2() {
        System.out.println("第一次查询");
        System.out.println("studentService.queryById(1) = " + studentService.queryById(1));
        System.out.println("第二次查询");
        System.out.println("studentService.queryById(1) = " + studentService.queryById(1));
    }

    /**
     * 条件查询
     * 通过配置类设置该缓存的ttl
     */
    @Test
    void queryByPage() {
        Student student = new Student();
        student.setGender(1);
        Page<Student> students = studentService.queryByPage(student, PageRequest.of(1, 3));
        System.out.println("students = " + students.getContent());
    }

    /**
     * 添加操作
     */
    @Test
    void insert() {
        Student student = new Student();
        student.setName("新增的学生");
        student.setAge(20);        student.setGender(1);
        Student insert = studentService.insert(student);
        System.out.println("insert = " + insert);
    }

    /**
     * 修改操作
     */
    @Test
    void update() {
        Student student = new Student();
        student.setName("修改后的学生");
        student.setId(31);
        Student update = studentService.update(student);
        System.out.println("update = " + update);
    }

    /**
     * 删除操作
     *
     */
    @Test
    void delete() {
        boolean b = studentService.deleteById(31);
        System.out.println("b = " + b);
    }
}