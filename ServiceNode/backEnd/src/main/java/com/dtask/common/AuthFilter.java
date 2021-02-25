package com.dtask.common;

import com.dtask.DTask.accountModule.bo.TokenBo;
import com.dtask.common.config.MyShiroRealm;
import com.dtask.common.util.CacheUtil;
import com.dtask.common.util.EncodeUtil;
import com.dtask.common.util.JsonUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * 用户权限拦截类
 * Created by zhong on 2019-12-17.
 */
public class AuthFilter extends BasicHttpAuthenticationFilter {

    public static HashMap<String,String> userPwdMap = new HashMap<>();

    Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;

        // 如果是登录页面，不拦截
        if(httpServletRequest.getRequestURI().equals( "/api/account/localLogin")){
            return true;
        }

        // 如果是插件页面，不拦截
        if (httpServletRequest.getRequestURI().startsWith("/pluginPage")){
            return true;
        }

        // 如果是登出，不拦截
        if(httpServletRequest.getRequestURI().equals( "/api/account/logout")){
            return true;
        }

        // 如果是远程登录，不拦截
        if(httpServletRequest.getRequestURI().equals( "/api/account/remoteLogin")){
            return true;
        }

        if(httpServletRequest.getRequestURI().equals( "/api/account/parentNodes")){
            return true;
        }

        // 获取用户信息
        String base64Token = httpServletRequest.getHeader("token");

        if(base64Token == null)
        {
            // token不在header里
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            try {
                // 向前端返回token过期
                PrintWriter printWriter = httpServletResponse.getWriter();
                printWriter.print("NO_TOKEN_IN_HEADER");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        String json = EncodeUtil.decodeBase64(base64Token);
        TokenBo tokenBo = (TokenBo) JsonUtil.jsonToObject(json,TokenBo.class);

        // 从缓存中获取pwd
        String pwd = userPwdMap.get(tokenBo.getUsername());

        if(pwd == null){
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

        // 尝试登录，校验token信息正确
        UsernamePasswordToken token = new UsernamePasswordToken(tokenBo.getUsername(),pwd);
        SecurityUtils.getSubject().login(token);

        RealmSecurityManager rsm = (RealmSecurityManager)SecurityUtils.getSecurityManager();
        MyShiroRealm myShiroRealm = (MyShiroRealm) rsm.getRealms().iterator().next();

        return true;
    }
}
