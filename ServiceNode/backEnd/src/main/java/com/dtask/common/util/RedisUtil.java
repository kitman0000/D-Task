package com.dtask.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import com.dtask.common.ICacheManager;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhong on 2020-3-11.
 */
@Component
public class RedisUtil implements ICacheManager {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public void write(String key, String value) {
        redisTemplate.opsForValue().set(key,value);
    }

    @Override
    public void write(String key, int value) {
        redisTemplate.opsForValue().set(key,String.valueOf(value));
    }

    @Override
    public void write(String key, String value, long ttl) {
        write(key,value);
        redisTemplate.expire(key,ttl, TimeUnit.SECONDS);
    }

    @Override
    public void write(String key, int value, long ttl) {
        write(key,value);
        redisTemplate.expire(key,ttl, TimeUnit.SECONDS);
    }

    @Override
    public Object read(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void increase(String key) {
        redisTemplate.opsForValue().increment(key);
    }

    @Override
    public void increase(String key, long ttl) {
        increase(key);
        redisTemplate.expire(key,ttl,TimeUnit.SECONDS);
    }
}
