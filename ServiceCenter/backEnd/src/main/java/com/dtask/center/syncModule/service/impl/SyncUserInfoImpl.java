package com.dtask.center.syncModule.service.impl;

import com.dtask.center.syncModule.bo.UserBo;
import com.dtask.center.syncModule.dao.SyncUserDao;
import com.dtask.center.syncModule.entity.SyncUserInfoEntity;
import com.dtask.center.syncModule.entity.UserListEntity;
import com.dtask.center.syncModule.service.ISyncUserInfo;
import com.dtask.common.util.JsonUtil;
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
            // 解析节点传来的用户数据
            List<UserListEntity> userListEntityList = syncUserInfoEntity.getUserListEntity();

            // 如果数据不为空，则进行同步操作
            // 空数据执行会引发SQL异常，所以直接跳过
            if(!userListEntityList.isEmpty()) {
                syncUserDao.insertUserInfo(userListEntityList, syncUserInfoEntity.getNodeID());
            }
            return "sync_success";
        }catch (Exception ex){
            ex.printStackTrace();
            return "SYS_FAILED";
        }
    }

    @Override
    public String getRemoteUser(int nodeID) {
        List<UserBo> userBoList = syncUserDao.getRemoteUser(nodeID);
        return JsonUtil.objectToJson(userBoList);
    }
}
