package com.dtask.DTask.localTaskModule.dao;

import com.dtask.DTask.localTaskModule.bo.LocalSubTaskBo;
import com.dtask.DTask.localTaskModule.entity.LocalSubTaskEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhong on 2020-5-3.
 */
@Mapper
@Repository
public interface LocalSubTaskDao {
    void addLocalSubTask(@Param("localSubTaskEntity") LocalSubTaskEntity localSubTaskEntity);

    void updateLocalSubTask(@Param("localSubTaskEntity") LocalSubTaskEntity localSubTaskEntity);

    void deleteLocalSubTask(@Param("id") int[] id);

    int getLocalSubTaskNumber(int taskID);

    List<LocalSubTaskBo> getLocalSubTaskList(@Param("taskID") int taskID, @Param("startRow") int startRow, @Param("rowsOnePage") int rowsOnePage);

    void updateLocalSubTaskStatus(@Param("localSubTaskEntity") LocalSubTaskEntity localSubTaskEntity);

    void updateLocalSubTaskAssignee(@Param("localSubTaskEntity") LocalSubTaskEntity localSubTaskEntity);

    void clearLocalSubTaskAssignee(@Param("localSubTaskEntity") LocalSubTaskEntity localSubTaskEntity);

    boolean isUserAdmin(@Param("userID") int userID, @Param("taskID") int taskID);

    boolean isAllowUserChangeState(int taskID);

    boolean isAllowUserChangeAssignee(int taskID);

    // 通过子任务ID获取任务ID
    List<Integer> getTaskIDsBySubTask(@Param("subTaskID") int[] subTaskID);
}
