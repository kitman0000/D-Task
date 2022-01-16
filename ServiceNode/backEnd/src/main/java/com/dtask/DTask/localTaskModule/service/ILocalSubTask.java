package com.dtask.DTask.localTaskModule.service;

import com.dtask.DTask.localTaskModule.bo.LocalSubTaskBo;
import com.dtask.DTask.localTaskModule.entity.LocalSubTaskEntity;
import com.dtask.DTask.localTaskModule.entity.LocalSubTaskFilterEntity;
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

    int getLocalSubTaskNumberWithFilter(LocalSubTaskEntity localSubTaskEntity, LocalSubTaskFilterEntity filterEntity);

    List<LocalSubTaskBo> getLocalSubTaskList(LocalSubTaskEntity localSubTaskEntity, int page);

    List<LocalSubTaskBo> getLocalSubTaskListWithFilter(LocalSubTaskEntity localSubTaskEntity, int page, LocalSubTaskFilterEntity filterEntity);

    boolean editLocalSubTaskStatusByUser(LocalSubTaskEntity localSubTaskEntity);

    boolean editLocalSubTaskStatus(LocalSubTaskEntity localSubTaskEntity);

    boolean editLocalSubTaskAssigneeByUser(LocalSubTaskEntity localSubTaskEntity);

    boolean editLocalSubTaskAssignee(LocalSubTaskEntity localSubTaskEntity);

}
