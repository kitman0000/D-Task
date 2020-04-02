package com.dtask.common;

import com.dtask.common.util.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhong on 2020-3-31.
 */
@Service
public class CacheFactory {
    @Autowired
    private CacheUtil cacheUtil;

    public CacheUtil getCacheUtil() {
        return cacheUtil;
    }
}
