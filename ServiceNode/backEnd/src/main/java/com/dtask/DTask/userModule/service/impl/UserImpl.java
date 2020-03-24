package com.dtask.DTask.userModule.service.impl;

import com.dtask.DTask.userModule.dao.UserDao;
import com.dtask.DTask.userModule.entity.UserSelectEntity;
import com.dtask.DTask.userModule.service.IUser;
import com.dtask.common.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhong on 2020-3-24.
 */
@Service
public class UserImpl implements IUser {
    @Autowired
    UserDao userDao;

    @Override
    public ResponseData getUserNumber(UserSelectEntity userSelectEntity) {
        int userNumber = userDao.getUserNumber(userSelectEntity);
        return new ResponseData(1,"查询成功",userNumber);
    }

    @Override
    public ResponseData getUserList(UserSelectEntity userSelectEntity) {
        return null;
    }
}
