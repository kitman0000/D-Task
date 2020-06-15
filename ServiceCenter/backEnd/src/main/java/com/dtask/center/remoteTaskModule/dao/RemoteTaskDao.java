package com.dtask.center.remoteTaskModule.dao;

import com.dtask.center.remoteTaskModule.bo.RemoteTaskBo;
import com.dtask.center.remoteTaskModule.bo.RemoteTaskMemberBo;
import com.dtask.center.remoteTaskModule.entity.RemoteTaskSearchEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhong on 2020-5-3.
 */
@Mapper
@Repository
public interface RemoteTaskDao {
    void insertRemoteTask(@Param("creatorID") int creatorID, @Param("creatorNode") int creatorNode, @Param("name") String name);

    void updateRemoteTask(@Param("id") int id, @Param("name") String name, @Param("creatorNode") int creatorNode,
                          @Param("creator") int creator, @Param("allowedMemberChangeStatus") boolean allowedMemberChangeStatus);

    void deleteRemoteTask(int id);

    int getRemoteTaskNumber(@Param("remoteTaskSearchEntity") RemoteTaskSearchEntity remoteTaskSearchEntity);

    List<RemoteTaskBo> getRemoteTaskList(@Param("remoteTaskSearchEntity") RemoteTaskSearchEntity remoteTaskSearchEntity, @Param("startRow") int startRow, @Param("rowsOnePage") int rowsOnePage);

    int checkTaskMemberExist(@Param("taskID") int taskID,@Param("nodeID")int nodeID, @Param("userID") int userID);

    void addTaskMember(@Param("taskID") int taskID,@Param("nodeID") int nodeID, @Param("userID") int userID);

    void removeTaskMember(@Param("taskID") int taskID, @Param("nodeID") int nodeID, @Param("userID") int userID);

    RemoteTaskMemberBo getTaskCreator(int taskID);

    boolean checkIsAdmin(@Param("taskID") int taskID, @Param("nodeID") int nodeID, @Param("userID") int userID);

    List<RemoteTaskMemberBo> getTaskUser(int taskID);

    int getCurrentTaskID();

    void setRemoteTaskAdmin(@Param("taskID") int taskID, @Param("nodeID") int nodeID, @Param("userID") int userID, @Param("isAdmin") boolean isAdmin);


}
