package priv.noby.config.test.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (Emp)表控制层
 *
 * @author Noby
 * @since 2022-08-12 13:57:14
 */
//配置自动刷新注解
@RefreshScope
@RestController
public class TestController {

    @Value("${info}")
    String info;
    @RequestMapping("info")
    public ResponseEntity<String> info() {
        return ResponseEntity.ok(info);
    }
}

