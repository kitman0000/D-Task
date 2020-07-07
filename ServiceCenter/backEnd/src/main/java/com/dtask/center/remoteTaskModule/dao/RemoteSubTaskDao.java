package com.dtask.center.remoteTaskModule.dao;
import com.dtask.center.remoteTaskModule.bo.RemoteSubTaskBo;
import com.dtask.center.remoteTaskModule.entity.RemoteSubTaskEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhong on 2020-5-3.
 */
@Mapper
@Repository
public interface RemoteSubTaskDao {
    void addRemoteSubTask(RemoteSubTaskEntity remoteSubTaskEntity);

    void updateRemoteSubTask(RemoteSubTaskEntity remoteSubTaskEntity);

    void deleteRemoteSubTask(int[] id);

    int getRemoteSubTaskNumber(int taskID);

    List<RemoteSubTaskBo> getRemoteSubTaskList(int taskID);

    void updateRemoteSubTaskStatus(RemoteSubTaskEntity localSubTaskEntity);

    boolean isUserAdmin(@Param("userID") int userID, @Param("nodeID") int nodeID, @Param("taskID") int taskID);

    boolean isAllowUserChangeState(int taskID);
}
