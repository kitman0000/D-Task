package com.dtask.DTask.localTaskModule.controller;

import com.dtask.DTask.localTaskModule.bo.PublicEditPermissionBo;
import com.dtask.DTask.localTaskModule.entity.LocalTaskSearchEntity;
import com.dtask.DTask.localTaskModule.service.ILocalTask;
import com.dtask.common.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2020-5-3.
 */
@RestController
public class LocalTaskCl {

    @Autowired
    ILocalTask localTask;

    @RequestMapping(value = "/api/localTask/localTask",method = RequestMethod.POST)
    public ResponseData addLocalTask(String name){
        return localTask.addLocalTask(name);
    }

    @RequestMapping(value = "/api/localTask/localTaskDetail",method = RequestMethod.GET)
    public ResponseData getTaskDetail(int taskID){
        return localTask.getTaskDetail(taskID);
    }

    @RequestMapping(value = "/api/localTask/localTask",method = RequestMethod.PUT)
    public ResponseData editLocalTask(int id,
                                      String name,
                                      int creator,
                                      boolean allowedMemberChangeStatus,
                                      boolean allowedMemberChangeAssignee){
        return localTask.editLocalTask(id, name, creator, allowedMemberChangeStatus,allowedMemberChangeAssignee);
    }

    @RequestMapping(value = "/api/localTask/localTask",method = RequestMethod.DELETE)
    public ResponseData deleteLocalTask(int id){
        return localTask.deleteLocalTask(id);
    }

    @RequestMapping(value = "/api/localTask/localTaskPage",method = RequestMethod.GET)
    public ResponseData getLocalTaskNumber(LocalTaskSearchEntity localTaskSearchEntity){
        return localTask.getLocalTaskNumber(localTaskSearchEntity);
    }

    @RequestMapping(value = "/api/localTask/localTaskList",method = RequestMethod.GET)
    public ResponseData getLocalTaskList(LocalTaskSearchEntity localTaskSearchEntity,int page){
        return localTask.getLocalTaskList(localTaskSearchEntity,page);
    }

    @RequestMapping(value = "/api/localTask/UserLocalTaskPage",method = RequestMethod.GET)
    public ResponseData getUserLocalTaskNumber(LocalTaskSearchEntity localTaskSearchEntity){
        return localTask.getUserTaskNumber(localTaskSearchEntity);
    }

    @RequestMapping(value = "/api/localTask/UserLocalTaskList",method = RequestMethod.GET)
    public ResponseData getUserLocalTaskList(LocalTaskSearchEntity localTaskSearchEntity,int page){
        return localTask.getUserTaskList(localTaskSearchEntity,page);
    }

    @RequestMapping(value = "/api/localTask/LocalTaskMember",method = RequestMethod.POST)
    public ResponseData addLocalTaskMember(int taskID,int userID){
        return localTask.addLocalTaskMember(taskID,userID);
    }

    @RequestMapping(value = "/api/localTask/LocalTaskMember",method = RequestMethod.DELETE)
    public ResponseData removeLocalTaskMember(int taskID,int userID){
        return localTask.removeLocalTaskMember(taskID,userID);
    }

    @RequestMapping(value = "/api/localTask/LocalTaskMember",method = RequestMethod.GET)
    public ResponseData getLocalTaskMember(int taskID){
        return localTask.getLocalTaskMember(taskID);
    }

    @RequestMapping(value = "/api/localTask/Admin",method = RequestMethod.PUT)
    public ResponseData toggleTaskAdmin(int taskID,int userID,boolean isAdmin){
        return localTask.toggleTaskAdmin(taskID,userID,isAdmin);
    }

    @RequestMapping(value = "/api/localTask/userRole",method = RequestMethod.GET)
    public ResponseData getTaskUserRole(int taskID){
        return localTask.getTaskUserRole(taskID);
    }

    @RequestMapping(value = "/api/localTask/publicEditPermission",method = RequestMethod.GET)
    public ResponseData getPublicEditPermission(int taskID){
        return new ResponseData(1,"查询成功", localTask.getPublicEditPermission(taskID));
    }

    @RequestMapping(value = "/api/localTask/IsAutoAssign",method = RequestMethod.PUT)
    public ResponseData editTaskUserIsAutoAssign(int taskID,int userID,boolean isAutoAssign){
        boolean result = localTask.editTaskUserIsAutoAssign(taskID,userID,isAutoAssign);
        return result ?  new ResponseData(1,"修改成功",null):new ResponseData(2,"权限错误",null);
    }


}
