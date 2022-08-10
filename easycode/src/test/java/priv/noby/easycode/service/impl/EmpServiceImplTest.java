package priv.noby.easycode.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import priv.noby.easycode.entity.Emp;
import priv.noby.easycode.service.EmpService;

import java.util.List;

@SpringBootTest
class EmpServiceImplTest {
    @Autowired
    EmpService empService;
    @Test
    void queryById() {
    }

    @Test
    void queryByPage() {
        Emp emp = new Emp();
        emp.setDid(2);
        PageRequest pageRequest = PageRequest.of(0, 20);
        Page<Emp> page = empService.queryByPage(emp,pageRequest);
        List<Emp> emps = page.getContent();
        System.out.println("emps = " + emps);
    }

    @Test
    void insert() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }
}