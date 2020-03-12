package com.dtask.DTask.accountModule.service.impl;

import com.dtask.DTask.accountModule.dao.AccountDao;
import com.dtask.DTask.accountModule.service.IAccount;
import com.dtask.DTask.userModule.bo.UserBo;
import com.dtask.common.UserCommon;
import com.dtask.common.util.CacheUtil;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhong on 2020-3-12.
 */

@Service
public class AccountImpl implements IAccount{
    @Autowired
    AccountDao accountDao;

    @Override
    public String login(String username, String password) {
        // 对密码二次加密
        password = UserCommon.encodePwd(password);

        // 添加用户认证信息
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);

        // 进行验证，这里可以捕获异常，然后返回对应信息
        try {
            SecurityUtils.getSubject().login(usernamePasswordToken);

            // 用户验证通过，生成token
            UserBo userBo = new UserBo();
            userBo.setUsername(username);
            userBo.setPwd(password);
            userBo.setUserID(accountDao.findUserIDByName(username).getUserID());
            String token = UserCommon.createToken(userBo);

            // 将token放入缓存
            CacheUtil cacheUtil = new CacheUtil();
            cacheUtil.write("token:"+username,token);
            cacheUtil.write("pwd:"+username,password);

            return token;
        }
        catch (DisabledAccountException ex)
        {
            return null;
            //return AccountResult.USER_LOGIN_BAN.toString();
        }
        catch (Exception ex)
        {
            // 记录尝试次数
            return null;
            //return AccountResult.USER_LOGIN_FAILED.toString();
        }
    }

    @Override
    public void sendChangePwdCaptcha(String userID) {

    }

    @Override
    public void changePwd(String username, String password, String captcha) {

    }
}
