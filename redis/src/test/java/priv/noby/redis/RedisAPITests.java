package priv.noby.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;
/**
 * jedis 的使用(java 使用 redis 的原生用法)
 */
@SpringBootTest
class RedisAPITests {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /**
     * jedis 的基本使用
     */
    @Test
    void redis() {
        Jedis jedis = new Jedis("192.168.122.128", 6379);
        jedis.auth("123");
        jedis.set("str", "info");
        String str = jedis.get("str");
        System.out.println("str = " + str);

        jedis.hset("hash", "h1", "1");
        jedis.hset("hash", "h2", "2");
        jedis.hset("hash", "h3", "3");
        Map<String, String> hash = jedis.hgetAll("hash");
        System.out.println("hash = " + hash);

        System.out.println(jedis.keys("*"));

        jedis.close();

    }

    /**
     * jedis 配置连接池
     */
    @Test
    void redis2() {
        //连接池配置
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(50);//设置最大连接数
        jedisPoolConfig.setMaxIdle(10);//设置最大空闲
        //连接池对象
        //final GenericObjectPooLConfig pooLConfig,连接池配置
        ////final String host,int port,主机端口
        //int timeout, final String password 等待时间密码
        JedisPool jedisPooL = new JedisPool(jedisPoolConfig, "192.168.122.128", 6379, 2000, "123");
        Jedis jedis = jedisPooL.getResource();
        jedis.set("pool", "info");
        String pool = jedis.get("pool");
        System.out.println("pool = " + pool);
        jedis.close();
    }
}
