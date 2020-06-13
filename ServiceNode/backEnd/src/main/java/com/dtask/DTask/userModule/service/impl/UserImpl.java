package com.dtask.DTask.userModule.service.impl;

import com.dtask.DTask.userModule.bo.SyncUserInfoBo;
import com.dtask.DTask.userModule.bo.UserDetailBo;
import com.dtask.DTask.userModule.bo.UserListBo;
import com.dtask.DTask.userModule.dao.UserDao;
import com.dtask.DTask.userModule.entity.UserAddEntity;
import com.dtask.DTask.userModule.entity.UserSelectEntity;
import com.dtask.DTask.userModule.service.IUser;
import com.dtask.common.ResponseData;
import com.dtask.common.UserCommon;
import com.dtask.common.config.RabbitSender;
import com.dtask.common.util.CacheUtil;
import com.dtask.common.util.DateUtil;
import com.dtask.common.util.JsonUtil;
import com.dtask.common.util.PageDivideUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhong on 2020-3-24.
 */
@Service
public class UserImpl implements IUser {
    private static final int ROWS_ONE_PAGE = 10;
    @Autowired
    private UserDao userDao;

    @Autowired
    private CacheUtil cacheUtil;

    @Autowired
    private RabbitSender rabbitSender;

    @Override
    public ResponseData getUserNumber(UserSelectEntity userSelectEntity) {
        int userNumber = userDao.getUserNumber(userSelectEntity);
        int pageNumber = PageDivideUtil.getCountOfPages(userNumber,ROWS_ONE_PAGE);

        return new ResponseData(1,"查询成功",pageNumber);
    }

    @Override
    public ResponseData getUserList(UserSelectEntity userSelectEntity, int page) {
        int startRow = (page -1) * ROWS_ONE_PAGE;
        List<UserListBo> userListBoList = userDao.getUserList(userSelectEntity,startRow,ROWS_ONE_PAGE);

        return new ResponseData(1,"查询成功",userListBoList);
    }

    @Override
    public ResponseData getUserDetail(int userID) {
        UserDetailBo userDetailBo = userDao.getUserDetail(userID);
        return new ResponseData(1,"查询成功",userDetailBo);
    }

    @Override
    public ResponseData addUser(UserAddEntity userAddEntity) {
        // 对密码二次加密
        userAddEntity.setPwd(UserCommon.encodePwd(userAddEntity.getPwd()));

        userDao.addUser(userAddEntity);
        return new ResponseData(1,"添加成功",null);
    }

    @Override
    public ResponseData editUser(UserAddEntity userAddEntity) {
        userDao.updateUser(userAddEntity);
        return new ResponseData(1,"修改成功",null);
    }

    @Override
    public void deleteUser(int[] userID) {
        userDao.deleteUser(userID);
    }

    @Override
    public void syncUserInfo() {
        Object lastUpdateTime = cacheUtil.read("userInfo.lastUpdateTime");
        String lastUpdateTimeStr = "";
        if(lastUpdateTime == null){
            // 如果之前没有保存，就从2020年1月1日开始
            lastUpdateTimeStr = "2020-01-01 00:00:00";
        }else {
            lastUpdateTimeStr = String.valueOf(lastUpdateTime);
        }

        List<UserListBo> userList = userDao.getUnsyncUserList(lastUpdateTimeStr);

        // 发送数据到中心调配节点
        SyncUserInfoBo syncUserInfoBo = new SyncUserInfoBo();
        syncUserInfoBo.setNodeID(1); // Todo 暂时使用1为节点ID
        syncUserInfoBo.setUserListBo(userList);
        String msg = JsonUtil.objectToJson(syncUserInfoBo);
        rabbitSender.send("dtask.syncUserInfo",msg);

        lastUpdateTimeStr = DateUtil.getTimestamp();
        cacheUtil.write("userInfo.lastUpdateTime",lastUpdateTimeStr);
    }
}
