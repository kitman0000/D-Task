package com.dtask.center.remoteTaskModule.service.impl;

import com.dtask.center.remoteTaskModule.bo.RemoteSubTaskBo;
import com.dtask.center.remoteTaskModule.dao.RemoteSubTaskDao;
import com.dtask.center.remoteTaskModule.entity.RemoteSubTaskEntity;
import com.dtask.center.remoteTaskModule.service.IRemoteSubTask;
import com.dtask.common.util.JsonUtil;
import com.dtask.common.util.PageDivideUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhong on 2020-5-3.
 */
@Service
public class RemoteSubTaskImpl implements IRemoteSubTask{

    private final int COUNT_ONE_PAGE = 10;

    @Autowired
    RemoteSubTaskDao remoteSubTaskDao;

    @Override
    public String addRemoteSubTask(RemoteSubTaskEntity remoteSubTaskEntity) {
        remoteSubTaskDao.addRemoteSubTask(remoteSubTaskEntity);
        return "ADD_REMOTE_SUBTASK_SUCCESS";
    }

    @Override
    public String editRemoteSubTask(RemoteSubTaskEntity remoteSubTaskEntity) {
        remoteSubTaskDao.updateRemoteSubTask(remoteSubTaskEntity);
        return "EDIT_REMOTE_SUBTASK_SUCCESS";
    }

    @Override
    public String deleteRemoteSubTask(int[] id) {
        remoteSubTaskDao.deleteRemoteSubTask(id);
        return "DELETE_REMOTE_SUBTASK_SUCCESS";
    }

    @Override
    public String getRemoteSubTaskNumber(RemoteSubTaskEntity remoteSubTaskEntity) {
        int count = remoteSubTaskDao.getRemoteSubTaskNumber(remoteSubTaskEntity.getTaskID());
        int page = PageDivideUtil.getCountOfPages(count,COUNT_ONE_PAGE);
        return String.valueOf(page);
    }

    @Override
    public String getRemoteSubTaskList(RemoteSubTaskEntity remoteSubTaskEntity) {
        int startRow = (remoteSubTaskEntity.getPage() -1) * COUNT_ONE_PAGE;

        List<RemoteSubTaskBo> remoteSubTaskBoList = remoteSubTaskDao.getRemoteSubTaskList(remoteSubTaskEntity.getTaskID(),startRow,COUNT_ONE_PAGE);
        return JsonUtil.objectToJson(remoteSubTaskBoList);
    }


    @Override
    public String editRemoteSubTaskStatus(RemoteSubTaskEntity remoteSubTaskEntity) {

        int subTaskID = remoteSubTaskEntity.getId();
        int taskID = remoteSubTaskEntity.getTaskID();

        int userID = remoteSubTaskEntity.getUserID();
        int nodeID = remoteSubTaskEntity.getNodeID();

        // 判断是否是管理员或者有权限
        if(!remoteSubTaskDao.isUserAdmin(userID,nodeID,taskID) || !remoteSubTaskDao.isAllowUserChangeState(subTaskID)){
            return "";
        }

        remoteSubTaskDao.updateRemoteSubTaskStatus(remoteSubTaskEntity);

        return "EDIT_REMOTE_SUBTASK_STATUS_SUCCESS";

    }

    private boolean checkIsAdmin(int taskID,int userID,int nodeID){
        try {
            return remoteSubTaskDao.isUserAdmin(taskID,userID,taskID);
        }
        catch (Exception ex) {
            return false;
        }
    }


}
