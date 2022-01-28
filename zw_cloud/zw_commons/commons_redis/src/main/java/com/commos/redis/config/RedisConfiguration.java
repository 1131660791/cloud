package com.commos.redis.config;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.commos.redis.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author hzw
 * @version 4.0.0
 * @date 2019-09-18
 *
 * 为什么要重写Redis序列化方式，改为Json呢？
 * 因为RedisTemplate默认使用的是JdkSerializationRedisSerializer，会出现2个问题：
 * 1: 被序列化的对象必须实现Serializable接口
 * 2: 被序列化会出现乱码,导致value值可读性差.
 *
 */
@Slf4j
@Configuration
public class RedisConfiguration extends CachingConfigurerSupport {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        ParserConfig.getGlobalInstance().addAccept("com.");

        template.setConnectionFactory(connectionFactory);
        // 设置键（key）的序列化采用StringRedisSerializer。
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // 设置值（value）的序列化采用FastJsonRedisSerializer。
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisUtils redisUtils() {
        return new RedisUtils();
    }

}