package com.dtask.center.remoteTaskModule.service.impl;


import com.dtask.center.remoteTaskModule.bo.RemoteTaskMemberBo;
import com.dtask.center.remoteTaskModule.bo.RemoteTaskBo;
import com.dtask.center.remoteTaskModule.dao.RemoteTaskDao;
import com.dtask.center.remoteTaskModule.entity.*;
import com.dtask.center.remoteTaskModule.service.IRemoteTask;
import com.dtask.common.util.JsonUtil;
import com.dtask.common.util.PageDivideUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhong on 2020-5-3.
 */

@Service
public class RemoteTaskImpl implements IRemoteTask {
    private final int COUNT_ONE_PAGE = 10;

    @Autowired
    private RemoteTaskDao remoteTaskDao;

    @Override
    public String addRemoteTask(AddRemoteTaskEntity addRemoteTaskEntity) {

        remoteTaskDao.insertRemoteTask(addRemoteTaskEntity.getUserID(),addRemoteTaskEntity.getNodeID(),addRemoteTaskEntity.getName());

        int taskID = remoteTaskDao.getCurrentTaskID();
        remoteTaskDao.addTaskMember(taskID, addRemoteTaskEntity.getNodeID(),addRemoteTaskEntity.getUserID());
        // 将拥有者设为管理员
        remoteTaskDao.setRemoteTaskAdmin(taskID,addRemoteTaskEntity.getNodeID(),addRemoteTaskEntity.getUserID(),true);
        return "ADD_SUCCESS";
    }

    @Override
    public String editRemoteTask(EditRemoteTaskEntity editRemoteTaskEntity) {
        remoteTaskDao.updateRemoteTask(editRemoteTaskEntity.getId(),editRemoteTaskEntity.getName(),
                editRemoteTaskEntity.getCreatorNode(),editRemoteTaskEntity.getCreator(),
                editRemoteTaskEntity.isAllowedMemberChangeStatus());
        return "EDIT_SUCCESS";
    }

    @Override
    public String deleteRemoteTask(DeleteRemoteTaskEntity deleteRemoteTaskEntity) {
        remoteTaskDao.deleteRemoteTask(deleteRemoteTaskEntity.getId());
        return "DELETE_SUCCESS";
    }

    @Override
    public String getRemoteTaskNumber(RemoteTaskSearchEntity localTaskSearchEntity) {
        int amount = remoteTaskDao.getRemoteTaskNumber(localTaskSearchEntity);
        int page = PageDivideUtil.getCountOfPages(amount,COUNT_ONE_PAGE);

        return String.valueOf(page);
    }

    @Override
    public String getRemoteTaskList(RemoteTaskSearchEntity localTaskSearchEntity, int page) {
        int startRow = (page -1) * COUNT_ONE_PAGE;
        List<RemoteTaskBo> remoteTaskBoList = remoteTaskDao.getRemoteTaskList(localTaskSearchEntity,startRow,COUNT_ONE_PAGE);

        return JsonUtil.objectToJson(remoteTaskBoList);
    }

    @Override
    public String addRemoteTaskMember(AddRemoteTaskMemberEntity addRemoteTaskMemberEntity) {
        int taskID = addRemoteTaskMemberEntity.getTaskID();
        int nodeID = addRemoteTaskMemberEntity.getNodeID();
        int userID = addRemoteTaskMemberEntity.getUserID();
        int newUserID = addRemoteTaskMemberEntity.getNewUserID();
        int newUserNodeID = addRemoteTaskMemberEntity.getNewUserNodeID();

        if(!checkUserHasPermission(taskID,nodeID,userID)){
            return "PERMISSION_DENNY";
        }

        if(remoteTaskDao.checkTaskMemberExist(taskID,nodeID,userID) >= 1){
            return "USER_EXIST";
        }

        remoteTaskDao.addTaskMember(taskID,newUserNodeID,newUserID);
        return "ADD_SUCCESS";
    }

    @Override
    public String removeRemoteTaskMember(RemoveRemoteTaskMemberEntity removeRemoteTaskMemberEntity) {
        int taskID = removeRemoteTaskMemberEntity.getTaskID();
        int nodeID = removeRemoteTaskMemberEntity.getNodeID();
        int userID = removeRemoteTaskMemberEntity.getUserID();
        int removeUserID = removeRemoteTaskMemberEntity.getRemoveUserID();
        int removeUserNodeID = removeRemoteTaskMemberEntity.getRemoveUserNodeID();

        if(!checkUserHasPermission(taskID,nodeID,userID)){
            return "PERMISSION_DENNY";
        }

        if(remoteTaskDao.checkTaskMemberExist(taskID,nodeID,userID) == 0){
            return "USER_NOT_EXIST";
        }

        RemoteTaskMemberBo taskMemberBo = remoteTaskDao.getTaskCreator(taskID);
        if(taskMemberBo.getNodeID() == nodeID && taskMemberBo.getUserID() == userID){
            return "PERMISSION_DENNY";
        }

        remoteTaskDao.removeTaskMember(taskID,removeUserNodeID,removeUserID);

        return "DELETE_SUCCESS";

    }

    @Override
    public String getRemoteTaskMember(GetRemoteTaskMemberEntity getRemoteTaskMemberEntity) {
        List<RemoteTaskMemberBo> memberBoList = remoteTaskDao.getTaskUser(getRemoteTaskMemberEntity.getTaskID());
        return JsonUtil.objectToJson(memberBoList);
    }

    @Override
    public String toggleTaskAdmin(ToggleTaskAdminEntity toggleTaskAdminEntity) {
        int taskID = toggleTaskAdminEntity.getTaskID();
        int nodeID = toggleTaskAdminEntity.getNodeID();
        int userID = toggleTaskAdminEntity.getUserID();

        int toggleUserID = toggleTaskAdminEntity.getToggleUserID();
        int toggleUserNodeID = toggleTaskAdminEntity.getToggleUserNodeID();
        boolean isAdmin = toggleTaskAdminEntity.isAdmin();

        if(!checkUserHasPermission(taskID,nodeID,userID)){
            return "PERMISSION_DENNY";
        }

        if(!checkUserHasPermission(taskID,nodeID,userID)){
            return "PERMISSION_DENNY";
        }

        remoteTaskDao.setRemoteTaskAdmin(taskID,toggleUserNodeID,toggleUserID,isAdmin);

        return "EDIT_SUCCESS";
    }

    @Override
    public String getUserTaskNumber(RemoteTaskSearchEntity remoteTaskSearchEntity) {
        int numbers = remoteTaskDao.getUserTaskNumber(remoteTaskSearchEntity);
        int page = PageDivideUtil.getCountOfPages(numbers,COUNT_ONE_PAGE);

        return String.valueOf(page);
    }

    @Override
    public String getUserTaskList(RemoteTaskSearchEntity remoteTaskSearchEntity, int page) {
        int startRow = (page -1) * COUNT_ONE_PAGE;
        List<RemoteTaskBo> remoteTaskBoList = remoteTaskDao.getUserRemoteTaskList(remoteTaskSearchEntity,startRow,COUNT_ONE_PAGE);
        return JsonUtil.objectToJson(remoteTaskBoList);
    }

    @Override
    public String getTaskUserRole(GetTaskUserRole getTaskUserRole) {

        RemoteTaskMemberBo creator = remoteTaskDao.getTaskCreator(getTaskUserRole.getTaskID());
        int creatorID = creator.getUserID();
        int creatorNode = creator.getNodeID();

        if(creatorID == getTaskUserRole.getUserID() && creatorNode == getTaskUserRole.getNodeID()){
            // 任务拥有者
            return "1";
        }
        if(checkUserHasPermission(getTaskUserRole.getTaskID(),getTaskUserRole.getNodeID(),getTaskUserRole.getUserID())){
            // 任务管理员
            return "2";
        }

        // 任务参与者
        return "3";
    }

    @Override
    public String getAllowUserChangeStatus(int taskID) {
        boolean allowUserChangeStatus = remoteTaskDao.getAllowUserChangeStatus(taskID);
        return String.valueOf(allowUserChangeStatus);
    }

    /**
     * 判断用户是否拥有管理权限
     */
    private boolean checkUserHasPermission(int taskID,int nodeID,int userID){
        // 一般情况下任务所有者和管理员都拥有管理权限，但防止后期变化，特此列出
        try {
            return remoteTaskDao.checkIsAdmin(taskID, nodeID, userID);
        }catch (Exception ex){
            return false;
        }
    }
}
