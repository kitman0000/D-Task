package com.dtask.DTask.localTaskModule.controller;

import com.dtask.DTask.localTaskModule.bo.LocalSubTaskBo;
import com.dtask.DTask.localTaskModule.entity.LocalSubTaskEntity;
import com.dtask.DTask.localTaskModule.service.ILocalSubTask;
import com.dtask.common.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zhong on 2020-5-3.
 */
@RestController
public class LocalSubTaskCl{

    @Autowired
    ILocalSubTask localSubTask;

    @RequestMapping(value = "/api/localTask/localSubTask",method = RequestMethod.POST)
    public ResponseData addLocalSubTask(LocalSubTaskEntity localSubTaskEntity) {
        boolean result = localSubTask.addLocalSubTaskByUser(localSubTaskEntity);
        if (result){
            return new ResponseData(1,"添加成功",null);
        }else {
            return new ResponseData(0,"无权限/添加失败",null);
        }
    }

    @RequestMapping(value = "/api/localTask/localSubTask",method = RequestMethod.PUT)
    public ResponseData editLocalSubTask(LocalSubTaskEntity localSubTaskEntity) {
        boolean result =  localSubTask.editLocalSubTask(localSubTaskEntity);
        if (result){
            return new ResponseData(1,"修改成功",null);
        }else {
            return new ResponseData(0,"无权限/修改失败",null);
        }
    }

    @RequestMapping(value = "/api/localTask/localSubTask",method = RequestMethod.DELETE)
    public ResponseData deleteLocalSubTask(int taskID,int[] id) {
        boolean result =  localSubTask.deleteLocalSubTask(taskID, id);
        if (result){
            return new ResponseData(1,"删除成功",null);
        }else {
            return new ResponseData(0,"无权限/删除失败",null);
        }
    }

    @RequestMapping(value = "/api/localTask/localSubTaskNumber",method = RequestMethod.GET)
    public ResponseData getLocalSubTaskNumber(LocalSubTaskEntity localSubTaskEntity) {
        int result = localSubTask.getLocalSubTaskNumber(localSubTaskEntity);
        return new ResponseData(1,"查询成功",result);
    }

    @RequestMapping(value = "/api/localTask/localSubTaskList",method = RequestMethod.GET)
    public ResponseData getLocalSubTaskList(LocalSubTaskEntity localSubTaskEntity,int page) {
        List<LocalSubTaskBo> result = localSubTask.getLocalSubTaskList(localSubTaskEntity, page);
        return new ResponseData(1,"查询成功",result);
    }

    @RequestMapping(value = "/api/localTask/localSubTaskStatus",method = RequestMethod.PUT)
    public ResponseData editLocalSubTaskStatus(LocalSubTaskEntity localSubTaskEntity) {
        boolean result =  localSubTask.editLocalSubTaskStatusByUser(localSubTaskEntity);
        if (result){
            return new ResponseData(1,"修改成功",null);
        }else {
            return new ResponseData(0,"无权限/修改失败",null);
        }
    }

    /**
     * Update sub task's assignee, use 0 for no one
     */
    @RequestMapping(value = "/api/localTask/localSubTaskAssignee",method = RequestMethod.PUT)
    public ResponseData editLocalSubTaskAssignee(LocalSubTaskEntity localSubTaskEntity){
        boolean result =  localSubTask.editLocalSubTaskAssigneeByUser(localSubTaskEntity);
        if (result){
            return new ResponseData(1,"修改成功",null);
        }else {
            return new ResponseData(0,"无权限/修改失败",null);
        }
    }
}
