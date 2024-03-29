package com.dtask.DTask.remoteTaskModule.controller;

import com.dtask.DTask.remoteTaskModule.entity.RemoteTaskSearchEntity;
import com.dtask.DTask.remoteTaskModule.service.IRemoteTask;
import com.dtask.common.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2020-7-2.
 */
@RestController
public class RemoteTaskCl {
    @Autowired IRemoteTask remoteTask;

    @RequestMapping(value = "/api/remoteTask/remoteTask",method = RequestMethod.POST)
    public ResponseData addRemoteTask(String name) {
       return remoteTask.addRemoteTask(name);
    }

    @RequestMapping(value = "/api/remoteTask/remoteTask",method = RequestMethod.PUT)
    public ResponseData editRemoteTask(int id, String name,int creatorNodeID, int creator, boolean allowedMemberChangeStatus) {
        return remoteTask.editRemoteTask(id,name,creator,allowedMemberChangeStatus,creatorNodeID);
    }

    @RequestMapping(value = "/api/remoteTask/remoteTaskDetail",method = RequestMethod.GET)
    public ResponseData getTaskDetail(int taskID){
        return remoteTask.getTaskDetail(taskID);
    }

    @RequestMapping(value = "/api/remoteTask/remoteTask",method = RequestMethod.DELETE)
    public ResponseData deleteRemoteTask(int id) {
        return remoteTask.deleteRemoteTask(id);
    }

    @RequestMapping(value = "/api/remoteTask/remoteTaskPage",method = RequestMethod.GET)
    public ResponseData getRemoteTaskNumber(RemoteTaskSearchEntity remoteTaskSearchEntity) {
        return remoteTask.getRemoteTaskNumber(remoteTaskSearchEntity);
    }

    @RequestMapping(value = "/api/remoteTask/remoteTaskList",method = RequestMethod.GET)
    public ResponseData getRemoteTaskList(RemoteTaskSearchEntity remoteTaskSearchEntity) {
        return remoteTask.getRemoteTaskList(remoteTaskSearchEntity);
    }

    @RequestMapping(value = "/api/remoteTask/remoteTaskMember",method = RequestMethod.POST)
    public ResponseData addRemoteTaskMember(int taskID, int newUserID,int newUserNodeID) {
        return remoteTask.addRemoteTaskMember(taskID,newUserID, newUserNodeID);
    }

    @RequestMapping(value = "/api/remoteTask/remoteTaskMember",method = RequestMethod.DELETE)
    public ResponseData removeRemoteTaskMember(int taskID, int removeUserID,int removeUserNodeID) {
        return remoteTask.removeRemoteTaskMember(taskID,removeUserID,removeUserNodeID );
    }

    @RequestMapping(value = "/api/remoteTask/remoteTaskMember",method = RequestMethod.GET)
    public ResponseData getRemoteTaskMember(int taskID) {
        return remoteTask.getRemoteTaskMember(taskID);
    }

    @RequestMapping(value = "/api/remoteTask/Admin",method = RequestMethod.PUT)
    public ResponseData toggleTaskAdmin(int taskID, int toggleUserID,int toggleUserNodeID, boolean isAdmin) {
        return remoteTask.toggleTaskAdmin(taskID,toggleUserID, toggleUserNodeID, isAdmin);
    }

    @RequestMapping(value = "/api/remoteTask/userRemoteTaskPage",method = RequestMethod.GET)
    public ResponseData getUserTaskNumber(RemoteTaskSearchEntity remoteTaskSearchEntity) {
        return remoteTask.getUserTaskNumber(remoteTaskSearchEntity);
    }

    @RequestMapping(value = "/api/remoteTask/userRemoteTaskList",method = RequestMethod.GET)
    public ResponseData getUserTaskList(RemoteTaskSearchEntity remoteTaskSearchEntity) {
        return remoteTask.getUserTaskList(remoteTaskSearchEntity);
    }

    @RequestMapping(value = "/api/remoteTask/userRole",method = RequestMethod.GET)
    public ResponseData getTaskUserRole(int taskID){
        return remoteTask.getTaskUserRole(taskID);
    }

    @RequestMapping(value = "/api/remoteTask/allowUserChangeStatus",method = RequestMethod.GET)
    public ResponseData getAllowUserChangeStatus(int taskID){
        return remoteTask.getAllowUserChangeStatus(taskID);
    }

}
