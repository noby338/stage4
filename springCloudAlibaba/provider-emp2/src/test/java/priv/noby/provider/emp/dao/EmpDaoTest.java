package priv.noby.provider.emp.dao;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class EmpDaoTest {
    @Resource
    EmpDao empDao;

    @Test
    void queryById() {
        System.out.println("empDao.queryById(1) = " + empDao.queryById(1));
    }

}