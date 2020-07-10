package com.dtask.DTask.remoteTaskModule.controller;

import com.dtask.DTask.remoteTaskModule.entity.RemoteSubTaskEntity;
import com.dtask.DTask.remoteTaskModule.service.IRemoteSubTask;
import com.dtask.common.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2020-7-5.
 */
@RestController
public class RemoteSubTaskCl {

    @Autowired
    IRemoteSubTask remoteSubTask;

    @RequestMapping(value = "/api/remoteTask/remoteSubTask",method = RequestMethod.POST)
    public ResponseData addRemoteSubTask(RemoteSubTaskEntity remoteSubTaskEntity) {
        return remoteSubTask.addRemoteSubTask(remoteSubTaskEntity);
    }

    @RequestMapping(value = "/api/remoteTask/remoteSubTask",method = RequestMethod.PUT)
    public ResponseData editRemoteSubTask(RemoteSubTaskEntity remoteSubTaskEntity){
        return remoteSubTask.editRemoteSubTask(remoteSubTaskEntity);
    }

    @RequestMapping(value = "/api/remoteTask/remoteSubTask",method = RequestMethod.DELETE)
    public ResponseData deleteRemoteSubTask(int[] id){
        return remoteSubTask.deleteRemoteSubTask(id);
    }

    @RequestMapping(value = "/api/remoteTask/remoteSubTaskNumber",method = RequestMethod.GET)
    public ResponseData getRemoteSubTaskNumber(RemoteSubTaskEntity remoteSubTaskEntity){
        return remoteSubTask.getRemoteSubTaskNumber(remoteSubTaskEntity);
    }

    @RequestMapping(value = "/api/remoteTask/remoteSubTaskList",method = RequestMethod.GET)
    public ResponseData getRemoteSubTaskList(RemoteSubTaskEntity remoteSubTaskEntity){
        return remoteSubTask.getRemoteSubTaskList(remoteSubTaskEntity);
    }

    @RequestMapping(value = "/api/remoteTask/remoteSubTaskStatus",method = RequestMethod.PUT)
    public ResponseData editRemoteSubTaskStatus(RemoteSubTaskEntity remoteSubTaskEntity){
        return remoteSubTask.editRemoteSubTaskStatus(remoteSubTaskEntity);
    }
}
