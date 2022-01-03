package com.dtask.common;

import com.dtask.DTask.externalModule.bo.ExternalUrlBo;
import com.dtask.common.entity.ApplicationToken;
import com.dtask.common.util.AESUtil;
import com.dtask.common.util.EncodeUtil;
import com.dtask.common.util.JsonUtil;
import com.dtask.common.util.RequestUtil;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

/**
 * Created by zhong on 2021-12-28.
 */
public class ApplicationFilter extends BasicHttpAuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        String uri = httpServletRequest.getRequestURI();
        String method = httpServletRequest.getMethod().toLowerCase();

        // Don't filter if it is the login interface
        if(uri.equals( "/externalApi/application/login")){
            return true;
        }

        String tokenJson = decodeTokenFromBase64AndAes(httpServletRequest,httpServletResponse);
        if (tokenJson == null){
            RequestUtil.refuseRequest(httpServletResponse);
            return false;
        }

        ApplicationToken applicationToken = (ApplicationToken) JsonUtil.jsonToObject(tokenJson,ApplicationToken.class);

        if (isTokenExpired(applicationToken)){
            return false;
        }

        for (ExternalUrlBo externalUrlBo : applicationToken.getApiList()) {
            if (externalUrlBo.getUrl().equals(uri) && externalUrlBo.getMethod().toLowerCase().equals(method)){
                return true;
            }
        }

        return false;
    }

    /**
     * Get token from headers and decode it from base64 and aes, header key is 'token'
     * @param request Request
     * @param response Response
     * @return Decoded content
     */
    private String decodeTokenFromBase64AndAes(HttpServletRequest request, HttpServletResponse response){
        String base64Token = request.getHeader("token");

        if(base64Token == null)
        {
            RequestUtil.refuseRequest(response);
            return null;
        }
        byte[] bytes = EncodeUtil.decodeBase64ToByte(base64Token);

        // todo: Remove the hard code here
        try {
            return new String(AESUtil.decryptAES(bytes,""),"utf-8");
        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace();
            return null;
        }
    }

    /**
     * If the token is expired.
     * @param token token
     * @return Is expired
     */
    private boolean isTokenExpired(ApplicationToken token){
        // No token's createTime must be earlier than this
        Calendar latestCalender = Calendar.getInstance();
        latestCalender.add(Calendar.HOUR,-2);

        return token.getTimestamp().before(latestCalender.getTime());
    }
}
