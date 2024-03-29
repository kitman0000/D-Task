package com.dtask.DTask.accountModule.service;

import com.dtask.DTask.accountModule.entity.RemoteLoginEntity;
import com.dtask.common.ResponseData;

/**
 * Created by zhong on 2020-3-12.
 */
public interface IAccount {
    ResponseData login(String username, String password);

    void sendChangePwdCaptcha(String userID);

    boolean changePwd(String password, String oldPassword);

    void logout();

    void addAccount(String username, String pwd) throws Exception;

    void updatePwd(int id,String pwd);

    ResponseData deleteUser(int[] id);

    ResponseData getParentNodes();

    ResponseData remoteLogin(RemoteLoginEntity remoteLoginEntity);

    String mqRemoteLogin(String username,String pwd);

}
