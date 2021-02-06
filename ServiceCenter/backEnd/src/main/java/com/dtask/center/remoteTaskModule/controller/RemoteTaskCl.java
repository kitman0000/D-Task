package com.dtask.center.remoteTaskModule.controller;

import com.MQClouder.MessageReceiver;
import com.dtask.center.remoteTaskModule.entity.*;
import com.dtask.center.remoteTaskModule.service.IRemoteTask;
import com.dtask.common.util.CacheUtil;
import com.dtask.common.util.JsonUtil;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhong on 2020-5-3.
 */
@Component
public class RemoteTaskCl {

    @Autowired
    IRemoteTask remoteTask;

    @MessageReceiver
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dtask.remoteTask.addRemoteTask"),
            exchange = @Exchange(value = "topicExchange",type = "topic"),
            key = "dtask.remoteTask.addRemoteTask"
    ))
    public String addRemoteTask(String msg){
        try {
            AddRemoteTaskEntity addRemoteTaskEntity = (AddRemoteTaskEntity) JsonUtil.jsonToObject(msg,AddRemoteTaskEntity.class);
            return remoteTask.addRemoteTask(addRemoteTaskEntity);
        }catch (Exception ex) {
            ex.printStackTrace();
            return "SYS_FAILED";
        }
    }

    @MessageReceiver
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dtask.remoteTask.editRemoteTask"),
            exchange = @Exchange(value = "topicExchange",type = "topic"),
            key = "dtask.remoteTask.editRemoteTask"
    ))
    public String editRemoteTask(String msg){
        try {
            EditRemoteTaskEntity editRemoteTaskEntity = (EditRemoteTaskEntity) JsonUtil.jsonToObject(msg,EditRemoteTaskEntity.class);
            return remoteTask.editRemoteTask(editRemoteTaskEntity);
        }catch (Exception ex) {
            ex.printStackTrace();
            return "SYS_FAILED";
        }
    }

    @MessageReceiver
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dtask.remoteTask.getTaskDetail"),
            exchange = @Exchange(value = "topicExchange",type = "topic"),
            key = "dtask.remoteTask.getTaskDetail"
    ))
    public String getTaskDetail(String msg){
        try {
            int taskID = Integer.valueOf(msg);
            return remoteTask.getTaskDetail(taskID);
        }catch (Exception ex){
            ex.printStackTrace();
            return "SYS_FAILED";
        }

    }

    @MessageReceiver
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dtask.remoteTask.deleteRemoteTask"),
            exchange = @Exchange(value = "topicExchange",type = "topic"),
            key = "dtask.remoteTask.deleteRemoteTask"
    ))
    public String deleteRemoteTask(String msg){
        try {
            DeleteRemoteTaskEntity deleteRemoteTaskEntity = (DeleteRemoteTaskEntity) JsonUtil.jsonToObject(msg,DeleteRemoteTaskEntity.class);
            return remoteTask.deleteRemoteTask(deleteRemoteTaskEntity);
        }catch (Exception ex) {
            ex.printStackTrace();
            return "SYS_FAILED";
        }
    }

    @MessageReceiver
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dtask.remoteTask.getRemoteTaskNumber"),
            exchange = @Exchange(value = "topicExchange",type = "topic"),
            key = "dtask.remoteTask.getRemoteTaskNumber"
    ))
    public String getRemoteTaskNumber(String msg){
        try {
            RemoteTaskSearchEntity remoteTaskSearchEntity = (RemoteTaskSearchEntity) JsonUtil.jsonToObject(msg,RemoteTaskSearchEntity.class);
            return remoteTask.getRemoteTaskNumber(remoteTaskSearchEntity);
        }catch (Exception ex) {
            ex.printStackTrace();
            return "SYS_FAILED";
        }
    }

    @MessageReceiver
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dtask.remoteTask.getRemoteTaskList"),
            exchange = @Exchange(value = "topicExchange",type = "topic"),
            key = "dtask.remoteTask.getRemoteTaskList"
    ))
    public String getRemoteTaskList(String msg){
        try {
            RemoteTaskSearchEntity remoteTaskSearchEntity = (RemoteTaskSearchEntity)JsonUtil.jsonToObject(msg,RemoteTaskSearchEntity.class);
            return remoteTask.getRemoteTaskList(remoteTaskSearchEntity, remoteTaskSearchEntity.getPage());
        }catch (Exception ex) {
            ex.printStackTrace();
            return "SYS_FAILED";
        }
    }

    @MessageReceiver
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dtask.remoteTask.addRemoteTaskMember"),
            exchange = @Exchange(value = "topicExchange",type = "topic"),
            key = "dtask.remoteTask.addRemoteTaskMember"
    ))
    public String addRemoteTaskMember(String msg){
        try {
            AddRemoteTaskMemberEntity addRemoteTaskMemberEntity = (AddRemoteTaskMemberEntity) JsonUtil.jsonToObject(msg,AddRemoteTaskMemberEntity.class);
            return remoteTask.addRemoteTaskMember(addRemoteTaskMemberEntity);
        }catch (Exception ex) {
            ex.printStackTrace();
            return "SYS_FAILED";
        }
    }

    @MessageReceiver
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dtask.remoteTask.removeRemoteTaskMember"),
            exchange = @Exchange(value = "topicExchange",type = "topic"),
            key = "dtask.remoteTask.removeRemoteTaskMember"
    ))
    public String removeRemoteTaskMember(String msg){
        try {
            RemoveRemoteTaskMemberEntity removeRemoteTaskMemberEntity = (RemoveRemoteTaskMemberEntity) JsonUtil.jsonToObject(msg,RemoveRemoteTaskMemberEntity.class);
            return remoteTask.removeRemoteTaskMember(removeRemoteTaskMemberEntity);
        }catch (Exception ex) {
            ex.printStackTrace();
            return "SYS_FAILED";
        }
    }

    @MessageReceiver
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dtask.remoteTask.getRemoteMember"),
            exchange = @Exchange(value = "topicExchange",type = "topic"),
            key = "dtask.remoteTask.getRemoteMember"
    ))
    public String getRemoteMember(String msg){
        try {
            GetRemoteTaskMemberEntity getRemoteTaskMemberEntity = (GetRemoteTaskMemberEntity) JsonUtil.jsonToObject(msg,GetRemoteTaskMemberEntity.class);
            return remoteTask.getRemoteTaskMember(getRemoteTaskMemberEntity);
        }catch (Exception ex) {
            ex.printStackTrace();
            return "SYS_FAILED";
        }
    }

    @MessageReceiver
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dtask.remoteTask.getUserTaskNumber"),
            exchange = @Exchange(value = "topicExchange",type = "topic"),
            key = "dtask.remoteTask.getUserTaskNumber"
    ))
    public String getUserTaskNumber(String msg){
        try {
            RemoteTaskSearchEntity remoteTaskSearchEntity = (RemoteTaskSearchEntity) JsonUtil.jsonToObject(msg,RemoteTaskSearchEntity.class);
            return remoteTask.getUserTaskNumber(remoteTaskSearchEntity);
        } catch (Exception ex){
            ex.printStackTrace();
            return "SYS_FAILED";
        }
    }

    @MessageReceiver
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dtask.remoteTask.getUserTaskList"),
            exchange = @Exchange(value = "topicExchange",type = "topic"),
            key = "dtask.remoteTask.getUserTaskList"
    ))
    public String getUserTaskList(String msg){
        try {
            RemoteTaskSearchEntity remoteTaskSearchEntity = (RemoteTaskSearchEntity)JsonUtil.jsonToObject(msg,RemoteTaskSearchEntity.class);
            return remoteTask.getUserTaskList(remoteTaskSearchEntity,remoteTaskSearchEntity.getPage());
        }catch (Exception ex){
            ex.printStackTrace();
            return "SYS_FAILED";
        }
    }

    @MessageReceiver
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dtask.remoteTask.toggleTaskAdmin"),
            exchange = @Exchange(value = "topicExchange",type = "topic"),
            key = "dtask.remoteTask.toggleTaskAdmin"
    ))
    public String toggleTaskAdmin(String msg){
        try {
            ToggleTaskAdminEntity toggleTaskAdminEntity = (ToggleTaskAdminEntity) JsonUtil.jsonToObject(msg,ToggleTaskAdminEntity.class);
            return remoteTask.toggleTaskAdmin(toggleTaskAdminEntity);
        }catch (Exception ex) {
            ex.printStackTrace();
            return "SYS_FAILED";
        }
    }

    @MessageReceiver
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dtask.remoteTask.getTaskUserRole"),
            exchange = @Exchange(value = "topicExchange",type = "topic"),
            key = "dtask.remoteTask.getTaskUserRole"
    ))
    public String getTaskUserRole(String msg){
        try {
            GetTaskUserRole getTaskUserRole = (GetTaskUserRole) JsonUtil.jsonToObject(msg,GetTaskUserRole.class);
            return remoteTask.getTaskUserRole(getTaskUserRole);
        }catch (Exception ex){
            ex.printStackTrace();
            return "SYS_FAILED";
        }
    }

    @MessageReceiver
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dtask.remoteTask.getAllowUserChangeStatus"),
            exchange = @Exchange(value = "topicExchange",type = "topic"),
            key = "dtask.remoteTask.getAllowUserChangeStatus"
    ))
    public String getAllowUserChangeStatus(String msg){
        try {
            int taskID = Integer.valueOf(msg);
            return remoteTask.getAllowUserChangeStatus(taskID);
        } catch (Exception ex){
            ex.printStackTrace();
            return "SYS_FAILED";
        }
    }

}
