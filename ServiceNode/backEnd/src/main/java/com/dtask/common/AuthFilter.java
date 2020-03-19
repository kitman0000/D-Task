package com.dtask.common;

import com.dtask.DTask.accountModule.bo.TokenBo;
import com.dtask.common.util.CacheUtil;
import com.dtask.common.util.EncodeUtil;
import com.dtask.common.util.JsonUtil;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用户权限拦截类
 * Created by zhong on 2019-12-17.
 */
public class AuthFilter extends BasicHttpAuthenticationFilter {

    @Autowired
    private CacheUtil cacheUtil;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;

        // 如果是登录页面，不拦截
        if(httpServletRequest.getRequestURI().equals( "/api/account/localLogin")){
            return true;
        }

        // 如果是登出，不拦截
        if(httpServletRequest.getRequestURI().equals( "/api/account/logout")){
            return true;
        }

        // 获取用户信息
        String base64Token = httpServletRequest.getHeader("token");
        String json = EncodeUtil.decodeBase64(base64Token);
        TokenBo tokenBo = (TokenBo) JsonUtil.jsonToObject(json,TokenBo.class);

        // 判断缓存中是否存在token
        Object tokenObj = cacheUtil.read("token:" + tokenBo.getUsername());

        if(tokenObj == null||!tokenBo.toString().equals(base64Token)){
            // token过期或不存在
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            try {
                // 向前端返回token过期
                PrintWriter printWriter = httpServletResponse.getWriter();
                printWriter.print("NO_TOKEN_FOUND");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        // 从缓存中获取pwd
        String pwd = cacheUtil.read("pwd:"+tokenBo.getUsername()).toString();

        // 尝试登录，校验token信息正确
        UsernamePasswordToken token = new UsernamePasswordToken(tokenBo.getUsername(),pwd);
        SecurityUtils.getSubject().login(token);

        return true;
    }
}
