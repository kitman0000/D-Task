package com.dtask.common.util;

import com.dtask.common.ICacheManager;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Created by zhong on 2020-3-11.
 */
public class EcacheUtil implements ICacheManager {

    private CacheManager cacheManager = CacheManager.create();
    private Cache cache;

    public EcacheUtil() {
        if(cache == null){
            cache = new Cache("ehcache",1024 * 1024,true,true,0,0);
            cacheManager.addCache(cache);
        }
    }

    @Override
    public void write(String key, String value) {
        cache.put(new Element(key,value));
    }

    @Override
    public void write(String key, int value) {
        cache.put(new Element(key,value));
    }

    @Override
    public void write(String key, String value, long ttl) {
        cache.put(new Element(key,value,false,(int)ttl,(int)ttl));
    }

    @Override
    public void write(String key, int value, long ttl) {
        cache.put(new Element(key,value,false,(int)ttl,(int)ttl));
    }

    @Override
    public void increase(String key) {
        Object obj = read(key);
        if(obj != null){
            int temp = Integer.parseInt(obj.toString());
            temp++;
            write(key,temp);
        }else {
            write(key,1);
        }
    }

    @Override
    public void increase(String key, long ttl) {
        Object obj = read(key);
        if(obj != null){
            int temp = Integer.parseInt(obj.toString());
            temp++;
            write(key,temp,ttl);
        }else {
            write(key,1,ttl);
        }
    }

    @Override
    public Object read(String key) {
        Element obj = cache.get(key);
        if(obj != null) {
            return obj.getObjectValue();
        }else {
            return null;
        }
    }

}
