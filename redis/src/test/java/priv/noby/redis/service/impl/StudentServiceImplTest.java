package priv.noby.redis.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import priv.noby.redis.entity.Student;
import priv.noby.redis.service.StudentService;

@SpringBootTest
class StudentServiceImplTest {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    StudentService studentService;

    @Test
    void queryById() {
        Student student = studentService.queryById(4);
        System.out.println("student = " + student);
    }

    @Test
    void queryByI2() {
        Student student = studentService.queryById2(6);
        System.out.println("student = " + student);
    }

    @Test
    void insert() {
        Student tom = new Student("tom", 21, true);
        studentService.insert(tom);
    }

    @Test
    void update() {
        studentService.update(new Student(13,"lucy", 21, true));
    }

    @Test
    void deleteById() {
        studentService.deleteById(13);
    }
}