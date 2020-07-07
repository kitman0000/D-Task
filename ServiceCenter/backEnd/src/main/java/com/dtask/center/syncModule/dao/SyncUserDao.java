package com.dtask.center.syncModule.dao;

import com.dtask.center.syncModule.entity.UserListEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by zhong on 2020-6-11.
 */
@Mapper
@Repository
public interface SyncUserDao {
    void insertUserInfo(@Param("userListEntity") UserListEntity userListEntity, @Param("nodeID") int nodeID);
}
