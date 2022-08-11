package priv.noby.redis.configuration;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @Description
 * @Author noby
 * @Date 2022/7/28 17:26
 */
@Configuration
public class RedisConfiguration {
    /**
     * 修改默认的序列化为 json 序列化
     * <String, Object> 为自定义
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        //统一修改数据类型序列化器
//        template.setDefaultSerializer(RedisSerializer.json());

        //指定数据类型修改序列化器
        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(RedisSerializer.json());
        template.setHashKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(RedisSerializer.json());
        boolean b = true;
        int a = 1;
        return template;
    }

    /**
     * 修改 spring 声明式缓存的序列化方式
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        //修改默认的序列化器
        redisCacheConfiguration = redisCacheConfiguration.
                serializeKeysWith(
                        RedisSerializationContext.SerializationPair.
                                fromSerializer(RedisSerializer.string())).
                serializeValuesWith(
                        RedisSerializationContext.SerializationPair.
                                fromSerializer(RedisSerializer.json())
                );
        return redisCacheConfiguration;
    }

    /**
     * 分布式锁
     */
    @Bean
    public RedissonClient redissonClient(){
        Config config=new Config();
        config.useSingleServer()
                .setAddress("redis://192.168.122.128:6379")
                .setPassword("123");
        return Redisson.create(config);
    }
}
