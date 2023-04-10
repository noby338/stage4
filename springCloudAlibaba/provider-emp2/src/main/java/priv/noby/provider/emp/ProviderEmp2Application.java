package priv.noby.provider.emp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;
// 配置包扫描，创建该包下接口的实现类
@MapperScan("priv.noby.provider.emp.dao")
// 配置注解驱动
@EnableDiscoveryClient
@EnableTransactionManagement
//@SpringBootApplication
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ProviderEmp2Application {

    public static void main(String[] args) {
        SpringApplication.run(ProviderEmp2Application.class, args);
    }

}
