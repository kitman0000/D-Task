package com.dtask.common.config;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * Created by zhong on 2019-12-6.
 */
public class EhCacheManager  implements CacheManager {
    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return new EcacheImpl<K, V>();
    }
}
