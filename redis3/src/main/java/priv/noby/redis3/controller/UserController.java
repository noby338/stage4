package priv.noby.redis3.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author Noby
 * @since 2022/11/13
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Value("${server.port}")
    String port;

    /**
     * 使用nginx，从8080端口login存入session
     *
     * @param session
     * @return
     */
    @RequestMapping("/login")
    public String login(HttpSession session){
        session.setAttribute("user", "noby");
        return "已登录端口:"+port+"，并存入session:user";
    }

    /**
     * 使用nginx，从8081端口login获取session
     *
     * @param session
     * @return
     */
    @RequestMapping("/getSession")
    public String getSession(HttpSession session){
        String user = (String)session.getAttribute("user");
        return "已登录端口:"+port+"，获取session:"+user;
    }
}
