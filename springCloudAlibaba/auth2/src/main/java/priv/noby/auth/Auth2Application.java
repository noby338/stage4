package priv.noby.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class Auth2Application {
    public static void main(String[] args) {
        SpringApplication.run(Auth2Application.class, args);
    }
}
