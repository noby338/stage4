package priv.noby.redis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// 启用声明式注解
@EnableCaching
// 配置包扫描，创建该包下接口的实现类
@MapperScan("priv.noby.redis.dao")
// 配置注解驱动
@EnableTransactionManagement
@SpringBootApplication
public class RedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }

}
