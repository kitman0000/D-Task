package com.dtask.common.util;

import com.dtask.common.ICacheManager;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Created by zhong on 2020-3-11.
 */
public class EcacheUtil implements ICacheManager{

    private CacheManager cacheManager = CacheManager.create();
    private Cache cache = cacheManager.getCache("ehcache");

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
        int temp = Integer.parseInt(read(key).toString());
        temp++;
        write(key,temp);
    }

    @Override
    public void increase(String key, long ttl) {
        int temp = Integer.parseInt(read(key).toString());
        temp++;
        write(key,temp,ttl);
    }

    @Override
    public Object read(String key) {
        return cache.get(key).getObjectValue();
    }

}
