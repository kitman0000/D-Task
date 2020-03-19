package com.dtask.DTask.accountModule.service.impl;

import com.dtask.DTask.accountModule.dao.AccountDao;
import com.dtask.DTask.accountModule.service.IAccount;
import com.dtask.DTask.accountModule.type.LoginType;
import com.dtask.DTask.accountModule.bo.UserBo;
import com.dtask.common.ResponseData;
import com.dtask.common.UserCommon;
import com.dtask.common.config.WebsiteConfig;
import com.dtask.common.util.CacheUtil;
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
    private AccountDao accountDao;

    @Autowired
    private CacheUtil cacheUtil;

    @Override
    public ResponseData login(String username, String password) {

        ResponseData responseData;

        // 对密码二次加密
        password = UserCommon.encodePwd(password);

        // 添加用户认证信息
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);

        Object loginFailedTimeObj = cacheUtil.read("loginFailedTime:"+username);

        int loginFailedTime;
        if(loginFailedTimeObj == null){
            loginFailedTime = 0;
        }else {
            loginFailedTime = Integer.parseInt(loginFailedTimeObj.toString());
        }

        if(loginFailedTime >= WebsiteConfig.getMaxLoginTime()){
            return new ResponseData(3,LoginType.LOGIN_LOCK.toString(),null);
        }

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
            cacheUtil.write("token:"+username,token);
            cacheUtil.write("pwd:"+username,password);

            return new ResponseData(1, LoginType.LOGIN_SUCCESS.toString(),token);
        }
        catch (DisabledAccountException ex)
        {
            return  new ResponseData(4,LoginType.LOGIN_BAN.toString(),null);
        }
        catch (Exception ex)
        {
            // 记录尝试次数
            cacheUtil.increase("loginFailedTime:"+username,300);
            return new ResponseData(2,LoginType.LOGIN_FAILED.toString(),null);
        }
    }

    @Override
    public void sendChangePwdCaptcha(String userID) {

    }

    @Override
    public void changePwd(String username, String password, String captcha) {

    }

    @Override
    public ResponseData logout(String token) {
        return null;
    }
}
