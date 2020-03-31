package com.dtask.DTask.accountModule.service;

import com.dtask.common.ResponseData;

/**
 * Created by zhong on 2020-3-12.
 */
public interface IAccount {
    ResponseData login(String username, String password);

    void sendChangePwdCaptcha(String userID);

    void changePwd(String username,String password,String captcha);

    ResponseData logout();

    void addAccount(String username, String pwd) throws Exception;

    void updatePwd(int id,String pwd);
}
