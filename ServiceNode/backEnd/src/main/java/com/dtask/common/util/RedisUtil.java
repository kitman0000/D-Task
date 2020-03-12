package com.dtask.common.util;

import com.dtask.common.ICacheManager;

/**
 * Created by zhong on 2020-3-11.
 */
public class RedisUtil implements ICacheManager {
    @Override
    public void write(String key, String value) {

    }

    @Override
    public void write(String key, int value) {

    }

    @Override
    public String read(String key) {
        return null;
    }
}
