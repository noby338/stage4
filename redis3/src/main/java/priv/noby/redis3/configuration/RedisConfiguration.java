package priv.noby.redis3.configuration;

import org.springframework.context.annotation.Configuration;


@Configuration
public class RedisConfiguration {
//    /**
//     * 修改默认的序列化为 json 序列化
//     * <String, Object> 为自定义
//     */
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, Object> template = new RedisTemplate();
//        template.setConnectionFactory(redisConnectionFactory);
//        //统一修改数据类型序列化器
////        template.setDefaultSerializer(RedisSerializer.json());
//
//        //指定数据类型修改序列化器
//        template.setKeySerializer(RedisSerializer.string());
//        template.setValueSerializer(RedisSerializer.json());
//        template.setHashKeySerializer(RedisSerializer.string());
//        template.setHashValueSerializer(RedisSerializer.json());
//        return template;
//    }

//    /**
//     * 修改 spring 声明式缓存的序列化方式
//     */
//    @Bean
//    public RedisCacheConfiguration redisCacheConfiguration() {
//        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
//        //修改默认的序列化器
//        redisCacheConfiguration = redisCacheConfiguration.
//                serializeKeysWith(
//                        RedisSerializationContext.SerializationPair.
//                                fromSerializer(RedisSerializer.string())).
//                serializeValuesWith(
//                        RedisSerializationContext.SerializationPair.
//                                fromSerializer(RedisSerializer.json())
//                );
//        return redisCacheConfiguration;
//    }
}
