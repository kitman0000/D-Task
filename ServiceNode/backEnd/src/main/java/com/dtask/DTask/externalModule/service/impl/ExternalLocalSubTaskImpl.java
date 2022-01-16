package com.dtask.DTask.externalModule.service.impl;

import com.dtask.DTask.externalModule.bo.ExternalSubTaskAddBo;
import com.dtask.DTask.externalModule.service.IExternalLocalSubTask;
import com.dtask.DTask.localTaskModule.bo.LocalSubTaskBo;
import com.dtask.DTask.localTaskModule.bo.LocalTaskAssigneeCount;
import com.dtask.DTask.localTaskModule.bo.LocalTaskMemberBo;
import com.dtask.DTask.localTaskModule.dao.LocalSubTaskDao;
import com.dtask.DTask.localTaskModule.dao.LocalTaskDao;
import com.dtask.DTask.localTaskModule.entity.LocalSubTaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhong on 2022-1-3.
 */
@Service
public class ExternalLocalSubTaskImpl implements IExternalLocalSubTask {

    @Autowired
    private LocalSubTaskDao localSubTaskDao;

    @Autowired
    private LocalTaskDao localTaskDao;

    /**
     * Auto assign the sub task which is last created.
     * @return
     */
    @Override
    public ExternalSubTaskAddBo autoAssignSubTask() {
        LocalSubTaskBo localSubTaskBo = localSubTaskDao.getLastCreatedSubTask();
        return autoAssignSubTask(localSubTaskBo);
    }

    @Override
    public ExternalSubTaskAddBo autoAssignSubTask(LocalSubTaskBo localSubTaskBo) {
        List<LocalTaskMemberBo> taskUser = localTaskDao.getTaskUser(localSubTaskBo.getTaskID());
        List<LocalTaskAssigneeCount> taskAssigneeCount = localTaskDao.getTaskAssigneeCount(localSubTaskBo.getTaskID());

        // Get the member with the fewest sub tasks
        int fewestSubTaskMemberID = 0;
        int assignedTaskCount = -1;
        for (LocalTaskAssigneeCount member : taskAssigneeCount) {
            // Filter the non-assign value
            if (member.getAssigneeID() != null && member.getAssigneeID() != -1) {
                if (assignedTaskCount == -1 || assignedTaskCount > member.getCount()) {
                    fewestSubTaskMemberID = member.getAssigneeID();
                    assignedTaskCount = member.getCount();
                }
                // Remove the member from list
                taskUser.removeIf(memberInList-> memberInList.getUserID() == member.getAssigneeID());
            }
        }

        // Have the member with the fewest sub task here and also the member who has non of sub tasks now.
        // Assign to the no sub task member first.
        LocalSubTaskEntity localSubTaskEntity = new LocalSubTaskEntity();
        localSubTaskEntity.setTaskID(localSubTaskBo.getTaskID());
        localSubTaskEntity.setId(localSubTaskBo.getId());

        if (taskUser.isEmpty()){
            // Every member has their sub tasks
            localSubTaskEntity.setAssignee(fewestSubTaskMemberID);
        }else {
            // There are members have no sub tasks
            localSubTaskEntity.setAssignee(taskUser.get(0).getUserID());
        }
        localSubTaskDao.updateLocalSubTaskAssignee(localSubTaskEntity);

        ExternalSubTaskAddBo externalSubTaskAddBo = new ExternalSubTaskAddBo();
        externalSubTaskAddBo.setSubTaskID(localSubTaskEntity.getId());
        externalSubTaskAddBo.setAssignee(localSubTaskEntity.getAssignee());
        return externalSubTaskAddBo;
    }
}
