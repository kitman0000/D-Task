package com.dtask.DTask.localTaskModule.dao;

import com.dtask.DTask.localTaskModule.bo.LocalTaskAssigneeCount;
import com.dtask.DTask.localTaskModule.bo.LocalTaskBo;
import com.dtask.DTask.localTaskModule.bo.LocalTaskMemberBo;
import com.dtask.DTask.localTaskModule.entity.LocalTaskSearchEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhong on 2020-5-3.
 */
@Mapper
@Repository
public interface LocalTaskDao {
    void insertLocalTask(@Param("creatorID") int creatorID, @Param("name") String name);

    LocalTaskBo getLocalTaskDetail(int taskID);

    void updateLocalTask(@Param("id") int id, @Param("name") String name,
                         @Param("creator") int creator, @Param("allowedMemberChangeStatus") boolean allowedMemberChangeStatus,
                         @Param("allowedMemberChangeAssignee") boolean allowedMemberChangeAssignee);

    void deleteLocalTask(int id);

    int getLocalTaskNumber(@Param("localTaskSearchEntity") LocalTaskSearchEntity localTaskSearchEntity);

    List<LocalTaskBo> getLocalTaskList(@Param("localTaskSearchEntity") LocalTaskSearchEntity localTaskSearchEntity, @Param("startRow") int startRow, @Param("rowsOnePage") int rowsOnePage);

    int getUserLocalTaskNumber(@Param("localTaskSearchEntity") LocalTaskSearchEntity localTaskSearchEntity, @Param("userID") int userID);

    List<LocalTaskBo> getUserLocalTaskList(@Param("localTaskSearchEntity") LocalTaskSearchEntity localTaskSearchEntity, @Param("userID") int userID, @Param("startRow") int startRow, @Param("rowsOnePage") int rowsOnePage);

    int checkTaskMemberExist(@Param("taskID") int taskID, @Param("userID") int userID);

    void addTaskMember(@Param("taskID") int taskID, @Param("userID") int userID);

    void removeTaskMember(@Param("taskID") int taskID, @Param("userID") int userID);

    int getTaskCreator(int taskID);

    boolean checkIsAdmin(@Param("taskID") int taskID, @Param("userID") int userID);

    List<LocalTaskMemberBo> getTaskUser(int taskID);

    int getLastCreatedTaskID();

    void setLocalTaskAdmin(@Param("taskID") int taskID, @Param("userID") int userID, @Param("isAdmin") boolean isAdmin);

    boolean getAllowUserChangeStatus(int taskID);

    boolean getAllowUserChangeAssignee(int taskID);

    List<LocalTaskAssigneeCount> getTaskAssigneeCount(int taskID);
}
