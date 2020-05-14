package com.dtask.DTask.localTaskModule.dao;

import com.dtask.DTask.localTaskModule.bo.LocalSubTaskBo;
import com.dtask.DTask.localTaskModule.entity.LocalSubTaskEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhong on 2020-5-3.
 */
@Mapper
@Repository
public interface LocalSubTaskDao {
    void addLocalSubTask(LocalSubTaskEntity localSubTaskEntity);

    void updateLocalSubTask(LocalSubTaskEntity localSubTaskEntity);

    void deleteLocalSubTask(int[] id);

    int getLocalSubTaskNumber(int taskID);

    List<LocalSubTaskBo> getLocalSubTaskList(int taskID);

    void updateLocalSubTaskStatus(LocalSubTaskEntity localSubTaskEntity);

    boolean isUserAdmin(int userID);

    boolean isAllowUserChangeState(int taskID);
}
