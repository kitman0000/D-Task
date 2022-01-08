package com.dtask.DTask.externalModule.service;

import com.dtask.DTask.externalModule.bo.ExternalSubTaskAddBo;
import com.dtask.DTask.localTaskModule.bo.LocalSubTaskBo;

/**
 * Created by zhong on 2022-1-3.
 */
public interface IExternalLocalSubTask {
    ExternalSubTaskAddBo autoAssignSubTask();

    ExternalSubTaskAddBo autoAssignSubTask(LocalSubTaskBo localSubTaskBo);
}
