package com.dtask.DTask.localTaskModule.controller;

import com.dtask.DTask.localTaskModule.entity.LocalSubTaskEntity;
import com.dtask.DTask.localTaskModule.service.ILocalSubTask;
import com.dtask.common.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2020-5-3.
 */
@RestController
public class LocalSubTaskCl{

    @Autowired
    ILocalSubTask localSubTask;

    @RequestMapping(value = "/api/localTask/localSubTask",method = RequestMethod.POST)
    public ResponseData addLocalSubTask(LocalSubTaskEntity localSubTaskEntity) {
        return localSubTask.addLocalSubTask(localSubTaskEntity);
    }

    @RequestMapping(value = "/api/localTask/localSubTask",method = RequestMethod.PUT)
    public ResponseData editLocalSubTask(LocalSubTaskEntity localSubTaskEntity) {
        return localSubTask.editLocalSubTask(localSubTaskEntity);
    }

    @RequestMapping(value = "/api/localTask/localSubTask",method = RequestMethod.DELETE)
    public ResponseData deleteLocalSubTask(int[] id) {
        return localSubTask.deleteLocalSubTask(id);
    }

    @RequestMapping(value = "/api/localTask/localSubTaskNumber",method = RequestMethod.GET)
    public ResponseData getLocalSubTaskNumber(LocalSubTaskEntity localSubTaskEntity) {
        return localSubTask.getLocalSubTaskNumber(localSubTaskEntity);
    }

    @RequestMapping(value = "/api/localTask/localSubTaskList",method = RequestMethod.GET)
    public ResponseData getLocalSubTaskList(LocalSubTaskEntity localSubTaskEntity) {
        return localSubTask.getLocalSubTaskList(localSubTaskEntity);
    }

    @RequestMapping(value = "/api/localTask/localSubTaskStatus",method = RequestMethod.PUT)
    public ResponseData editLocalSubTaskStatus(LocalSubTaskEntity localSubTaskEntity) {
        return localSubTask.editLocalSubTaskStatus(localSubTaskEntity);
    }
}
