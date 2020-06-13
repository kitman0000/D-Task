package com.dtask.DTask.localTaskModule.service.impl;

import com.dtask.DTask.localTaskModule.bo.LocalTaskBo;
import com.dtask.DTask.localTaskModule.bo.LocalTaskMemberBo;
import com.dtask.DTask.localTaskModule.dao.LocalTaskDao;
import com.dtask.DTask.localTaskModule.entity.LocalTaskSearchEntity;
import com.dtask.DTask.localTaskModule.service.ILocalTask;
import com.dtask.common.ResponseData;
import com.dtask.common.UserCommon;
import com.dtask.common.util.PageDivideUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhong on 2020-5-3.
 */

@Service
public class LocalTaskImpl implements ILocalTask {
    private final int COUNT_ONE_PAGE = 10;

    @Autowired
    private LocalTaskDao localTaskDao;

    @Override
    public ResponseData addLocalTask(String name) {
        int userID = UserCommon.getUserBo().getUserID();
        localTaskDao.insertLocalTask(userID,name);

        int taskID = localTaskDao.getCurrentTaskID();
        localTaskDao.addTaskMember(taskID,userID);
        return new ResponseData(1,"添加成功",null);
    }

    @Override
    public ResponseData editLocalTask(int id, String name, int creator, boolean allowedMemberChangeStatus) {
        localTaskDao.updateLocalTask(id,name,creator,allowedMemberChangeStatus);
        return new ResponseData(1,"修改成功",null);
    }

    @Override
    public ResponseData deleteLocalTask(int id) {
        localTaskDao.deleteLocalTask(id);
        return new ResponseData(1,"删除成功",null);
    }

    @Override
    public ResponseData getLocalTaskNumber(LocalTaskSearchEntity localTaskSearchEntity) {
        int amount = localTaskDao.getLocalTaskNumber(localTaskSearchEntity);
        int page = PageDivideUtil.getCountOfPages(amount,COUNT_ONE_PAGE);

        return new ResponseData(1,"查询成功",page);
    }

    @Override
    public ResponseData getLocalTaskList(LocalTaskSearchEntity localTaskSearchEntity, int page) {
        int startRow = (page -1) * COUNT_ONE_PAGE;
        List<LocalTaskBo> localTaskBoList = localTaskDao.getLocalTaskList(localTaskSearchEntity,startRow,COUNT_ONE_PAGE);

        return new ResponseData(1,"查询成功",localTaskBoList);
    }

    @Override
    public ResponseData getUserTaskNumber(LocalTaskSearchEntity localTaskSearchEntity) {
        int userID = UserCommon.getUserBo().getUserID();

        int amount = localTaskDao.getUserLocalTaskNumber(localTaskSearchEntity,userID);
        int page = PageDivideUtil.getCountOfPages(amount,COUNT_ONE_PAGE);

        return new ResponseData(1,"查询成功",page);
    }

    @Override
    public ResponseData getUserTaskList(LocalTaskSearchEntity localTaskSearchEntity, int page) {
        int userID = UserCommon.getUserBo().getUserID();

        int startRow = (page -1) * COUNT_ONE_PAGE;
        List<LocalTaskBo> localTaskBoList = localTaskDao.getUserLocalTaskList(localTaskSearchEntity,userID,startRow,COUNT_ONE_PAGE);

        return new ResponseData(1,"查询成功",localTaskBoList);
    }

    @Override
    public ResponseData addLocalTaskMember(int taskID, int userID) {
        if(!checkUserHasPermission(taskID,userID)){
            return new ResponseData(2,"权限不足",null);
        }

        if(localTaskDao.checkTaskMemberExist(taskID,userID) >= 1){
            return new ResponseData(2,"用户已存在该任务中",null);
        }

        localTaskDao.addTaskMember(taskID,userID);
        return new ResponseData(1,"添加成功",null);
    }

    @Override
    public ResponseData removeLocalTaskMember(int taskID, int userID) {
        if(!checkUserHasPermission(taskID,userID)){
            return new ResponseData(2,"权限不足",null);
        }

        if(localTaskDao.checkTaskMemberExist(taskID,userID) == 0){
            return new ResponseData(2,"用户不存在该任务中",null);
        }

        if(localTaskDao.getTaskCreator(taskID) == userID){
            return new ResponseData(2,"不可以删除任务拥有者",null);
        }

        localTaskDao.removeTaskMember(taskID,userID);

        return new ResponseData(1,"删除成功",null);

    }

    @Override
    public ResponseData getLocalTaskMember(int taskID) {
        List<LocalTaskMemberBo> memberBoList = localTaskDao.getTaskUser(taskID);
        return new ResponseData(1,"查询成功",memberBoList);
    }

    @Override
    public ResponseData toggleTaskAdmin(int taskID, int userID, boolean isAdmin) {
        if(!checkUserHasPermission(taskID,userID)){
            return new ResponseData(2,"权限不足",null);
        }

        if(localTaskDao.getTaskCreator(taskID) == userID){
            return new ResponseData(1,"任务所有者不可切换管理员",null);
        }

        localTaskDao.setLocalTaskAdmin(taskID,userID,isAdmin);

        return new ResponseData(1,"修改成功",null);
    }

    /**
     * 判断用户是否拥有管理权限
     */
    private boolean checkUserHasPermission(int taskID,int userID){
        // 一般情况下任务所有者和管理员都拥有管理权限，但防止后期变化，特此列出
        return localTaskDao.checkIsAdmin(taskID,userID);
    }
}
