package com.dtask.DTask.accountModule.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2020-3-12.
 */
@RestController
public class AccountCl {

    @RequestMapping(value = "api/account/localLogin",method = RequestMethod.POST)
    public String localLogin(String username,String pwd)
    {
        return null;
    }

}
