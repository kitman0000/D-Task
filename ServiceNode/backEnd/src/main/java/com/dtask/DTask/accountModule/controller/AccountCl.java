package com.dtask.DTask.accountModule.controller;

import com.dtask.DTask.accountModule.service.IAccount;
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

    @RequestMapping(value = "/api/account/localLogin",method = RequestMethod.POST)
    public ResponseData localLogin(String username,String pwd)
    {
        return account.login(username,pwd);
    }

}
