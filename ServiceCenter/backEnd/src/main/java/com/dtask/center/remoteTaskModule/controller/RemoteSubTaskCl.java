package com.dtask.center.remoteTaskModule.controller;

import com.dtask.center.remoteTaskModule.entity.RemoteSubTaskEntity;
import com.dtask.center.remoteTaskModule.service.IRemoteSubTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2020-5-3.
 */
@RestController
public class RemoteSubTaskCl {

    @Autowired
    IRemoteSubTask remoteSubTask;

    public String addLocalSubTask(String msg) {
        RemoteSubTaskEntity remoteSubTaskEntity = null;
        return remoteSubTask.addRemoteSubTask(remoteSubTaskEntity);
    }

    public String editLocalSubTask(String msg) {
        RemoteSubTaskEntity remoteSubTaskEntity = null;
        return remoteSubTask.editRemoteSubTask(remoteSubTaskEntity);
    }

    public String deleteLocalSubTask(String msg) {
        int[] id = null;
        return remoteSubTask.deleteRemoteSubTask(id);
    }

    public String getLocalSubTaskNumber(String msg) {
        RemoteSubTaskEntity remoteSubTaskEntity = null;
        return remoteSubTask.getRemoteSubTaskNumber(remoteSubTaskEntity);
    }

    public String getLocalSubTaskList(String msg) {
        RemoteSubTaskEntity remoteSubTaskEntity = null;
        return remoteSubTask.getRemoteSubTaskList(remoteSubTaskEntity);
    }

    public String editLocalSubTaskStatus(String msg) {
        RemoteSubTaskEntity remoteSubTaskEntity = null;
        return remoteSubTask.editRemoteSubTaskStatus(remoteSubTaskEntity);
    }
}
