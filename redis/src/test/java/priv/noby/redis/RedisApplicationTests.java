package priv.noby.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import priv.noby.redis.entity.Student;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;

@SpringBootTest
class RedisApplicationTests {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Test
    void contextLoads() {
    }

    /**
     * jedis 的基本使用
     */
    @Test
    void redis() {
        Jedis jedis = new Jedis("192.168.32.128", 6379);
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
        JedisPool jedisPooL = new JedisPool(jedisPoolConfig, "192.168.32.128", 6379, 2000, "123");
        Jedis jedis = jedisPooL.getResource();
        jedis.set("pool", "info");
        String pool = jedis.get("pool");
        System.out.println("pool = " + pool);
        jedis.close();
    }

    /**
     * stringRedisTemplate 的基本使用
     */
    @Test
    void stringRedisTemplate() throws JsonProcessingException {
        ValueOperations<String, String> forValue = stringRedisTemplate.opsForValue();
        HashOperations<String, Object, Object> forHash = stringRedisTemplate.opsForHash();
        ListOperations<String, String> forList = stringRedisTemplate.opsForList();
        SetOperations<String, String> forSet = stringRedisTemplate.opsForSet();
        ZSetOperations<String, String> forZSet = stringRedisTemplate.opsForZSet();
        Student noby = new Student("noby", 20, true);
        ObjectMapper objectMapper = new ObjectMapper();
        //String 类型的 redis 数据类型存储对象需要将对象序列化为 json
        forValue.set("nobyJson", objectMapper.writeValueAsString(noby));
        //反序列化
        Student nobyJson = objectMapper.readValue(forValue.get("nobyJson"), Student.class);
        System.out.println("nobyJson = " + nobyJson);
    }

    /**
     * redisTemplate 的基本使用
     */
    @Test
    void redisTemplate() {
        ValueOperations<String, Object> forValue = redisTemplate.opsForValue();
        HashOperations<String, Object, Object> forHash = redisTemplate.opsForHash();
        ListOperations<String, Object> forList = redisTemplate.opsForList();
        SetOperations<String, Object> forSet = redisTemplate.opsForSet();
        ZSetOperations<String, Object> forZSet = redisTemplate.opsForZSet();
        Student kace = new Student("kace", 21, true);
        forValue.set("kace", kace);
        Student kace1 = (Student) forValue.get("kace");
        System.out.println("kace1 = " + kace1);
    }

    /**
     * redisTemplate 中的常用API
     */
    @Test
    void redisTemplateAPI() {
        ValueOperations<String, Object> forValue = redisTemplate.opsForValue();
        //exists => hasKey()
        System.out.println("redisTemplate.hasKey(\"noby\") = " + redisTemplate.hasKey("noby"));
        //ttl =>

    }
}
