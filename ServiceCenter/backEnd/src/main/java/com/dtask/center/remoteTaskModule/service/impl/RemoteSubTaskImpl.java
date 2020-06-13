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
    public String addRemoteSubTask(RemoteSubTaskEntity localSubTaskEntity) {
        remoteSubTaskDao.addRemoteSubTask(localSubTaskEntity);
        return "ADD_REMOTE_SUBTASK_SUCCESS";
    }

    @Override
    public String editRemoteSubTask(RemoteSubTaskEntity localSubTaskEntity) {
        remoteSubTaskDao.updateRemoteSubTask(localSubTaskEntity);
        return "EDIT_REMOTE_SUBTASK_SUCCESS";
    }

    @Override
    public String deleteRemoteSubTask(int[] id) {
        remoteSubTaskDao.deleteRemoteSubTask(id);
        return "DELETE_REMOTE_SUBTASK_SUCCESS";
    }

    @Override
    public String getRemoteSubTaskNumber(RemoteSubTaskEntity localSubTaskEntity) {
        int count = remoteSubTaskDao.getRemoteSubTaskNumber(localSubTaskEntity.getTaskID());
        int page = PageDivideUtil.getCountOfPages(count,COUNT_ONE_PAGE);
        return String.valueOf(page);
    }

    @Override
    public String getRemoteSubTaskList(RemoteSubTaskEntity localSubTaskEntity) {
        List<RemoteSubTaskBo> remoteSubTaskBoList = remoteSubTaskDao.getRemoteSubTaskList(localSubTaskEntity.getTaskID());
        return JsonUtil.objectToJson(remoteSubTaskBoList);
    }


    @Override
    public String editRemoteSubTaskStatus(RemoteSubTaskEntity localSubTaskEntity) {

        int userID = 0;
        int subTaskID = localSubTaskEntity.getId();

        // 判断是否是管理员
        if(!remoteSubTaskDao.isUserAdmin(userID) || !remoteSubTaskDao.isAllowUserChangeState(subTaskID)){
            return "";
        }

        remoteSubTaskDao.updateRemoteSubTaskStatus(localSubTaskEntity);

        return "EDIT_REMOTE_SUBTASK_STATUS_SUCCESS";

    }


}
