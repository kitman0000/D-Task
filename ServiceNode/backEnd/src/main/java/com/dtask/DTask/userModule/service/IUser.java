package com.dtask.DTask.userModule.service;

import com.dtask.DTask.userModule.entity.UserAddEntity;
import com.dtask.DTask.userModule.entity.UserSelectEntity;
import com.dtask.common.ResponseData;

/**
 * Created by zhong on 2020-3-24.
 */
public interface IUser {
    ResponseData getUserNumber(UserSelectEntity userSelectEntity);

    ResponseData getUserList(UserSelectEntity userSelectEntity, int page);

    ResponseData getUserDetail(int userID);

    ResponseData addUser(UserAddEntity userAddEntity);

    ResponseData editUser(UserAddEntity userAddEntity);

    void deleteUser(int[] userID);

}
