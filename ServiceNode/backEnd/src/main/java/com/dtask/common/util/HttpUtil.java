package com.dtask.common.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Author: Kitman0000
 * Date:   2021-11-13
 * Intro:
 **/
public class HttpUtil {

    public enum Method{
        POST,GET,PUT,DELETE
    }

    /**
     * 无Header的URL传参POST
     */
    public static String httpURLRequest(Method method, final String url, Map<String, String> params) {
        return httpURLRequest(method,url, params, null);
    }

    /***
     * 使用URL传参的POST
     */
    public static String httpURLRequest(Method method, String url, final Map<String, String> params, final Map<String, String> headers) {

        final CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

        if (params != null){
            StringBuilder urlStringBuilder = new StringBuilder();

            params.forEach((k,v)->{
                String encodedValue = null;
                try {
                    encodedValue = URLEncoder.encode(v,"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                urlStringBuilder.append(k).append("=").append(encodedValue).append("&");
            });

            url += "?" + urlStringBuilder.substring(0,urlStringBuilder.length() -1);

        }

        HttpRequestBase httpRequest = getRequest(method, url);

        // 设置header
        httpRequest.setHeader("Content-type", "application/json");
        if (headers != null && headers.size() > 0) {
            for (final Map.Entry<String, String> entry : headers.entrySet()) {
                httpRequest.setHeader(entry.getKey(), entry.getValue());
            }
        }
        String content = null;
        CloseableHttpResponse httpResponse = null;
        try {
            // 响应信息
            httpResponse = closeableHttpClient.execute(httpRequest);
            final HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, "UTF-8");
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpResponse.close();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        try { // 关闭连接、释放资源
            closeableHttpClient.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    private static HttpRequestBase getRequest(Method method, String url){
        switch (method){
            case POST:
                return new HttpPost(url);
            case PUT:
                return new HttpPut(url);
            case DELETE:
                return new HttpDelete(url);
            case GET:
                return new HttpGet(url);
        }

        throw new RuntimeException("Invalid Http Method");
    }
}
