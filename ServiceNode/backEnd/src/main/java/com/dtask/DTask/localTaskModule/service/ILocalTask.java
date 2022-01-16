package com.dtask.DTask.localTaskModule.service;

import com.dtask.DTask.localTaskModule.bo.PublicEditPermissionBo;
import com.dtask.DTask.localTaskModule.entity.LocalTaskSearchEntity;
import com.dtask.common.ResponseData;
import com.sun.org.apache.regexp.internal.RE;

/**
 * Created by zhong on 2020-5-3.
 */
public interface ILocalTask {
    ResponseData addLocalTask(String name);

    ResponseData getTaskDetail(int taskID);

    ResponseData editLocalTask(int id,
                               String name,
                               int creator,
                               boolean allowedMemberChangeStatus,
                               boolean allowedMemberChangeAssignee);

    ResponseData deleteLocalTask(int id);

    ResponseData getLocalTaskNumber(LocalTaskSearchEntity localTaskSearchEntity);

    ResponseData getLocalTaskList(LocalTaskSearchEntity localTaskSearchEntity,int page);

    ResponseData addLocalTaskMember(int taskID,int userID);

    ResponseData removeLocalTaskMember(int taskID,int userID);

    ResponseData getLocalTaskMember(int taskID);

    ResponseData toggleTaskAdmin(int taskID,int userID,boolean isAdmin);

    ResponseData getUserTaskNumber(LocalTaskSearchEntity localTaskSearchEntity);

    ResponseData getUserTaskList(LocalTaskSearchEntity localTaskSearchEntity,int page);

    ResponseData getTaskUserRole(int taskID);

    PublicEditPermissionBo getPublicEditPermission(int taskID);

    boolean editTaskUserIsAutoAssign(int taskID,int userID,boolean isAutoAssign);
}
