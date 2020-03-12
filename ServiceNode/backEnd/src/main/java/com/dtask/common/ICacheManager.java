package com.dtask.common;

/**
 * Created by zhong on 2020-3-11.
 */
public interface ICacheManager {
    void write(String key,String value);

    void write(String key,int value);

    String read(String key);

}
