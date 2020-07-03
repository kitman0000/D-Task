package com.dtask.common;

import com.dtask.DTask.accountModule.bo.TokenBo;
import com.dtask.DTask.accountModule.bo.AccountBo;
import com.dtask.common.util.EncodeUtil;
import com.dtask.common.util.JsonUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户操作common类
 * Created by zhong on 2019-12-23.
 */

public class UserCommon {

    // 密钥
    private static String SECRETKEY = "FJGDFOIJ";

    /**
     * 获取当前用户Token
     * @return Token对象
     */
    public static TokenBo getUserBo(){
        HttpServletRequest request = null;

        try {
            // 获取当前请求
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            request = requestAttributes.getRequest();
        } catch (Exception NullPointerException){
            // 没有获取到请求，说明不是来自外部系统调用
            // 可能是日志功能试图获取用户ID
            // 返回999表示是系统操作，而不是用户操作
            TokenBo tokenBo = new TokenBo();
            tokenBo.setUserID(999);
            return tokenBo;
        }

        // 在Header中获取token
        String base64Token = request.getHeader("token");

        // 将token反序列化为token对象并返回
        String json = EncodeUtil.decodeBase64(base64Token);
        return (TokenBo) JsonUtil.jsonToObject(json,TokenBo.class);

    }

    /**
     * 生成用户token
     * @param accountBo 用户信息对象
     * @return token字符串
     */
    public static String createToken(AccountBo accountBo){
        // 生成token对象
        TokenBo tokenBo = new TokenBo();
        tokenBo.setUsername(accountBo.getUsername());
        tokenBo.setUserID(accountBo.getId());
        tokenBo.setCreateTime(System.currentTimeMillis());

        // 序列化token对象并返回
        return EncodeUtil.encodeBase64(JsonUtil.objectToJson(tokenBo));
    }

    /**
     * 加密密码
     * @param pwd 原始密码
     * @return 加密后的密码
     */
    public static String encodePwd(String pwd){
        return EncodeUtil.encodeHmac256(pwd,SECRETKEY);
    }

}
