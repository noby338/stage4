package priv.noby.redis.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 分布式环境使用redis的session登录(不使用redis的情况下集群中每个tomcat存储的是自己的Session信息)
 * 添加依赖即可分布式环境使用redis的session登录 spring-session-data-redis
 */
@RestController
public class LoginController {

    @Value("${server.port}")
    String port;

    @RequestMapping("/login")
    public String login(HttpSession session){
        session.setAttribute("user", "userInfo");
        return "login--ok--"+port;
    }

    @RequestMapping("/test")
    public String test(HttpSession session){
        Object user = session.getAttribute("user");
        return "test--"+user+"--"+port;
    }
}
