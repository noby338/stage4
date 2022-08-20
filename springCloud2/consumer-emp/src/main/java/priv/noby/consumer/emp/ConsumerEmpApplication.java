package priv.noby.consumer.emp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// 配置包扫描，创建该包下接口的实现类
@MapperScan("priv.noby.consumer.emp.dao")
// 配置注解驱动
@EnableTransactionManagement
@EnableFeignClients
@SpringBootApplication
//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ConsumerEmpApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerEmpApplication.class, args);
    }

}
