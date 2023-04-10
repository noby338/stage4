package priv.noby.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import priv.noby.common.entity.ResponseResult;
import priv.noby.common.util.JwtUtil;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class LoginFilter implements GlobalFilter, Ordered {
    /**
     * 自定义过滤拦截
     */
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        System.out.println("LoginFilter：进入");
//        ServerHttpRequest request = exchange.getRequest();
////        request.getQueryParams().get("username").get(0);
//        String username = request.getQueryParams().getFirst("username");
//        if(username==null){
//            System.out.println("LoginFilter：拦截请求");
//            ServerHttpResponse response = exchange.getResponse();
//            //设置响应码
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            //拦截:响应完成
//            return response.setComplete();
//        }
//        //放行
//        return chain.filter(exchange);
//    }

    /**
     * 自定义过滤拦截 使用统一的响应体
     */
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        System.out.println("LoginFilter：进入");
//        ServerHttpRequest request = exchange.getRequest();
//        String username = request.getQueryParams().getFirst("username");
//        if(username==null){
//            System.out.println("LoginFilter：拦截请求");
//            ServerHttpResponse response = exchange.getResponse();
//            //拦截使用统一响应体
//            ResponseResult<String> responseResult=new ResponseResult<>(401, "login first", "先登录");
//            try {
//                byte[] bytes = new ObjectMapper().writeValueAsString(responseResult)
//                        .getBytes(StandardCharsets.UTF_8);
//                DataBuffer buffer = response.bufferFactory().wrap(bytes);
//                //响应体JSON，设置中文乱码
//                response.getHeaders().add("Content-Type","application/json;charset=utf-8");
//                return response.writeWith(Mono.just(buffer));
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//        //放行
//        return chain.filter(exchange);
//    }

    /**
     * 自定义过滤拦截 验证token
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("LoginFilter：进入");
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        //特殊路径放行，不需要拦截  /auth/login
        if (request.getURI().getPath().contains("/auth/login")) {
            System.out.println("LoginFilter：请求登录，直接放行");
            return chain.filter(exchange);
        }
        System.out.println("LoginFilter：其它请求验证token");

        //请求其它页面，请求头应该携带token
        String token = request.getHeaders().getFirst("token");
        if (!StringUtils.isEmpty(token) && JwtUtil.verify(token)) {
            System.out.println("LoginFilter：token有效，放行");
            return chain.filter(exchange);
        }

        System.out.println("LoginFilter：token无效，拦截");

        //拦截使用统一响应体
        ResponseResult<String> responseResult = new ResponseResult<>(401, "login first", "先登录");
        try {
            byte[] bytes = new ObjectMapper().writeValueAsString(responseResult)
                    .getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            //响应体JSON，设置中文乱码
            response.getHeaders().add("Content-Type", "application/json;charset=utf-8");
            return response.writeWith(Mono.just(buffer));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    //多个Filter执行顺序
    @Override
    public int getOrder() {
        return 0;
    }
}