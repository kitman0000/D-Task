package com.dtask.common;

/**
 * Created by zhong on 2020-3-11.
 */
public interface ICacheManager {
    void write(String key,String value);

    void write(String key, String value, long ttl);

    void write(String key,int value);

    void write(String key, int value, long ttl);

    void increase(String key);

    void increase(String key, long ttl);

    Object read(String key);

}
