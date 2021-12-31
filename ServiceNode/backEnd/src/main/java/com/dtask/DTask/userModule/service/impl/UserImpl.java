package com.dtask.DTask.userModule.service.impl;

import com.MQClouder.EncryptRabbitSender;
import com.dtask.DTask.userModule.bo.DTaskUser;
import com.dtask.DTask.userModule.bo.SyncUserInfoBo;
import com.dtask.DTask.userModule.bo.UserDetailBo;
import com.dtask.DTask.userModule.bo.UserListBo;
import com.dtask.DTask.userModule.dao.UserDao;
import com.dtask.DTask.userModule.entity.UserAddEntity;
import com.dtask.DTask.userModule.entity.UserSelectEntity;
import com.dtask.DTask.userModule.service.IUser;
import com.dtask.common.NodeCommon;
import com.dtask.common.ResponseData;
import com.dtask.common.UserCommon;
import com.dtask.common.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhong on 2020-3-24.
 */
@Service
public class UserImpl implements IUser {

    @Value("${arcAddress}")
    private String arcAddress;

    private static final int ROWS_ONE_PAGE = 10;
    @Autowired
    private UserDao userDao;

    @Autowired
    private CacheUtil cacheUtil;

    @Autowired
    private EncryptRabbitSender rabbitSender;

    @Autowired
    private NodeCommon nodeCommon;

    @Autowired
    private IntegrationUtil integrationUtil;

    @Override
    public ResponseData getUserOwnDetail() {
        int userID =  UserCommon.getUserBo().getUserID();
        UserDetailBo userDetailBo = userDao.getUserDetail(userID);
        return new ResponseData(1,"查询成功",userDetailBo);
    }

    /**
     * 用户自行修改个人账户数据
     * @param userAddEntity
     * @return
     */
    @Override
    public ResponseData changeUserDetail(UserAddEntity userAddEntity) {
        int userID = UserCommon.getUserBo().getUserID();
        userAddEntity.setId(userID);

        userDao.changeUserDetail(userAddEntity);

        return new ResponseData(1,"查询成功",null);
    }

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
    public boolean syncUserInfo() {
        int nodeID = nodeCommon.getNodeID();
        if(nodeID == -1){
            return false;
        }

        Object lastUpdateTime = cacheUtil.read("userInfo.lastUpdateTime");
        String lastUpdateTimeStr = "";
        if(lastUpdateTime == null){
            // 如果之前没有保存，就从2020年1月1日开始
            lastUpdateTimeStr = "2020-01-01 00:00:00";
        }else {
            lastUpdateTimeStr = String.valueOf(lastUpdateTime);
        }

        List<UserListBo> userList = userDao.getUnsyncUserList(lastUpdateTimeStr);
        lastUpdateTimeStr = DateUtil.getTimestamp();

        // 发送数据到中心调配节点
        SyncUserInfoBo syncUserInfoBo = new SyncUserInfoBo();
        if(userList.isEmpty()){
            return false;
        }

        syncUserInfoBo.setNodeID(nodeID);
        syncUserInfoBo.setUserListBo(userList);
        String msg = JsonUtil.objectToJson(syncUserInfoBo);
        rabbitSender.encryptSend("dtask.syncUserInfo",msg);

        cacheUtil.write("userInfo.lastUpdateTime",lastUpdateTimeStr);
        return true;
    }

    @Override
    public void syncUserInfoToArc() {
        Object lastUpdateTime = cacheUtil.read("userInfo.arc.lastUpdateTime");
        String lastUpdateTimeStr = "";
        if(lastUpdateTime == null){
            // 如果之前没有保存，就从2020年1月1日开始
            lastUpdateTimeStr = "2020-01-01 00:00:00";
        }else {
            lastUpdateTimeStr = String.valueOf(lastUpdateTime);
        }

        List<UserListBo> userList = userDao.getUnsyncUserList(lastUpdateTimeStr);
        lastUpdateTimeStr = DateUtil.getTimestamp();

        if (userList.isEmpty()){
            return;
        }

        // Convert to DTask User list, because it's going to transport to external system.
        List<DTaskUser> taskUserList = new LinkedList<>();
        userList.forEach(user->{
            DTaskUser taskUser = new DTaskUser();
            taskUser.setUsername(user.getNickname());
            taskUser.setDTaskID(user.getId());
            taskUser.setAvailable(user.isAvailable());
            taskUserList.add(taskUser);
        });

        IdentityHashMap<String,String> paramsMap = new IdentityHashMap<String,String>(){
            {
                put("users",JsonUtil.objectToJson(taskUserList));
            }
        };

        integrationUtil.post(arcAddress + "/DTask/synchronizedUser",paramsMap,false);

        cacheUtil.write("userInfo.arc.lastUpdateTime",lastUpdateTimeStr);
    }

    @Override
    public ResponseData getRemoteUser(int nodeID) {
        String res = rabbitSender.encryptSend("dtask.user.remote",String.valueOf(nodeID));
        return new ResponseData(1,"查询成功",res);
    }
}
