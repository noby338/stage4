package priv.noby.redis3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

// 启用声明式注解
@EnableCaching
@SpringBootApplication
public class Redis3Application {

    public static void main(String[] args) {
        SpringApplication.run(Redis3Application.class, args);
    }

}
