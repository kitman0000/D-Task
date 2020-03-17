package com.dtask.common.util;

import com.dtask.common.ICacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by zhong on 2020-3-11.
 */
@Component
public class CacheUtil implements ICacheManager{
    private ICacheManager cacheManager;

    @Resource
    RedisUtil redisUtil;
    /**
     * 启动时调用
     */
    public void setCacheManager(){
        cacheManager = redisUtil;
    }

    @Override
    public void write(String key,String value){
        cacheManager.write(key,value);
    }

    @Override
    public void write(String key,int value){
        cacheManager.write(key,value);
    }

    @Override
    public Object read(String key){
        return cacheManager.read(key);
    }

    @Override
    public void write(String key, String value, long ttl) {
        cacheManager.write(key,value,ttl);
    }

    @Override
    public void write(String key, int value, long ttl) {
        cacheManager.write(key,value,ttl);
    }

    @Override
    public void increase(String key) {
        cacheManager.increase(key);
    }

    @Override
    public void increase(String key, long ttl) {
        cacheManager.increase(key,ttl);
    }
}
