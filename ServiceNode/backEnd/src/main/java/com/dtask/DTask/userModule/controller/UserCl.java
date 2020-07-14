package com.dtask.DTask.userModule.controller;

import com.dtask.DTask.accountModule.service.IAccount;
import com.dtask.DTask.userModule.entity.UserAddEntity;
import com.dtask.DTask.userModule.entity.UserSelectEntity;
import com.dtask.DTask.userModule.service.IUser;
import com.dtask.common.ResponseData;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2020-3-24.
 */
@EnableScheduling
@RestController
public class UserCl {

    @Autowired
    IUser user;

    @Autowired
    IAccount account;

    @Transactional
    @RequestMapping(value = "/api/user/changeUserDetail", method = RequestMethod.POST)
    public ResponseData changeUserDetail(UserAddEntity userAddEntity, String oldPassword) {
        // 是否需要修改密码
        if (oldPassword != null) {
            // 修改密码成功
            if (!account.changePwd(userAddEntity.getPwd(), oldPassword)) {
                return new ResponseData(2, "密码错误", null);
            }
        }
        return user.changeUserDetail(userAddEntity);
    }

    @RequiresPermissions("user:userList:get")
    @RequestMapping(value = "/api/user/userNumber", method = RequestMethod.GET)
    public ResponseData getUserNumber(UserSelectEntity userSelectEntity) {
        return user.getUserNumber(userSelectEntity);
    }

    @RequiresPermissions("user:userList:get")
    @RequestMapping(value = "/api/user/userList", method = RequestMethod.GET)
    public ResponseData getUserList(UserSelectEntity userSelectEntity, int page) {
        return user.getUserList(userSelectEntity, page);
    }

    @RequiresPermissions("user:userList:get")
    @RequestMapping(value = "/api/user/userDetail", method = RequestMethod.GET)
    public ResponseData getUserDetail(int userID) {
        return user.getUserDetail(userID);
    }

    @RequiresPermissions("user:userList:add")
    @Transactional
    @RequestMapping(value = "/api/user/user", method = RequestMethod.POST)
    public ResponseData addUser(UserAddEntity userAddEntity) {
        try {
            account.addAccount(userAddEntity.getUsername(), userAddEntity.getPwd());
        } catch (Exception e) {
            return new ResponseData(2, "账号已经存在", null);
        }
        return user.addUser(userAddEntity);
    }

    @RequiresPermissions("user:userList:edit")
    @Transactional
    @RequestMapping(value = "/api/user/user", method = RequestMethod.PUT)
    public ResponseData editUser(UserAddEntity userAddEntity) {
        if (!userAddEntity.getPwd().equals("")) {
            account.updatePwd(userAddEntity.getId(), userAddEntity.getPwd());
        }
        return user.editUser(userAddEntity);
    }

    @RequiresPermissions("user:userList:delete")
    @Transactional
    @RequestMapping(value = "/api/user/user", method = RequestMethod.DELETE)
    public ResponseData deleteUser(int[] userID) {
        user.deleteUser(userID);
        return account.deleteUser(userID);
    }

    @RequestMapping(value = "/api/user/remoteUser",method = RequestMethod.GET)
    public ResponseData getRemoteUser(int nodeID) {
        return user.getRemoteUser(nodeID);
    }

    @Scheduled(cron = "0 0/30 * * * ? ")
    //@Scheduled(cron = "0/30 * * * * ? ") // 开发期间，30秒一次
    public void syncUserInfo() {
        user.syncUserInfo();
    }


}
