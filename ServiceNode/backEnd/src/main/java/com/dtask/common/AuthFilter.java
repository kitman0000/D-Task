package com.dtask.common;

import com.dtask.DTask.accountModule.bo.TokenBo;
import com.dtask.common.config.MyShiroRealm;
import com.dtask.common.util.CacheUtil;
import com.dtask.common.util.EncodeUtil;
import com.dtask.common.util.JsonUtil;
import com.dtask.common.util.RequestUtil;
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
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;

        String uri = httpServletRequest.getRequestURI();

        // 如果是登录页面，不拦截
        if(uri.equals( "/api/account/localLogin")){
            return true;
        }

        // 如果是插件页面，不拦截
        if (uri.startsWith("/pluginPage")){
            return true;
        }

        // 如果是登出，不拦截
        if(uri.equals( "/api/account/logout")){
            return true;
        }

        // 如果是远程登录，不拦截
        if(uri.equals( "/api/account/remoteLogin")){
            return true;
        }

        if(uri.equals( "/api/account/parentNodes")){
            return true;
        }

        // 解析Token
        String json = decodeTokenFromBase64(httpServletRequest,httpServletResponse);
        if (json == null){
            return false;
        }

        String username = null;
        String pwd = null;

        try {
            TokenBo tokenBo = (TokenBo) JsonUtil.jsonToObject(json,TokenBo.class);
            username = tokenBo.getUsername();

            // 从缓存中获取pwd
            pwd = userPwdMap.get(tokenBo.getUsername());

            if(pwd == null){
                throw new Exception("token过期或不存在");
            }

        }catch (Exception ex){
            // token过期或不存在
            RequestUtil.refuseRequest(httpServletResponse);
            return false;
        }

        // 尝试登录，校验token信息正确
        UsernamePasswordToken token = new UsernamePasswordToken(username,pwd);
        SecurityUtils.getSubject().login(token);

        RealmSecurityManager rsm = (RealmSecurityManager)SecurityUtils.getSecurityManager();
        MyShiroRealm myShiroRealm = (MyShiroRealm) rsm.getRealms().iterator().next();

        return true;


    }

    /**
     * Get token from headers and decode it from base64, header key is 'token'
     * @param request Request
     * @param response Response
     * @return Decoded content
     */
    private String decodeTokenFromBase64(HttpServletRequest request, HttpServletResponse response){
        String base64Token = request.getHeader("token");

        if(base64Token == null)
        {
            RequestUtil.refuseRequest(response);
            return null;
        }
        return EncodeUtil.decodeBase64(base64Token);
    }
}
