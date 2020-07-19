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
    void addRemoteSubTask(@Param("remoteSubTaskEntity") RemoteSubTaskEntity remoteSubTaskEntity);

    void updateRemoteSubTask(@Param("remoteSubTaskEntity") RemoteSubTaskEntity remoteSubTaskEntity);

    void deleteRemoteSubTask(@Param("id") int[] id);

    int getRemoteSubTaskNumber(@Param("taskID") int taskID);

    List<RemoteSubTaskBo> getRemoteSubTaskList(@Param("taskID") int taskID, @Param("startRow") int startRow, @Param("rowsOnePage") int rowsOnePage);

    void updateRemoteSubTaskStatus(@Param("remoteSubTaskEntity") RemoteSubTaskEntity remoteSubTaskEntity);

    boolean isUserAdmin(@Param("userID") int userID, @Param("nodeID") int nodeID, @Param("taskID") int taskID);

    boolean isAllowUserChangeState(@Param("taskID") int taskID);
}
