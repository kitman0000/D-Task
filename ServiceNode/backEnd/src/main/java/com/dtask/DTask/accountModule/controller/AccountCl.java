package com.dtask.DTask.accountModule.controller;

import com.dtask.DTask.accountModule.service.IAccount;
import com.dtask.DTask.accountModule.type.LoginType;
import com.dtask.common.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2020-3-12.
 */
@RestController
public class AccountCl {
    @Autowired
    IAccount account;

    @RequestMapping(value = "api/account/localLogin",method = RequestMethod.POST)
    public ResponseData localLogin(String username,String pwd)
    {
        String result = account.login(username,pwd);

        ResponseData responseData;

        switch (result){
            case "LOGIN_FAILED": responseData = new ResponseData(2,"用户名或密码错误");break;
            case "ACCOUNT_LOCK": responseData = new ResponseData(3,"账号被锁定");break;
            case "ACCOUNT_BAN": responseData = new ResponseData(4,"账号被禁止");break;
            default:
                responseData = new ResponseData(1,"登录成功");
                String[] token = {result};
                responseData.setData(token);
        }

        return responseData;
    }

}
