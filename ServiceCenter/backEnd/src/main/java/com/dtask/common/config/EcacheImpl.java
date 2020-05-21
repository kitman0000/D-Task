package com.dtask.common.config;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.io.Serializable;
import java.util.*;

/**
 * Created by zhong on 2019-12-6.
 */
public class EcacheImpl<K,V> implements Cache<K,V>{
    private net.sf.ehcache.Cache cache;

    public EcacheImpl() {
        cache = new net.sf.ehcache.Cache("ehcache",1024 * 1024,true,true,60*60*12,60*60*6);
        CacheManager cacheManager = new CacheManager();
        cacheManager.addCache(cache);
    }

    @Override
    public Object get(Object key) throws CacheException {
        Object obj = null;
        try {
            if (key != null) {
                    Element element = cache.get((Serializable) key);
                    if(element != null)
                        obj = element.getValue();
            }
        } catch (net.sf.ehcache.CacheException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public Object put(Object key, Object value) throws CacheException {
        Element element = new Element((Serializable) key,
                (Serializable) value);
        cache.put(element);

        return this.get(key);
    }

    @Override
    public Object remove(Object key) throws CacheException {
        Object obj = this.get(key);
        cache.remove(key);
        return obj;
    }

    @Override
    public void clear() throws CacheException {
        cache.removeAll();
    }

    @Override
    public int size() {
        return cache.getSize();
    }

    @Override
    public Set keys() {
        List<Object> keys = cache.getKeys();

        Set set = new HashSet();
        for(Object o : keys)
        {
            set.add(o);
        }

        return set;
    }

    @Override
    public Collection values() {
        Set keys = this.keys();

        List<Object> values = new ArrayList<Object>();
        for(Object o : values)
        {
            keys.add(o);
        }

        return keys;
    }
}
