package priv.noby.redis2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

// 启用声明式缓存
@EnableCaching
@MapperScan("priv.noby.redis2.dao")
@SpringBootApplication
public class Redis2Application {

    public static void main(String[] args) {
        SpringApplication.run(Redis2Application.class, args);
    }

}
