//package priv.noby.common.util;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//
//import java.util.Date;
///**
// * Jwt 算法登录验证
// */
//public class JwtUtil {
//
//    //密钥
//    private static final String SECRET = "noby";
//    //签发者
//    private static final String ISSUSER = "java";
//    //过期时间 30分钟
//    private static final long EXPIRES = 30 * 60 * 1000;
//
//    //生成token
//    public static String create(String username) {
//        //指定加密算法
//        Algorithm algorithm = Algorithm.HMAC256(SECRET);
//        //当前时间
//        Date now = new Date();
//        //过期时间
//        Date expires = new Date(now.getTime() + EXPIRES);
//        //产生token
//        String jwt = JWT.create()
//                .withIssuer(ISSUSER)           //指定签发人
//                .withIssuedAt(now)              //签发时间
//                .withExpiresAt(expires)         //过期时间
//                .withClaim("username", username)      //将用户名作为自定信息添加到载荷中
//                .sign(algorithm);               //使用前面的参数和加密算法成token
//        return jwt;
//    }
//
//    //验证token是否有效
//    public static boolean verify(String token) {
//        try {
//            //加密算法
//            Algorithm algorithm = Algorithm.HMAC256(SECRET);
//            //生产验证器
//            JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUSER).build();
//            //验证：验证失败抛异常
//            verifier.verify(token);
//            return true;
//        } catch (Exception ex) {
//            //ex.printStackTrace();
//            return false;
//        }
//    }
//
//    //解析自定义信息
//    public static String getUsername(String token) {
//        try {
//            //jwt反向解码，获取自定义信息的值
//            return JWT.decode(token).getClaim("username").asString();
//        } catch (Exception ex) {
//            //ex.printStackTrace();
//            return "";
//        }
//    }
//
//
//    //判断是否超时
//    public static boolean isExpires(String token) {
//        Date now = new Date();
//        Date expire = JWT.decode(token).getExpiresAt();
//        return now.after(expire);
//    }
//}
