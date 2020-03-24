package com.dtask.DTask.userModule.controller;

import com.dtask.DTask.userModule.entity.UserSelectEntity;
import com.dtask.DTask.userModule.service.IUser;
import com.dtask.common.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2020-3-24.
 */

@RestController
public class UserCl {

    @Autowired
    IUser user;

    @RequestMapping(value = "/api/user/userNumber",method = RequestMethod.GET)
    public ResponseData getUserNumber(UserSelectEntity userSelectEntity){
        return user.getUserNumber(userSelectEntity);
    }

    @RequestMapping(value = "/api/user/userList",method = RequestMethod.GET)
    public ResponseData getUserList(UserSelectEntity userSelectEntity){
        return user.getUserList(userSelectEntity);
    }

}
