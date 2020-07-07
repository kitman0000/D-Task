package com.dtask.center.remoteTaskModule.controller;

import com.dtask.center.remoteTaskModule.entity.*;
import com.dtask.center.remoteTaskModule.service.IRemoteTask;
import com.dtask.common.util.JsonUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhong on 2020-5-3.
 */
public class RemoteTaskCl {

    @Autowired
    IRemoteTask remoteTask;

    @RabbitListener(queues = "dtask.remoteTask.addRemoteTask")
    public String addRemoteTask(String msg){
        try {
            AddRemoteTaskEntity addRemoteTaskEntity = (AddRemoteTaskEntity) JsonUtil.jsonToObject(msg,AddRemoteTaskEntity.class);
            return remoteTask.addRemoteTask(addRemoteTaskEntity);
        }catch (Exception ex) {
            return "SYS_FAILED";
        }
    }

    @RabbitListener(queues = "dtask.remoteTask.editRemoteTask")
    public String editRemoteTask(String msg){
        try {
            EditRemoteTaskEntity editRemoteTaskEntity = (EditRemoteTaskEntity) JsonUtil.jsonToObject(msg,EditRemoteTaskEntity.class);
            return remoteTask.editRemoteTask(editRemoteTaskEntity);
        }catch (Exception ex) {
            return "SYS_FAILED";
        }
    }

    @RabbitListener(queues = "dtask.remoteTask.deleteRemoteTask")
    public String deleteRemoteTask(String msg){
        try {
            DeleteRemoteTaskEntity deleteRemoteTaskEntity = (DeleteRemoteTaskEntity) JsonUtil.jsonToObject(msg,DeleteRemoteTaskEntity.class);
            return remoteTask.deleteRemoteTask(deleteRemoteTaskEntity);
        }catch (Exception ex) {
            return "SYS_FAILED";
        }
    }

    @RabbitListener(queues = "dtask.remoteTask.getRemoteTaskNumber")
    public String getRemoteTaskNumber(String msg){
        try {
            RemoteTaskSearchEntity remoteTaskSearchEntity = (RemoteTaskSearchEntity) JsonUtil.jsonToObject(msg,RemoteTaskSearchEntity.class);
            return remoteTask.getRemoteTaskNumber(remoteTaskSearchEntity);
        }catch (Exception ex) {
            return "SYS_FAILED";
        }
    }

    @RabbitListener(queues = "dtask.remoteTask.getRemoteTaskList")
    public String getRemoteTaskList(String msg){
        try {
            GetRemoteTaskListEntity getRemoteTaskListEntity = (GetRemoteTaskListEntity)JsonUtil.jsonToObject(msg,GetRemoteTaskListEntity.class);
            return remoteTask.getRemoteTaskList(getRemoteTaskListEntity.getRemoteTaskSearchEntity(), getRemoteTaskListEntity.getPage());
        }catch (Exception ex) {
            return "SYS_FAILED";
        }
    }

    @RabbitListener(queues = "dtask.remoteTask.addRemoteTaskMember")
    public String addRemoteTaskMember(String msg){
        try {
            AddRemoteTaskMemberEntity addRemoteTaskMemberEntity = (AddRemoteTaskMemberEntity) JsonUtil.jsonToObject(msg,AddRemoteTaskMemberEntity.class);
            return remoteTask.addRemoteTaskMember(addRemoteTaskMemberEntity);
        }catch (Exception ex) {
            return "SYS_FAILED";
        }
    }

    @RabbitListener(queues = "dtask.remoteTask.removeRemoteTaskMember")
    public String removeRemoteTaskMember(String msg){
        try {
            RemoveRemoteTaskMemberEntity removeRemoteTaskMemberEntity = (RemoveRemoteTaskMemberEntity) JsonUtil.jsonToObject(msg,RemoveRemoteTaskMemberEntity.class);
            return remoteTask.removeRemoteTaskMember(removeRemoteTaskMemberEntity);
        }catch (Exception ex) {
            return "SYS_FAILED";
        }
    }

    @RabbitListener(queues = "dtask.remoteTask.getRemoteTaskMember")
    public String getLocalRemoteMember(String msg){
        try {
            GetRemoteTaskMemberEntity getRemoteTaskMemberEntity = (GetRemoteTaskMemberEntity) JsonUtil.jsonToObject(msg,GetRemoteTaskMemberEntity.class);
            return remoteTask.getRemoteTaskMember(getRemoteTaskMemberEntity);
        }catch (Exception ex) {
            return "SYS_FAILED";
        }
    }

    @RabbitListener(queues = "dtask.remoteTask.toggleTaskAdmin")
    public String toggleTaskAdmin(String msg){
        try {
            ToggleTaskAdminEntity toggleTaskAdminEntity = (ToggleTaskAdminEntity) JsonUtil.jsonToObject(msg,ToggleTaskAdminEntity.class);
            return remoteTask.toggleTaskAdmin(toggleTaskAdminEntity);
        }catch (Exception ex) {
            return "SYS_FAILED";
        }
    }
}
