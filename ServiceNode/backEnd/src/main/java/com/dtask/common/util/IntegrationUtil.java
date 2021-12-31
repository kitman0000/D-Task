package com.dtask.common.util;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Kitman0000
 * Date:   2021-11-13
 * Intro:
 **/
@Component
public class IntegrationUtil {

    private static HashMap<String, Cache> cacheHashMap = new HashMap<>();
    private final int TIMEOUT_SECONDS = 60 * 30;

    public String get(String url, HashMap<String,String> params, boolean useCache){
        if (useCache){
            String cacheContent = getCache(getCacheKey(url,params));
            if (cacheContent != null){
                return cacheContent;
            }
        }

        String result = HttpUtil.httpURLRequest(HttpUtil.Method.GET,url,params);

        if (useCache){
            setCache(getCacheKey(url,params),null);
        }

        return result;
    }

    public String post(String url, Map<String,String> params, boolean useCache){
        if (useCache){
            String cacheContent = getCache(getCacheKey(url,params));
            if (cacheContent != null){
                return cacheContent;
            }
        }

        String result = HttpUtil.httpURLRequest(HttpUtil.Method.POST,url,params);

        if (useCache){
            setCache(getCacheKey(url,params),result);
        }

        return result;
    }

    public String put(String url, HashMap<String,String> params,boolean useCache){
        if (useCache){
            String cacheContent = getCache(getCacheKey(url,params));
            if (cacheContent != null){
                return cacheContent;
            }
        }

        String result = HttpUtil.httpURLRequest(HttpUtil.Method.PUT,url,params);


        if (useCache){
            setCache(getCacheKey(url,params),null);
        }

        return result;
    }

    public String delete(String url, HashMap<String,String> params,boolean useCache){
        if (useCache){
            String cacheContent = getCache(getCacheKey(url,params));
            if (cacheContent != null){
                return cacheContent;
            }
        }

        String result = HttpUtil.httpURLRequest(HttpUtil.Method.DELETE,url,params);


        if (useCache){
            setCache(getCacheKey(url,params),null);
        }

        return result;
    }

    /**
     * 获取缓存数据
     * @param key 缓存标识Key
     * @return
     */
    private String getCache(String key){
        Cache memoryCache = cacheHashMap.get(key);

        // 如果有缓存，且未超时，返回缓存数据
        if (memoryCache != null && memoryCache.getCreateTime().after(getEarliestCache())){
            return memoryCache.getPayload();
        }

        // 无缓存，返回null
        return null;
    }

    /**
     * 设置缓存数据
     * @param key 缓存标识Key
     * @param cacheContent 缓存内容
     */
    private void setCache(String key, String cacheContent){
        cacheHashMap.put(key,new Cache(cacheContent));
    }

    /**
     * 获取缓存标识Key
     * @param url url
     * @param params 参数
     * @return
     */
    private String getCacheKey(String url, Map<String,String> params){
        if (params == null || params.isEmpty()){
            return url;
        }

        StringBuilder paramsKeyStringBuilder = new StringBuilder("?");
        params.forEach((k,v)->{
            paramsKeyStringBuilder.append(k).append("=").append(v).append("&");
        });

        return url + paramsKeyStringBuilder.toString();
    }

    private Date getEarliestCache(){
        Calendar earliestCalendar = Calendar.getInstance();
        earliestCalendar.add(Calendar.SECOND,- TIMEOUT_SECONDS);

        return earliestCalendar.getTime();
    }

    public void clearOutDateCache(){
        cacheHashMap.keySet().removeIf(url -> getCache(url) == null);
    }

    @Data
    class Cache {
        public Cache() {
        }

        public Cache(String payload) {
            this.payload = payload;
            this.createTime = new Date();
        }

        private String payload;

        private Date createTime;
    }
}
