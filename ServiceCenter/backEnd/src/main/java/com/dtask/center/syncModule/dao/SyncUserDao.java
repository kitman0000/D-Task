package com.dtask.center.syncModule.dao;

import com.dtask.center.syncModule.bo.UserBo;
import com.dtask.center.syncModule.entity.UserListEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhong on 2020-6-11.
 */
@Mapper
@Repository
public interface SyncUserDao {
    void insertUserInfo(@Param("userListEntityList") List<UserListEntity> userListEntityList, @Param("nodeID") int nodeID);

    List<UserBo> getRemoteUser(int nodeID);
}
