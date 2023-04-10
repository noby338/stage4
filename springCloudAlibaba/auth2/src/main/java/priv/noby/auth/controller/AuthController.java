package priv.noby.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.noby.common.entity.ResponseResult;
import priv.noby.common.entity.User;
import priv.noby.common.util.JwtUtil;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @PostMapping("/login")
    public ResponseResult<String> login(@RequestBody User user, HttpServletResponse response){
        //验证用户名密码
        if("noby".equals(user.getName())&&"123".equals(user.getPassword())){
            //如果有效，生成token,通过响应头，响应给用户
            String token = JwtUtil.create(user.getName());
            response.setHeader("token",token);
            //暴露响应头
            response.setHeader("Access-Control-Expose-Headers", "token");
            return new ResponseResult<>(200, "login-ok", "登录成功");
        }
        return new ResponseResult<>(401, "login-error", "登录失败");
    }
}
