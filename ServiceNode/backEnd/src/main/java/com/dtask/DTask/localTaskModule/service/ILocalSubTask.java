package com.dtask.DTask.localTaskModule.service;

import com.dtask.DTask.localTaskModule.bo.LocalSubTaskBo;
import com.dtask.DTask.localTaskModule.entity.LocalSubTaskEntity;
import com.dtask.common.ResponseData;

import java.util.List;

/**
 * Created by zhong on 2020-5-3.
 */
public interface ILocalSubTask {
    boolean addLocalSubTask(LocalSubTaskEntity localSubTaskEntity);

    boolean addLocalSubTaskByUser(LocalSubTaskEntity localSubTaskEntity);

    boolean editLocalSubTask(LocalSubTaskEntity localSubTaskEntity);

    boolean deleteLocalSubTask(int taskID, int[] id);

    int getLocalSubTaskNumber(LocalSubTaskEntity localSubTaskEntity);

    List<LocalSubTaskBo> getLocalSubTaskList(LocalSubTaskEntity localSubTaskEntity, int page);

    boolean editLocalSubTaskStatus(LocalSubTaskEntity localSubTaskEntity);

    boolean editLocalSubTaskAssignee(LocalSubTaskEntity localSubTaskEntity);

}
