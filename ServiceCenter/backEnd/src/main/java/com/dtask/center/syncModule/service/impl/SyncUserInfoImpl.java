package com.dtask.center.syncModule.service.impl;

import com.dtask.center.syncModule.dao.SyncUserDao;
import com.dtask.center.syncModule.entity.SyncUserInfoEntity;
import com.dtask.center.syncModule.entity.UserListEntity;
import com.dtask.center.syncModule.service.ISyncUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhong on 2020-6-11.
 */
@Service
public class SyncUserInfoImpl implements ISyncUserInfo {

    @Autowired
    private SyncUserDao syncUserDao;

    @Override
    public String syncUserInfo(SyncUserInfoEntity syncUserInfoEntity) {
        try {


            List<UserListEntity> userListEntityList = syncUserInfoEntity.getUserListEntity();

            // 通过循环将数据存入数据库，后期有时间再优化
            for (UserListEntity userListEntity : userListEntityList) {
                syncUserDao.insertUserInfo(userListEntity, syncUserInfoEntity.getNodeID());
            }
            return "sync_success";
        }catch (Exception ex){
            ex.printStackTrace();
            return "SYS_FAILED";
        }
    }
}
