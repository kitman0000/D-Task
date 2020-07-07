package com.dtask.DTask.remoteTaskModule.controller;

import com.dtask.DTask.localTaskModule.entity.LocalSubTaskEntity;
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

    public ResponseData addRemoteSubTask(RemoteSubTaskEntity remoteSubTaskEntity) {
        return remoteSubTask.addRemoteSubTask(remoteSubTaskEntity);
    }

    public ResponseData editRemoteSubTask(RemoteSubTaskEntity remoteSubTaskEntity){
        return remoteSubTask.editRemoteSubTask(remoteSubTaskEntity);
    }

    public ResponseData deleteRemoteSubTask(int[] id){
        return remoteSubTask.deleteRemoteSubTask(id);
    }

    public ResponseData getRemoteSubTaskNumber(RemoteSubTaskEntity remoteSubTaskEntity){
        return remoteSubTask.getRemoteSubTaskNumber(remoteSubTaskEntity);
    }

    public ResponseData getRemoteSubTaskList(RemoteSubTaskEntity remoteSubTaskEntity){
        return remoteSubTask.getRemoteSubTaskList(remoteSubTaskEntity);
    }

    public ResponseData editRemoteSubTaskStatus(RemoteSubTaskEntity remoteSubTaskEntity){
        return remoteSubTask.editRemoteSubTaskStatus(remoteSubTaskEntity);
    }
}
