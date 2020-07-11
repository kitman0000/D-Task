package com.dtask.center.remoteTaskModule.controller;

import com.dtask.center.remoteTaskModule.entity.RemoteSubTaskEntity;
import com.dtask.center.remoteTaskModule.service.IRemoteSubTask;
import com.dtask.common.util.JsonUtil;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2020-5-3.
 */
@Component
public class RemoteSubTaskCl {

    @Autowired
    IRemoteSubTask remoteSubTask;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dtask.remoteSubTask.addRemoteSubTask"),
            exchange = @Exchange(value = "topicExchange",type = "topic"),
            key = "dtask.remoteSubTask.addRemoteSubTask"
    ))
    public String addRemoteSubTask(String msg) {
        try {
            RemoteSubTaskEntity remoteSubTaskEntity = (RemoteSubTaskEntity)JsonUtil.jsonToObject(msg,RemoteSubTaskEntity.class);
            return remoteSubTask.addRemoteSubTask(remoteSubTaskEntity);
        }catch (Exception ex){
            return "SYS_FAILED";
        }
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dtask.remoteSubTask.editRemoteSubTask"),
            exchange = @Exchange(value = "topicExchange",type = "topic"),
            key = "dtask.remoteSubTask.editRemoteSubTask"
    ))
    public String editRemoteSubTask(String msg) {
        try {
            RemoteSubTaskEntity remoteSubTaskEntity = (RemoteSubTaskEntity) JsonUtil.jsonToObject(msg,RemoteSubTaskEntity.class);
            return remoteSubTask.editRemoteSubTask(remoteSubTaskEntity);
        }catch (Exception ex){
            return "SYS_FAILED";
        }
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dtask.remoteSubTask.deleteRemoteSubTask"),
            exchange = @Exchange(value = "topicExchange",type = "topic"),
            key = "dtask.remoteSubTask.deleteRemoteSubTask"
    ))
    public String deleteRemoteSubTask(String msg) {
        try {
            int[] id = (int[]) JsonUtil.jsonToObject(msg, int[].class);
            return remoteSubTask.deleteRemoteSubTask(id);
        }catch (Exception ex) {
            return "SYS_FAILED";
        }
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dtask.remoteSubTask.getRemoteSubTaskNumber"),
            exchange = @Exchange(value = "topicExchange",type = "topic"),
            key = "dtask.remoteSubTask.getRemoteSubTaskNumber"
    ))
    public String getRemoteSubTaskNumber(String msg) {
        try {
            RemoteSubTaskEntity remoteSubTaskEntity = (RemoteSubTaskEntity)JsonUtil.jsonToObject(msg,RemoteSubTaskEntity.class);
            return remoteSubTask.getRemoteSubTaskNumber(remoteSubTaskEntity);
        }catch (Exception ex) {
            return "SYS_FAILED";
        }
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dtask.remoteSubTask.getRemoteSubTaskList"),
            exchange = @Exchange(value = "topicExchange",type = "topic"),
            key = "dtask.remoteSubTask.getRemoteSubTaskList"
    ))
    public String getRemoteSubTaskList(String msg) {
        try {
            RemoteSubTaskEntity remoteSubTaskEntity = (RemoteSubTaskEntity) JsonUtil.jsonToObject(msg, RemoteSubTaskEntity.class);
            return remoteSubTask.getRemoteSubTaskList(remoteSubTaskEntity);
        }catch (Exception ex) {
            return "SYS_FAILED";
        }
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dtask.remoteSubTask.editRemoteSubTaskStatus"),
            exchange = @Exchange(value = "topicExchange",type = "topic"),
            key = "dtask.remoteSubTask.editRemoteSubTaskStatus"
    ))
    public String editRemoteSubTaskStatus(String msg) {
        try {
            RemoteSubTaskEntity remoteSubTaskEntity = (RemoteSubTaskEntity)JsonUtil.jsonToObject(msg,RemoteSubTaskEntity.class);
            return remoteSubTask.editRemoteSubTaskStatus(remoteSubTaskEntity);
        }catch (Exception ex) {
            return "SYS_FAILED";
        }
    }
}
