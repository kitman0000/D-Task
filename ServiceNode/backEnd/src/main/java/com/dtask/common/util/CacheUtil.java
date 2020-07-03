package com.dtask.common.util;

import com.dtask.DTask.settingModule.dao.SettingDao;
import com.dtask.DTask.settingModule.service.ISetting;
import com.dtask.common.ICacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by zhong on 2020-3-11.
 */
@Service
@Component
public class CacheUtil implements ICacheManager{

    private ICacheManager cacheManager;

    // 缓存的key前缀，表示此缓存项为DTask使用
    private final String APPLICATION_PREFIX = "DTask:";

    // 网站设置，用于读取数据库内的缓存方法设置
    @Autowired
    private SettingDao settingDao;

    private RedisUtil redisUtil;

    // 1、先创建Redis对象，保存
    @Autowired
    public CacheUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    // 2、通过setCacheMode方法，设置缓存
    // 如果为Redis则使用构造函数中的Redis操作对象
    // 如果为Ehcache则创建Ehcache操作对象
    public void setCacheMode(int cacheMode){
        if(cacheMode == 1){
            // 缓存模式是1则使用Redis
            this.cacheManager = redisUtil;
        }else {
            // 缓存模式是2则使用ECache
            this.cacheManager = new EcacheUtil();
        }
    }

    @Override
    public void write(String key,String value){
        cacheManager.write(APPLICATION_PREFIX + key,value);
    }

    @Override
    public void write(String key,int value){
        cacheManager.write(APPLICATION_PREFIX + key,value);
    }

    @Override
    public Object read(String key){
        return cacheManager.read(APPLICATION_PREFIX + key);
    }

    @Override
    public void write(String key, String value, long ttl) {
        cacheManager.write(APPLICATION_PREFIX + key,value,ttl);
    }

    @Override
    public void write(String key, int value, long ttl) {
        cacheManager.write(APPLICATION_PREFIX + key,value,ttl);
    }

    @Override
    public void increase(String key) {
        cacheManager.increase(APPLICATION_PREFIX + key);
    }

    @Override
    public void increase(String key, long ttl) {
        cacheManager.increase(APPLICATION_PREFIX + key,ttl);
    }
}
