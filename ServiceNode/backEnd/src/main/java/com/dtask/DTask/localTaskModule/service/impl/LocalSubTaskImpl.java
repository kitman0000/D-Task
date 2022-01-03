package com.dtask.DTask.localTaskModule.service.impl;

import com.dtask.DTask.localTaskModule.bo.LocalSubTaskBo;
import com.dtask.DTask.localTaskModule.dao.LocalSubTaskDao;
import com.dtask.DTask.localTaskModule.entity.LocalSubTaskEntity;
import com.dtask.DTask.localTaskModule.service.ILocalSubTask;
import com.dtask.common.ApplicationContextAwareCommon;
import com.dtask.common.ResponseData;
import com.dtask.common.UserCommon;
import com.dtask.common.config.ShiroConfig;
import com.dtask.common.util.PageDivideUtil;
import com.dtask.pluginsdk.localTaskModule.ILocalSubTaskEvent;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhong on 2020-5-3.
 */
@Service
public class LocalSubTaskImpl implements ILocalSubTask{

    private final int COUNT_ONE_PAGE = 20;

    @Autowired
    private LocalSubTaskDao localSubTaskDao;

    @Autowired
    private ApplicationContextAwareCommon applicationContextAware;

    @Override
    public boolean addLocalSubTask(LocalSubTaskEntity localSubTaskEntity) {
        if(!checkIsAdmin(localSubTaskEntity.getTaskID())){
            return false;
        }

        localSubTaskDao.addLocalSubTask(localSubTaskEntity);

        Map<String,ILocalSubTaskEvent> interfaceMap = applicationContextAware.getImplementsMap(ILocalSubTaskEvent.class);

        if (interfaceMap != null){
            interfaceMap.forEach((K,V)->{
                V.addLocalSubTask(localSubTaskEntity.getTaskID(),localSubTaskEntity.getName(),
                        localSubTaskEntity.getContent(),localSubTaskEntity.getDeadline(),
                        localSubTaskEntity.getStatus(),localSubTaskEntity.getStartTime(),
                        localSubTaskEntity.getLevel(),localSubTaskEntity.getTag(),localSubTaskEntity.getStar());
            });
        }

        return true;
    }

    @Override
    public boolean editLocalSubTask(LocalSubTaskEntity localSubTaskEntity) {

        // 检查所申请操作的subTaskID是否属于taskID
        int[] subTaskID = {localSubTaskEntity.getId()};
        if(!checkSubTask(localSubTaskEntity.getTaskID(),subTaskID)){
            return false;
        }

        if(!checkIsAdmin(localSubTaskEntity.getTaskID())){
            return false;
        }

        Map<String,ILocalSubTaskEvent> interfaceMap = applicationContextAware.getImplementsMap(ILocalSubTaskEvent.class);

        if (interfaceMap != null){
            interfaceMap.forEach((K,V)->{
                V.editLocalSubTask(localSubTaskEntity.getTaskID(),localSubTaskEntity.getTaskID(),localSubTaskEntity.getName(),
                        localSubTaskEntity.getContent(),localSubTaskEntity.getDeadline(),
                        localSubTaskEntity.getStatus(),localSubTaskEntity.getStartTime(),
                        localSubTaskEntity.getLevel(),localSubTaskEntity.getTag(),localSubTaskEntity.getStar());
            });
        }

        localSubTaskDao.updateLocalSubTask(localSubTaskEntity);
        return true;
    }

    @Override
    public boolean deleteLocalSubTask(int taskID, int[] id) {
        // 检查所申请操作的subTaskID是否属于taskID
        if(!checkSubTask(taskID,id)){
            return false;
        }
        if(!checkIsAdmin(taskID)){
            return false;
        }

        localSubTaskDao.deleteLocalSubTask(id);

        Map<String,ILocalSubTaskEvent> interfaceMap = applicationContextAware.getImplementsMap(ILocalSubTaskEvent.class);

        if (interfaceMap != null){
            interfaceMap.forEach((K,V)->{
                V.deleteLocalSubTask(taskID,id);
            });
        }

        return true;
    }

    @Override
    public int getLocalSubTaskNumber(LocalSubTaskEntity localSubTaskEntity) {
        int count = localSubTaskDao.getLocalSubTaskNumber(localSubTaskEntity.getTaskID());
        int page = PageDivideUtil.getCountOfPages(count,COUNT_ONE_PAGE);
        return page;
    }

    @Override
    public List<LocalSubTaskBo> getLocalSubTaskList(LocalSubTaskEntity localSubTaskEntity, int page) {

        // 判断用户是否属于此任务
        if (!SecurityUtils.getSubject().isPermitted("task:localTaskMange:manage")){
            // 如果用户没有这个权限，则用户不是网站管理员
            int userID = UserCommon.getUserBo().getUserID();
            // 如果用户不属于此任务，获取管理员时会报错，则认为是非法请求
            try {
                localSubTaskDao.isUserAdmin(userID,localSubTaskEntity.getTaskID());
            }catch (Exception ex){
                return null;
            }
        }

        int startRow = (page -1) * COUNT_ONE_PAGE;
        List<LocalSubTaskBo> localSubTaskBoList = localSubTaskDao.getLocalSubTaskList(localSubTaskEntity.getTaskID(),startRow,COUNT_ONE_PAGE);

        // 根据Deadline，调整星级
        // 5天以内自动变为重要 2天以内自动变为特别重要
        final long FIVE_DAY = 5*24*60*60*1000;
        final long TWO_DAY = 2*24*60*60*1000;
        final int IMPORTANT = 2;
        final int VERY_IMPORTANT = 3;
        final int FINISH = 2;
        final int CANCEL = 3;

        for(LocalSubTaskBo localSubTaskBo:localSubTaskBoList){

            // 如果任务已经完成或取消，不变化星级
            if(localSubTaskBo.getStatus() == FINISH || localSubTaskBo.getStatus() == CANCEL){
                continue;
            }

            Date deadline = localSubTaskBo.getDeadline();
            Date today = new Date();

            long timeLeft = deadline.getTime() - today.getTime();

            if(timeLeft < FIVE_DAY && localSubTaskBo.getStar()!= VERY_IMPORTANT){
                localSubTaskBo.setStar(IMPORTANT);
            }
            if(timeLeft < TWO_DAY){
                localSubTaskBo.setStar(VERY_IMPORTANT);
            }
        }

        return localSubTaskBoList;
    }


    @Override
    public boolean editLocalSubTaskStatus(LocalSubTaskEntity localSubTaskEntity) {
        // 检查所申请操作的subTaskID是否属于taskID
        int[] subTaskIDArray = {localSubTaskEntity.getId()};
        if(!checkSubTask(localSubTaskEntity.getTaskID(),subTaskIDArray)){
            return false;
        }

        int userID = UserCommon.getUserBo().getUserID();
        int subTaskID = localSubTaskEntity.getId();
        int taskID = localSubTaskEntity.getTaskID();

        // 判断是否是管理员，或者管理员允许修改SubTask状态
        try {
            if(!localSubTaskDao.isUserAdmin(userID,localSubTaskEntity.getTaskID()) && !localSubTaskDao.isAllowUserChangeState(taskID)){
                return false;
            }
        } catch (Exception ex){
            return false;
        }

        localSubTaskDao.updateLocalSubTaskStatus(localSubTaskEntity);


        Map<String,ILocalSubTaskEvent> interfaceMap = applicationContextAware.getImplementsMap(ILocalSubTaskEvent.class);

        if (interfaceMap != null){
            interfaceMap.forEach((K,V)->{
                V.editLocalSubTaskStatus(localSubTaskEntity.getTaskID(),localSubTaskEntity.getTaskID(),
                        localSubTaskEntity.getStatus());
            });
        }

        return true;
    }

    @Override
    public boolean editLocalSubTaskAssignee(LocalSubTaskEntity localSubTaskEntity) {
        // 检查所申请操作的subTaskID是否属于taskID
        int[] subTaskIDArray = {localSubTaskEntity.getId()};
        if(!checkSubTask(localSubTaskEntity.getTaskID(),subTaskIDArray)){
            return false;
        }

        int userID = UserCommon.getUserBo().getUserID();
        int subTaskID = localSubTaskEntity.getId();
        int taskID = localSubTaskEntity.getTaskID();

        // 判断是否是管理员，或者管理员允许修改SubTask分配
        try {
            if(!localSubTaskDao.isUserAdmin(userID,localSubTaskEntity.getTaskID()) && !localSubTaskDao.isAllowUserChangeAssignee(taskID)){
                return false;
            }
        } catch (Exception ex){
            return false;
        }

        if (localSubTaskEntity.getAssignee() != 0){
            localSubTaskDao.updateLocalSubTaskAssignee(localSubTaskEntity);
        }else {
            localSubTaskDao.clearLocalSubTaskAssignee(localSubTaskEntity);
        }

        return true;
    }

    private boolean checkIsAdmin(int taskID){
        int opUser = UserCommon.getUserBo().getUserID();
        try {
            return localSubTaskDao.isUserAdmin(opUser,taskID);
        }
        catch (Exception ex) {
            return false;
        }
    }

    /**
     * 检查子任务是否归属于某个任务
     * @param taskID 任务ID
     * @param subTaskID 子任务ID（数组）
     * @return 是否有权限
     */
    private boolean checkSubTask(int taskID,int[] subTaskID){

        try {
            List<Integer> taskIDArray = localSubTaskDao.getTaskIDsBySubTask(subTaskID);

            // 遍历所有的taskID,如果所选择的id存在与taskID（即请求操作的ID）不同，则没有权限
            for (int id : taskIDArray) {
                if (id != taskID)
                    return false;
            }

            // 完成所有遍历，所有子任务的任务ID都等于taskID，有权限
            return true;
        }catch (Exception ex){
            return false;
        }
    }


}
