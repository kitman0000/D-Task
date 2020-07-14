package com.dtask.DTask.accountModule.service.impl;

import com.dtask.DTask.accountModule.dao.AccountDao;
import com.dtask.DTask.accountModule.entity.RemoteLoginEntity;
import com.dtask.DTask.accountModule.service.IAccount;
import com.dtask.DTask.accountModule.type.LoginType;
import com.dtask.DTask.accountModule.bo.AccountBo;
import com.dtask.common.AuthFilter;
import com.dtask.common.NodeCommon;
import com.dtask.common.ResponseData;
import com.dtask.common.UserCommon;
import com.dtask.common.config.RabbitSender;
import com.dtask.common.config.WebsiteConfig;
import com.dtask.common.util.CacheUtil;
import com.dtask.common.util.JsonUtil;
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

    @Autowired
    private RabbitSender rabbitSender;

    @Autowired
    private NodeCommon nodeCommon;

    @Override
    public ResponseData login(String username, String pwd) {

        // 对密码二次加密
        pwd = UserCommon.encodePwd(pwd);

        // 添加用户认证信息
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, pwd);

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
            AccountBo accountBo = new AccountBo();
            accountBo.setUsername(username);
            accountBo.setPwd(pwd);
            accountBo.setId(accountDao.findUserIDByName(username).getId());
            String token = UserCommon.createToken(accountBo);

            // 将token放入缓存
            cacheUtil.write("token:"+username,token);

            // 将密码放入HashMap缓存
            AuthFilter.userPwdMap.put(username,pwd);

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
    public boolean changePwd(String password, String oldPassword) {
        int userID = UserCommon.getUserBo().getUserID();

        if(!oldPassword.equals(accountDao.getUserPwd(userID))){
            return false;
        }

        accountDao.updateUserPwd(userID,password);
        return true;
    }

    @Override
    public ResponseData logout() {
        return null;
    }

    @Override
    public void addAccount(String username, String pwd) throws Exception {
        if(accountDao.checkUsernameExist(username) != 0){
            throw new Exception("account has exist");
        }

        pwd = UserCommon.encodePwd(pwd);
        accountDao.addAccount(username,pwd);
    }

    @Override
    public void updatePwd(int id, String pwd) {
        pwd =UserCommon.encodePwd(pwd);
        accountDao.updateUserPwd(id,pwd);
    }

    @Override
    public ResponseData deleteUser(int[] userID) {
        accountDao.deleteAccount(userID);
        return new ResponseData(1,"删除成功",null);
    }

    @Override
    public ResponseData getParentNodes() {
        int nodeID = nodeCommon.getNodeID();
        String res = rabbitSender.send("dtask.account.getParentNodes",String.valueOf(nodeID));

        return new ResponseData(1,"查询成功",res);
    }

    @Override
    public ResponseData remoteLogin(RemoteLoginEntity remoteLoginEntity) {
        // 给密码加密
        remoteLoginEntity.setPwd(UserCommon.encodePwd(remoteLoginEntity.getPwd()));
        remoteLoginEntity.setLoginNodeID(nodeCommon.getNodeID());

        String res =  rabbitSender.send("dtask.account.remoteLogin", JsonUtil.objectToJson(remoteLoginEntity));

        switch (res){
            case "NONE_ALLOWED_ACCOUNT":{
                return new ResponseData(2,"用户名不存在或不具有权限",null);
            }
            case "LOGIN_SUCCESS":{
                // 登录成功，为用户创建token

                // 远程用户默认账户信息
                final String RC_USERNAME = "RC";
                final String RC_PASSWORD = "1";
                final int RC_ID = 9999;

                // 生成远程账户Token
                AccountBo accountBo = new AccountBo();
                accountBo.setUsername(RC_USERNAME);
                accountBo.setPwd(RC_PASSWORD);
                accountBo.setId(RC_ID);
                String token = UserCommon.createToken(accountBo);

                // 将token放入缓存
                cacheUtil.write("token:"+RC_USERNAME,token);

                // 将密码放入HashMap缓存
                AuthFilter.userPwdMap.put(RC_USERNAME,RC_PASSWORD);

                return new ResponseData(1,"登录成功",token);
            }
            case "LOGIN_FAILED":{
                return new ResponseData(3,"密码错误",null);
            }
            default:{
                return new ResponseData(4,res,null);
            }
        }
    }

    /**
     * 处理消息队列发送至本节点的登录请求，即本节点用户申请登录至其他节点
     */
    @Override
    public String mqRemoteLogin(String username, String pwd) {
        String pwdInDb = accountDao.getRemoteUserPwd(username);
        if(pwdInDb == null || pwdInDb.equals("")){
            // 用户名不存在或不具有权限
            return "NONE_ALLOWED_ACCOUNT";
        }
        if(pwdInDb.equals(pwd)){
            return "LOGIN_SUCCESS";
        }
        return "LOGIN_FAILED";
    }
}
