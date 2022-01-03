package com.dtask.DTask.externalModule.controller;

import com.dtask.DTask.externalModule.service.IExternalApplication;
import com.dtask.common.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2022-1-1.
 */

@RestController
@RequestMapping(value = "/externalApi/application")
public class ExternalApplicationCl {

    @Autowired
    private IExternalApplication externalApplication;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseData login(String application,String password){
        String token = externalApplication.login(application,password);
        if (token == null){
            return new ResponseData(2,"Login failed",null);
        }
        return new ResponseData(1,token,null);
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public ResponseData test(){
        return new ResponseData(1,"Test success!",null);
    }

}
