package com.dtask.center.remoteTaskModule.service;


import com.dtask.center.remoteTaskModule.entity.RemoteSubTaskEntity;

/**
 * Created by zhong on 2020-5-3.
 */
public interface IRemoteSubTask {
    String addRemoteSubTask(RemoteSubTaskEntity remoteSubTaskEntity);

    String editRemoteSubTask(RemoteSubTaskEntity remoteSubTaskEntity);

    String deleteRemoteSubTask(int[] id);

    String getRemoteSubTaskNumber(RemoteSubTaskEntity remoteSubTaskEntity);

    String getRemoteSubTaskList(RemoteSubTaskEntity remoteSubTaskEntity);

    String editRemoteSubTaskStatus(RemoteSubTaskEntity remoteSubTaskEntity);

}
