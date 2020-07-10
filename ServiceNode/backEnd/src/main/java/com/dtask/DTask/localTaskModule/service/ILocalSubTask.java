package com.dtask.DTask.localTaskModule.service;

import com.dtask.DTask.localTaskModule.entity.LocalSubTaskEntity;
import com.dtask.common.ResponseData;

/**
 * Created by zhong on 2020-5-3.
 */
public interface ILocalSubTask {
    ResponseData addLocalSubTask(LocalSubTaskEntity localSubTaskEntity);

    ResponseData editLocalSubTask(LocalSubTaskEntity localSubTaskEntity);

    ResponseData deleteLocalSubTask(int taskID, int[] id);

    ResponseData getLocalSubTaskNumber(LocalSubTaskEntity localSubTaskEntity);

    ResponseData getLocalSubTaskList(LocalSubTaskEntity localSubTaskEntity, int page);

    ResponseData editLocalSubTaskStatus(LocalSubTaskEntity localSubTaskEntity);

}
