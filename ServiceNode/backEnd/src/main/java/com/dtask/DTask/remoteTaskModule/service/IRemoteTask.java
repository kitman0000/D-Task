package com.dtask.DTask.remoteTaskModule.service;

import com.dtask.DTask.remoteTaskModule.entity.RemoteTaskSearchEntity;
import com.dtask.common.ResponseData;

/**
 * Created by zhong on 2020-7-2.
 */
public interface IRemoteTask {
    ResponseData addRemoteTask(String name);

    ResponseData editRemoteTask(int id, String name, int creator, boolean allowedMemberChangeStatus, int creatorNodeID);

    ResponseData deleteRemoteTask(int id);

    ResponseData getRemoteTaskNumber(RemoteTaskSearchEntity remoteTaskSearchEntity);

    ResponseData getRemoteTaskList(RemoteTaskSearchEntity remoteTaskSearchEntity,int page);

    ResponseData addRemoteTaskMember(int taskID, int newUserID, int newUserNodeID);

    ResponseData removeRemoteTaskMember(int taskID, int removeUserID, int removeUserNodeID);

    ResponseData getRemoteTaskMember(int taskID);

    ResponseData toggleTaskAdmin(int taskID, int toggleUserID, int toggleUserNodeID, boolean isAdmin);

    ResponseData getUserTaskNumber(RemoteTaskSearchEntity remoteTaskSearchEntity);

    ResponseData getUserTaskList(RemoteTaskSearchEntity remoteTaskSearchEntity,int page);

    ResponseData getAllowUserChangeStatus(int taskID);

}
