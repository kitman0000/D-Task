package com.dtask.DTask.accountModule.service;

/**
 * Created by zhong on 2020-3-12.
 */
public interface IAccount {
    String login(String username, String password);

    void sendChangePwdCaptcha(String userID);

    void changePwd(String username,String password,String captcha);
}
