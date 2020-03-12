package com.dtask.common.util;

import com.dtask.common.ICacheManager;

/**
 * Created by zhong on 2020-3-11.
 */
public class CacheUtil {
    private static ICacheManager cacheManager;

    /**
     * 启动时调用
     */
    public static void setCacheManager(){
        cacheManager = new RedisUtil();
    }

    public void write(String key,String value){
        cacheManager.write(key,value);
    }

    public void write(String key,int value){
        cacheManager.write(key,value);
    }

    public Object read(String key){
        return cacheManager.read(key);
    }
}
