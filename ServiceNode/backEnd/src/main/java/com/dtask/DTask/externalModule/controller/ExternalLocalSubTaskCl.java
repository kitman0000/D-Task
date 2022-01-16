package com.dtask.DTask.externalModule.controller;

import com.dtask.DTask.externalModule.bo.ExternalSubTaskAddBo;
import com.dtask.DTask.externalModule.service.IExternalLocalSubTask;
import com.dtask.DTask.localTaskModule.entity.LocalSubTaskEntity;
import com.dtask.DTask.localTaskModule.service.ILocalSubTask;
import com.dtask.common.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2022-1-3.
 */

@RestController
@RequestMapping("/externalApi/localSubTask")
public class ExternalLocalSubTaskCl {

    @Autowired
    private IExternalLocalSubTask externalSubTask;

    @Autowired
    private ILocalSubTask subTask;

    @Transactional
    @RequestMapping(value = "subTask",method = RequestMethod.POST)
    public ResponseData addSubTask(LocalSubTaskEntity subTaskEntity){
        subTask.addLocalSubTask(subTaskEntity);
        ExternalSubTaskAddBo externalSubTaskAddBo = externalSubTask.autoAssignSubTask();
        return new ResponseData(1,"Add success",externalSubTaskAddBo,true);
    }

    @RequestMapping(value = "localSubTaskStatus",method = RequestMethod.PUT)
    public ResponseData editLocalSubTaskStatus(LocalSubTaskEntity localSubTaskEntity){
        boolean result = subTask.editLocalSubTaskStatus(localSubTaskEntity);
        if (result){
            return new ResponseData(1,"Edit success",null,true);
        }else {
            return new ResponseData(1,"Edit failed",null,true);
        }
    }

    @RequestMapping(value = "localSubTaskAssignee",method = RequestMethod.PUT)
    public ResponseData editLocalSubTaskAssignee(LocalSubTaskEntity localSubTaskEntity){
        boolean result = subTask.editLocalSubTaskAssignee(localSubTaskEntity);
        if (result){
            return new ResponseData(1,"Edit success",null,true);
        }else {
            return new ResponseData(1,"Edit failed",null,true);
        }
    }
}
