package priv.noby.consumer.emp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

// 配置包扫描，创建该包下接口的实现类
@MapperScan("priv.noby.consumer.emp.dao")
//断路器
@EnableDiscoveryClient
@EnableFeignClients
//@SpringBootApplication
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ConsumerEmp2Application {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerEmp2Application.class, args);
    }

}
