package com.dtask.DTask.remoteTaskModule.controller;

import com.dtask.DTask.remoteTaskModule.entity.RemoteTaskSearchEntity;
import com.dtask.DTask.remoteTaskModule.service.IRemoteTask;
import com.dtask.common.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2020-7-2.
 */
@RestController
public class RemoteTaskCl {
    @Autowired IRemoteTask remoteTask;

    public ResponseData addRemoteTask(String name) {
       return remoteTask.addRemoteTask(name);
    }

    public ResponseData editRemoteTask(int id, String name,int creatorNodeID, int creator, boolean allowedMemberChangeStatus) {
        return remoteTask.editRemoteTask(id,name,creator,allowedMemberChangeStatus,creatorNodeID);
    }

    public ResponseData deleteRemoteTask(int id) {
        return remoteTask.deleteRemoteTask(id);
    }

    public ResponseData getRemoteTaskNumber(RemoteTaskSearchEntity remoteTaskSearchEntity) {
        return remoteTask.getRemoteTaskNumber(remoteTaskSearchEntity);
    }

    public ResponseData getRemoteTaskList(RemoteTaskSearchEntity remoteTaskSearchEntity, int page) {
        return remoteTask.getRemoteTaskList(remoteTaskSearchEntity,page);
    }

    public ResponseData addRemoteTaskMember(int taskID, int newUserID,int newUserNodeID) {
        return remoteTask.addRemoteTaskMember(taskID,newUserID, newUserNodeID);
    }

    public ResponseData removeRemoteTaskMember(int taskID, int removeUserID,int removeUserNodeID) {
        return remoteTask.removeRemoteTaskMember(taskID,removeUserID,removeUserNodeID );
    }

    public ResponseData getRemoteTaskMember(int taskID) {
        return remoteTask.getRemoteTaskMember(taskID);
    }

    public ResponseData toggleTaskAdmin(int taskID, int toggleUserID,int toggleUserNodeID, boolean isAdmin) {
        return remoteTask.toggleTaskAdmin(taskID,toggleUserID, toggleUserNodeID, isAdmin);
    }

    public ResponseData getUserTaskNumber(RemoteTaskSearchEntity remoteTaskSearchEntity) {
        return remoteTask.getUserTaskNumber(remoteTaskSearchEntity);
    }

    public ResponseData getUserTaskList(RemoteTaskSearchEntity remoteTaskSearchEntity, int page) {
        return remoteTask.getUserTaskList(remoteTaskSearchEntity,page);
    }
}
