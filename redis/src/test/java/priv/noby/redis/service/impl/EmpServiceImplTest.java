package priv.noby.redis.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import priv.noby.redis.entity.Emp;
import priv.noby.redis.service.EmpService;

/**
 * @Description 测试类
 * @Author noby
 * @Date 2022/7/29 22:30
 */
@SpringBootTest
class EmpServiceImplTest {
    @Autowired
    EmpService empService;

    @Test
    void testInsert() {
        Emp emp = new Emp();
        emp.setEname("nb");
        emp.setEid(34);
        emp.setDid(1002);
        emp.setHiredate("2020-1-1");
        Emp insert = empService.insert(emp);
        System.out.println("insert = " + insert);
    }
}