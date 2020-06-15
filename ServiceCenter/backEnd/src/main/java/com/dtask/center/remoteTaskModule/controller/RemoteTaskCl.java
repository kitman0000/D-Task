package com.dtask.center.remoteTaskModule.controller;

import com.dtask.center.remoteTaskModule.entity.*;
import com.dtask.center.remoteTaskModule.service.IRemoteTask;
import com.dtask.common.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhong on 2020-5-3.
 */
public class RemoteTaskCl {

    @Autowired
    IRemoteTask remoteTask;

    public String addRemoteTask(String msg){
        AddRemoteTaskEntity addRemoteTaskEntity = (AddRemoteTaskEntity) JsonUtil.jsonToObject(msg,AddRemoteTaskEntity.class);
        return remoteTask.addRemoteTask(addRemoteTaskEntity);
    }`

    public String editRemoteTask(String msg){
        EditRemoteTaskEntity editRemoteTaskEntity = (EditRemoteTaskEntity) JsonUtil.jsonToObject(msg,EditRemoteTaskEntity.class);

        return remoteTask.editRemoteTask(editRemoteTaskEntity.getId(), editRemoteTaskEntity.getName(),
                editRemoteTaskEntity.getCreator(), editRemoteTaskEntity.isAllowedMemberChangeStatus());
    }

    public String deleteRemoteTask(String msg){
        DeleteRemoteTaskEntity deleteRemoteTaskEntity = (DeleteRemoteTaskEntity) JsonUtil.jsonToObject(msg,DeleteRemoteTaskEntity.class);
        return remoteTask.deleteRemoteTask(deleteRemoteTaskEntity.getId());
    }

    public String getRemoteTaskNumber(String msg){
        RemoteTaskSearchEntity remoteTaskSearchEntity = (RemoteTaskSearchEntity) JsonUtil.jsonToObject(msg,RemoteTaskSearchEntity.class);
        return remoteTask.getRemoteTaskNumber(remoteTaskSearchEntity);
    }

    public String getRemoteTaskList(String msg){
        GetRemoteTaskListEntity getRemoteTaskListEntity = (GetRemoteTaskListEntity)JsonUtil.jsonToObject(msg,GetRemoteTaskListEntity.class);
        return remoteTask.getRemoteTaskList(getRemoteTaskListEntity.getRemoteTaskSearchEntity(), getRemoteTaskListEntity.getPage());
    }

    public String addRemoteTaskMember(String msg){
        AddRemoteTaskMemberEntity addRemoteTaskMemberEntity = (AddRemoteTaskMemberEntity) JsonUtil.jsonToObject(msg,AddRemoteTaskMemberEntity.class);
        return remoteTask.addRemoteTaskMember(addRemoteTaskMemberEntity.getTaskID(),addRemoteTaskMemberEntity.getUserID());
    }

    public String removeRemoteTaskMember(String msg){
        RemoveRemoteTaskMemberEntity removeRemoteTaskMemberEntity = (RemoveRemoteTaskMemberEntity) JsonUtil.jsonToObject(msg,RemoveRemoteTaskMemberEntity.class);
        return remoteTask.removeRemoteTaskMember(removeRemoteTaskMemberEntity.getTaskID(),removeRemoteTaskMemberEntity.getUserID());
    }

    public String getLocalTaskMember(String msg){
        GetRemoteTaskMemberEntity getRemoteTaskMemberEntity = (GetRemoteTaskMemberEntity) JsonUtil.jsonToObject(msg,GetRemoteTaskMemberEntity.class);
        return remoteTask.getRemoteTaskMember(getRemoteTaskMemberEntity.getTaskID());
    }

    public String toggleTaskAdmin(String msg){
        ToggleTaskAdminEntity toggleTaskAdminEntity = (ToggleTaskAdminEntity) JsonUtil.jsonToObject(msg,ToggleTaskAdminEntity.class);
        return remoteTask.toggleTaskAdmin(toggleTaskAdminEntity.getTaskID(),toggleTaskAdminEntity.getUserID(),toggleTaskAdminEntity.isAdmin());
    }
}
