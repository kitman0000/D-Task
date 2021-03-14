package com.dtask.liveMeetingCenter.liveMeetingModule.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by zhong on 2021-3-2.
 */
@Mapper
@Repository
public interface LiveMeetingDao {
    // 获取房间的主持人
    String getRoomHost(int roomID);

    // 获取用户名称
    String getUsername(@Param("nodeID") int nodeID, @Param("userID") int userID);
}
