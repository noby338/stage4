package priv.noby.easycode;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// 配置包扫描，创建该包下接口的实现类
@MapperScan("priv.noby.easycode.dao")
// 配置注解驱动
@EnableTransactionManagement
@SpringBootApplication
public class EasycodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasycodeApplication.class, args);
    }

}
