package priv.noby.config.test.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 查看远程配置
 */
//配置自动刷新注解
@RefreshScope
@RestController
public class TestController {

    @Value("${info}")
    String info;
    @Value("${shared}")
    String shared;

    @RequestMapping("info")
    public ResponseEntity<String> info() {
        return ResponseEntity.ok(info);
    }

    @RequestMapping("shared")
    public ResponseEntity<String> shared() {
        return ResponseEntity.ok(shared);
    }

}

