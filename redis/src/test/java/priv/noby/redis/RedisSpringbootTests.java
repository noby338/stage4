package priv.noby.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import priv.noby.redis.entity.Student;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Springboot 整合 Redis 的使用
 */
@SpringBootTest
class RedisSpringbootTests {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /**
     * stringRedisTemplate 配合 objectMapper 类序列化和发序列化的基本使用
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
     * redisTemplate 配合配置类指定数据类型修改序列化器的基本使用
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
     * redisTemplate 中的常用 API
     */
    @Test
    void redisTemplateAPI() {
        ValueOperations<String, Object> forValue = redisTemplate.opsForValue();
        forValue.set("a", "aa");
        System.out.println("redisTemplate.hasKey(\"a\") = " +
                redisTemplate.hasKey("a"));
        System.out.println("redisTemplate.getExpire(\"a\") = " + redisTemplate.getExpire("a"));
        System.out.println("redisTemplate.expire(\"a\", 30, TimeUnit.SECONDS) = " +
                redisTemplate.expire("a", 30, TimeUnit.SECONDS));
        System.out.println("redisTemplate.delete(\"a\") = " +
                redisTemplate.delete("a"));
        System.out.println("redisTemplate.keys(\"*\") = " +
                redisTemplate.keys("*"));
        System.out.println("redisTemplate.countExistingKeys(redisTemplate.keys(\"*\")) = " +
                redisTemplate.countExistingKeys(redisTemplate.keys("*")));
    }

    /**
     * redisTemplate 中的 String 的使用
     */
    @Test
    void redisTemplateString() {
        ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
        //String
        Student s1 = new Student(1, "noby", 20, true);
        Student s2 = new Student(2, "kace", 21, true);
        //存入的时候指定过期时间
        opsForValue.set("student:" + s1.getId(), s1, Duration.ofMinutes(30));
        opsForValue.set("student:" + s2.getId(), s2, 1000, TimeUnit.SECONDS);
        System.out.println("redisTemplate.getExpire(\"student:\"+s1.getId()) = " +
                redisTemplate.getExpire("student:" + s1.getId()));
        System.out.println("redisTemplate.getExpire(\"student:\"+s2.getId()) = " +
                redisTemplate.getExpire("student:" + s2.getId()));
        //存在才能添加 XX
        System.out.println("opsForValue.setIfPresent(\"student:\"+s1.getId(), s1) = " +
                opsForValue.setIfPresent("student:" + s1.getId(), s1));
        //不存在才能添加 NX
        System.out.println("opsForValue.setIfAbsent(\"student:\"+s1.getId(), s1) = " +
                opsForValue.setIfAbsent("student:" + s1.getId(), s1));

        //自增
        opsForValue.increment("num", 2);
        System.out.println("opsForValue.get(\"num\") = " +
                opsForValue.get("num"));
        opsForValue.set("num2", 1);
        System.out.println("opsForValue.increment(\"num2\",3) = " +
                opsForValue.increment("num2", 3));
    }

    /**
     * redisTemplate 中的 Hash 的使用
     */
    @Test
    void redisTemplateHash() {
        HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
        //hash 的存值
        opsForHash.put("h", "age1", 11);
        opsForHash.put("h2", "name", "zs");
        opsForHash.put("h2", "age", "11");
        //hget
        System.out.println("opsForHash.get(\"h2\",\"name\") = " + opsForHash.get("h2", "name"));
        System.out.println("opsForHash.get(\"h2\",\"age\") = " + opsForHash.get("h2", "age"));
        //hkeys
        System.out.println("opsForHash.keys(\"h2\") = " + opsForHash.keys("h2"));
        //hvals
        System.out.println("opsForHash.values(\"h2\") = " + opsForHash.values("h2"));
        //hgetall
        System.out.println("opsForHash.entries(\"h2\") = " + opsForHash.entries("h2"));
        //hash 的自增
        System.out.println("opsForHash.increment(\"h\", \"age1\", 1) = " + opsForHash.increment("h", "age1", 1));
    }

    /**
     * redisTemplate 中的 List 的使用
     */
    @Test
    void redisTemplateList() {
        ListOperations<String, Object> opsForList = redisTemplate.opsForList();
        opsForList.rightPushAll("list", "a", "b", "c", "d");
        opsForList.leftPush("list", "0");
        opsForList.rightPush("list", "1");
        //lrange 遍历后 list 还在
        System.out.println("opsForList.range(\"list\", 0, -1) = " + opsForList.range("list", 0, -1));
        Object str;
        //Pop 弹出后 list 删除
        while ((str = opsForList.leftPop("list")) != null) {
            System.out.println("str = " + str);
        }
    }

    /**
     * redisTemplate 中的 Set 的使用
     */
    @Test
    void redisTemplateSet() {
        SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
        opsForSet.add("s1", "a", "b", "c", "d");
        opsForSet.add("s2", "e", "f", "c", "d");
        System.out.println("opsForSet.members(\"s1\") = " +
                opsForSet.members("s1"));
        System.out.println("opsForSet.members(\"s2\") = " +
                opsForSet.members("s2"));
        System.out.println("opsForSet.isMember(\"s1\", \"0\") = " +
                opsForSet.isMember("s1", "0"));
        //s1 - s2
        System.out.println("opsForSet.difference(\"s1\", \"s2\") = " +
                opsForSet.difference("s1", "s2"));
        System.out.println("opsForSet.differenceAndStore(\"s1\", \"s2\", \"s3\") = " +
                opsForSet.differenceAndStore("s1", "s2", "s3"));
        System.out.println("opsForSet.members(\"s3\") = " +
                opsForSet.members("s3"));
        //s1 + s2
        System.out.println("opsForSet.union(\"s1\",\"s2\") = " +
                opsForSet.union("s1", "s2"));
        //s1 * s2
        System.out.println("opsForSet.intersect(\"s1\",\"s2\") = " +
                opsForSet.intersect("s1", "s2"));
        //随机数
        System.out.println("opsForSet.randomMember(\"s1\") = " +
                opsForSet.randomMember("s1"));
        System.out.println("opsForSet.randomMembers(\"s1\",2) = " +
                opsForSet.randomMembers("s1", 2));
        //随机 pop
        System.out.println("opsForSet.pop(\"s1\") = " +
                opsForSet.pop("s1"));
        System.out.println("opsForSet.pop(\"s2\",2) = " +
                opsForSet.pop("s2", 2));
    }

    /**
     * redisTemplate 中的 ZSet 的使用
     */
    @Test
    void redisTemplateZSet() {
        ZSetOperations<String, Object> opsForZSet = redisTemplate.opsForZSet();
        //zadd
        opsForZSet.add("z", "zs", 80);
        opsForZSet.add("z", "ls", 70);
        opsForZSet.add("z", "ww", 90);
        //zrange
        Set<Object> z = opsForZSet.range("z", 0, -1);
        System.out.println("z = " + z);
        //withscores
        Set<ZSetOperations.TypedTuple<Object>> zRangeWithScores = opsForZSet.rangeWithScores("z", 0, -1);
        for(ZSetOperations.TypedTuple<Object> tuple : zRangeWithScores){
            System.out.printf("zRangeWithScores %s:%s",tuple.getValue(),tuple.getScore());
            System.out.println();
        }
    }

}
