package com.dtask.DTask.localTaskModule.service.impl;

import com.dtask.DTask.localTaskModule.bo.LocalTaskBo;
import com.dtask.DTask.localTaskModule.bo.LocalTaskMemberBo;
import com.dtask.DTask.localTaskModule.bo.PublicEditPermissionBo;
import com.dtask.DTask.localTaskModule.dao.LocalTaskDao;
import com.dtask.DTask.localTaskModule.entity.LocalTaskSearchEntity;
import com.dtask.DTask.localTaskModule.service.ILocalTask;
import com.dtask.common.ApplicationContextAwareCommon;
import com.dtask.common.ResponseData;
import com.dtask.common.UserCommon;
import com.dtask.common.util.PageDivideUtil;
import com.dtask.pluginsdk.localTaskModule.ILocalTaskEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zhong on 2020-5-3.
 */

@Service
public class LocalTaskImpl implements ILocalTask {
    private final int COUNT_ONE_PAGE = 16;

    @Autowired
    private LocalTaskDao localTaskDao;

    @Autowired
    private ApplicationContextAwareCommon applicationContextAware;

    @Override
    public ResponseData addLocalTask(String name) {
        int userID = UserCommon.getUserBo().getUserID();
        localTaskDao.insertLocalTask(userID,name);

        int taskID = localTaskDao.getLastCreatedTaskID();
        localTaskDao.addTaskMember(taskID,userID);
        // 将拥有者设为管理员
        localTaskDao.setLocalTaskAdmin(taskID,userID,true);

        Map<String,ILocalTask> interfaceMap = applicationContextAware.getImplementsMap(ILocalTaskEvent.class);

        if (interfaceMap != null){
            interfaceMap.forEach((K,V)->{
                V.addLocalTask(name);
            });
        }

        return new ResponseData(1,"添加成功",null);
    }

    @Override
    public ResponseData getTaskDetail(int taskID) {
        LocalTaskBo localTaskBoList = localTaskDao.getLocalTaskDetail(taskID);
        return new ResponseData(1,"查询成功",localTaskBoList);
    }



    @Override
    public ResponseData editLocalTask(int id, String name, int creator,
                                      boolean allowedMemberChangeStatus,boolean allowedMemberChangeAssignee) {
        localTaskDao.updateLocalTask(id,name,creator,allowedMemberChangeStatus,allowedMemberChangeAssignee);

        // 如果不在本任务中，会报错，先将该用户添加到任务中
        try {
            localTaskDao.checkIsAdmin(id,creator);
        }catch (Exception ex){
            localTaskDao.addTaskMember(id,creator);
        }

        localTaskDao.setLocalTaskAdmin(id,creator,true);

        Map<String,ILocalTask> interfaceMap = applicationContextAware.getImplementsMap(ILocalTaskEvent.class);

        if (interfaceMap != null){
            interfaceMap.forEach((K,V)->{
                V.editLocalTask(id,name,creator,allowedMemberChangeStatus,allowedMemberChangeAssignee);
            });
        }

        return new ResponseData(1,"修改成功",null);
    }

    @Override
    public ResponseData deleteLocalTask(int id) {
        localTaskDao.deleteLocalTask(id);

        Map<String,ILocalTask> interfaceMap = applicationContextAware.getImplementsMap(ILocalTaskEvent.class);

        if (interfaceMap != null){
            interfaceMap.forEach((K,V)->{
                V.deleteLocalTask(id);
            });
        }

        return new ResponseData(1,"删除成功",null);
    }

    @Override
    public ResponseData getLocalTaskNumber(LocalTaskSearchEntity localTaskSearchEntity) {
        int amount = localTaskDao.getLocalTaskNumber(localTaskSearchEntity);
        int page = PageDivideUtil.getCountOfPages(amount,COUNT_ONE_PAGE);

        return new ResponseData(1,"查询成功",page);
    }

    @Override
    public ResponseData getLocalTaskList(LocalTaskSearchEntity localTaskSearchEntity, int page) {
        int startRow = (page -1) * COUNT_ONE_PAGE;
        List<LocalTaskBo> localTaskBoList = localTaskDao.getLocalTaskList(localTaskSearchEntity,startRow,COUNT_ONE_PAGE);

        return new ResponseData(1,"查询成功",localTaskBoList);
    }

    @Override
    public ResponseData getUserTaskNumber(LocalTaskSearchEntity localTaskSearchEntity) {
        int userID = UserCommon.getUserBo().getUserID();

        int amount = localTaskDao.getUserLocalTaskNumber(localTaskSearchEntity,userID);
        int page = PageDivideUtil.getCountOfPages(amount,COUNT_ONE_PAGE);

        return new ResponseData(1,"查询成功",page);
    }

    @Override
    public ResponseData getUserTaskList(LocalTaskSearchEntity localTaskSearchEntity, int page) {
        int userID = UserCommon.getUserBo().getUserID();

        int startRow = (page -1) * COUNT_ONE_PAGE;
        List<LocalTaskBo> localTaskBoList = localTaskDao.getUserLocalTaskList(localTaskSearchEntity,userID,startRow,COUNT_ONE_PAGE);

        return new ResponseData(1,"查询成功",localTaskBoList);
    }

    @Override
    public ResponseData addLocalTaskMember(int taskID, int userID) {
        int opUserID = UserCommon.getUserBo().getUserID();

        if(!checkUserHasPermission(taskID,opUserID)){
            return new ResponseData(2,"权限不足",null);
        }

        if(localTaskDao.checkTaskMemberExist(taskID,userID) >= 1){
            return new ResponseData(2,"用户已存在该任务中",null);
        }

        localTaskDao.addTaskMember(taskID,userID);

        Map<String,ILocalTask> interfaceMap = applicationContextAware.getImplementsMap(ILocalTaskEvent.class);

        if (interfaceMap != null){
            interfaceMap.forEach((K,V)->{
                V.addLocalTaskMember(taskID,userID);
            });
        }

        return new ResponseData(1,"添加成功",null);
    }

    @Override
    public ResponseData removeLocalTaskMember(int taskID, int userID) {
        int opUserID = UserCommon.getUserBo().getUserID();

        if(!checkUserHasPermission(taskID,opUserID)){
            return new ResponseData(2,"权限不足",null);
        }

        if(localTaskDao.checkTaskMemberExist(taskID,userID) == 0){
            return new ResponseData(2,"用户不存在该任务中",null);
        }

        if(localTaskDao.getTaskCreator(taskID) == userID){
            return new ResponseData(2,"不可以删除任务拥有者",null);
        }

        localTaskDao.removeTaskMember(taskID,userID);

        Map<String,ILocalTask> interfaceMap = applicationContextAware.getImplementsMap(ILocalTaskEvent.class);

        if (interfaceMap != null){
            interfaceMap.forEach((K,V)->{
                V.removeLocalTaskMember(taskID,userID);
            });
        }

        return new ResponseData(1,"删除成功",null);

    }

    @Override
    public ResponseData getLocalTaskMember(int taskID) {
        List<LocalTaskMemberBo> memberBoList = localTaskDao.getTaskUser(taskID);
        return new ResponseData(1,"查询成功",memberBoList);
    }

    /**
     * 切换是否是管理员
     * 本操作只有任务所有者拥有权限
     */
    @Override
    public ResponseData toggleTaskAdmin(int taskID, int userID, boolean isAdmin) {
        int opUserID = UserCommon.getUserBo().getUserID();
        int creator = localTaskDao.getTaskCreator(taskID);

        if(creator != opUserID){
            return new ResponseData(2,"权限不足",null);
        }

        if(localTaskDao.getTaskCreator(taskID) == userID){
            return new ResponseData(2,"任务所有者不可被切换",null);
        }

        localTaskDao.setLocalTaskAdmin(taskID,userID,isAdmin);

        Map<String,ILocalTask> interfaceMap = applicationContextAware.getImplementsMap(ILocalTaskEvent.class);

        if (interfaceMap != null){
            interfaceMap.forEach((K,V)->{
                V.toggleTaskAdmin(taskID,userID,isAdmin);
            });
        }

        return new ResponseData(1,"修改成功",null);
    }

    /**
     * 获取用户在一个任务中的角色
     * @param taskID 任务ID
     * @return 1：拥有者 2：管理员 2：参与者
     */
    @Override
    public ResponseData getTaskUserRole(int taskID) {
        int userID = UserCommon.getUserBo().getUserID();

        if(localTaskDao.getTaskCreator(taskID) == userID){
            // 任务拥有者
            return new ResponseData(1,"查询成功",1);
        }
        if(checkUserHasPermission(taskID,userID)){
            // 任务管理员
            return new ResponseData(1,"查询成功",2);
        }

        // 任务参与者
        return new ResponseData(1,"查询成功",3);
    }

    /**
     * 判断是否允许用户修改子任务状态
     */
    @Override
    public PublicEditPermissionBo getPublicEditPermission(int taskID) {
        boolean isAllowUserChangeStatus = localTaskDao.getAllowUserChangeStatus(taskID);
        boolean isAllowUserChangeAssignee = localTaskDao.getAllowUserChangeAssignee(taskID);

        return new PublicEditPermissionBo(){
            {
                setAllowChangeStatus(isAllowUserChangeStatus);
                setAllowChangeAssignee(isAllowUserChangeAssignee);
            }
        };
    }

    /**
     * 判断用户是否拥有管理权限
     */
    private boolean checkUserHasPermission(int taskID,int userID){
        // 一般情况下任务所有者和管理员都拥有管理权限，但防止后期变化，特此列出
        try {
            return localTaskDao.checkIsAdmin(taskID, userID);
        } catch (Exception ex){
            // 如果用户不在该任务中，会返回null并抛出异常
            return false;
        }
    }
}
