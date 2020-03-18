package com.dtask.common.util;

import com.dtask.common.ICacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhong on 2020-3-11.
 */
@Component
public class CacheUtil implements ICacheManager{

    private ICacheManager cacheManager;

    @Autowired
    public CacheUtil(RedisUtil redisUtil) {
        // 获取缓存方法
        int cacheMode = 1;

        if(cacheMode == 1){ // 缓存模式是1则使用Redis
            this.cacheManager = redisUtil;
        }else { // 缓存模式是2则使用ECache
            this.cacheManager = new EcacheUtil();
        }
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
