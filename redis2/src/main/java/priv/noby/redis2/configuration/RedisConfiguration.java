package priv.noby.redis2.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class RedisConfiguration {
    /**
     * RedisCacheConfiguration的配置
     * 序列化方式
     * 默认Ttl
     */
    private RedisCacheConfiguration getRedisCacheConfiguration(Integer ttlSecond) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(ttlSecond)) //key的过期时间
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string())) //key的默认序列化方式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()) //value的默认序列化方式
                );
    }

    /**
     * 其他key的单独配置
     */
    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        redisCacheConfigurationMap.put("student:gender", this.getRedisCacheConfiguration(60 * 60));
        return redisCacheConfigurationMap;
    }

    /**
     * cacheManager的配置
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return new RedisCacheManager(
                RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
                this.getRedisCacheConfiguration(-1),//默认的key设置
                this.getRedisCacheConfigurationMap()//单独的key设置
        );
    }
}
