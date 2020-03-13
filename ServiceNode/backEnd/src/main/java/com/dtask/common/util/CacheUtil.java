package com.dtask.common.util;

import com.dtask.common.ICacheManager;

/**
 * Created by zhong on 2020-3-11.
 */
public class CacheUtil implements ICacheManager{
    private static ICacheManager cacheManager;

    /**
     * 启动时调用
     */
    public static void setCacheManager(){
        cacheManager = new RedisUtil();
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
