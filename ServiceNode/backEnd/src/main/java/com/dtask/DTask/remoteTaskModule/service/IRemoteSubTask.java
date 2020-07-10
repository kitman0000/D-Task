package com.dtask.DTask.remoteTaskModule.service;

import com.dtask.DTask.remoteTaskModule.entity.RemoteSubTaskEntity;
import com.dtask.common.ResponseData;

/**
 * Created by zhong on 2020-7-5.
 */
public interface IRemoteSubTask {
    ResponseData addRemoteSubTask(RemoteSubTaskEntity remoteSubTaskEntity);

    ResponseData editRemoteSubTask(RemoteSubTaskEntity remoteSubTaskEntity);

    ResponseData deleteRemoteSubTask(int[] id);

    ResponseData getRemoteSubTaskNumber(RemoteSubTaskEntity remoteSubTaskEntity);

    ResponseData getRemoteSubTaskList(RemoteSubTaskEntity remoteSubTaskEntity);

    ResponseData editRemoteSubTaskStatus(RemoteSubTaskEntity remoteSubTaskEntity);
}
