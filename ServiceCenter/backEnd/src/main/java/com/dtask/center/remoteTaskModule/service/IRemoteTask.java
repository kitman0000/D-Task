package com.dtask.center.remoteTaskModule.service;


import com.dtask.center.remoteTaskModule.entity.*;

/**
 * Created by zhong on 2020-5-3.
 */
public interface IRemoteTask {
    String addRemoteTask(AddRemoteTaskEntity addRemoteTaskEntity);

    String editRemoteTask(EditRemoteTaskEntity editRemoteTaskEntity);

    String deleteRemoteTask(DeleteRemoteTaskEntity deleteRemoteTaskEntity);

    String getRemoteTaskNumber(RemoteTaskSearchEntity remoteTaskSearchEntity);

    String getRemoteTaskList(RemoteTaskSearchEntity remoteTaskSearchEntity, int page);

    String addRemoteTaskMember(AddRemoteTaskMemberEntity addRemoteTaskMemberEntity);

    String removeRemoteTaskMember(RemoveRemoteTaskMemberEntity removeRemoteTaskMemberEntity);

    String getRemoteTaskMember(GetRemoteTaskMemberEntity getRemoteTaskMemberEntity);

    String toggleTaskAdmin(ToggleTaskAdminEntity toggleTaskAdminEntity);

}