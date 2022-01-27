package com.commos.redis.util;

import com.util.common.serializer.SerializerWrapper;
import com.util.common.stringutil.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * @author hzw
 * @version 4.0.0
 * @date 2019-09-18
 */
@Slf4j
public class RedisUtils {

    /**
     * Redis 连接
     */
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 根据缓存Key前缀删除缓存中的数据
     *
     * @param pattern 缓存的前缀
     * @return 删除成功的数量
     */
    public Long deleteByPattern(String pattern) {
        if (StringUtils.isBlank(pattern)) {
            return 0L;
        }

        final String keyPattern = StringUtils.endsWith(pattern, "*") ? pattern : pattern + "*";
        return redisTemplate.execute((RedisCallback<Long>) connection -> {
            connection.openPipeline();
            try {
                Set<byte[]> keys = connection.keys(keyPattern.getBytes());
                if (CollectionUtils.isEmpty(keys)) {
                    return 0L;
                }
                return connection.del(keys.toArray(new byte[keys.size()][]));
            } finally {
                connection.closePipeline();
            }
        });
    }

    /**
     * 从Redis中获取数据, 可能会出现数据格式不兼容
     *
     * @param key           缓存Key
     * @param expirySeconds 过期时间
     * @param supplier      通过其它方式产生数据
     * @param <V>           返回的数据类型
     * @return 缓存中的数据
     */
    @SuppressWarnings("unchecked")
    public <V> V mutexGetAndSetIfNeeded(String key, Integer expirySeconds, Supplier<? extends V> supplier) {
        final AtomicInteger loopCount = new AtomicInteger(0), sleepTime = new AtomicInteger(50);
        final long maxTimeout = TimeUnit.SECONDS.toMillis(2);
        final int retryTimes = 6;
        V finalValue = null;

        // 防止 OutOfStack，限制循环次数
        while (Objects.isNull(finalValue) && loopCount.getAndIncrement() < retryTimes && sleepTime.get() < maxTimeout) {
            // 从缓存中查询数据
            Object serializedValue = redisTemplate.opsForValue().get(key);
            if (Objects.nonNull(serializedValue)) {
                if (serializedValue instanceof SerializerWrapper) {
                    SerializerWrapper<V> wrapper = (SerializerWrapper<V>) serializedValue;
                    return wrapper.getData();
                }
                return (V) serializedValue;
            }

            // 不有其它方式取数据
            if (null == supplier) {
                return null;
            }
            // 对查数据库的逻辑加锁，保证只有一个请求进行DB查询，其它连接处理于等待状态
            String lockKey = key + "_mutex", lockVal = String.valueOf(System.currentTimeMillis());
            Boolean locked = redisTemplate.opsForValue().setIfAbsent(lockKey, lockVal, 30, TimeUnit.SECONDS);
            if (Optional.ofNullable(locked).orElse(false)) {
                try {
                    // 通过其它方式查询数据
                    finalValue = supplier.get();
                    // 防止因为网络或其它线程中断的原因导致没有获取到数据，需要快速过期
                    final long defaultExpiryTime = Objects.isNull(expirySeconds) ? TimeUnit.DAYS.toSeconds(1) : expirySeconds;
                    final long expiryTime = (null == finalValue) ? Math.min(30, defaultExpiryTime) : defaultExpiryTime;
//                    log.info("将Key:({})的数据:({})放入缓存, 过期时间: {}秒", key, finalValue, expiryTime);

                    redisTemplate.opsForValue().set(key, SerializerWrapper.builder(finalValue), expiryTime, TimeUnit.SECONDS);
                } finally {
                    redisTemplate.delete(lockKey);
                }
            } else {
                try {
                    TimeUnit.MILLISECONDS.sleep(sleepTime.getAndAdd(2 * sleepTime.get()));
                } catch (InterruptedException ex) {
                    log.warn("同步获取缓存失败", ex);
                }
            }
        }
        return finalValue;
    }
}