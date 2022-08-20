package priv.noby.provider.emp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
// 配置包扫描，创建该包下接口的实现类
@MapperScan("priv.noby.provider.emp.dao")
// 配置注解驱动
@EnableTransactionManagement
@SpringBootApplication
//启用seata的注解
//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ProviderEmpApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderEmpApplication.class, args);
    }

}
