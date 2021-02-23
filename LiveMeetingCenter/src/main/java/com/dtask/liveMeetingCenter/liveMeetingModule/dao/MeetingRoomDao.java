package com.dtask.liveMeetingCenter.liveMeetingModule.dao;

import com.dtask.liveMeetingCenter.liveMeetingModule.bo.MeetingRoomBo;
import com.dtask.liveMeetingCenter.liveMeetingModule.entity.MeetingRoomEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MeetingRoomDao {
    List<MeetingRoomBo> getMeetingRoom();

    void setMeetingRoom(@Param("meetingRoomEntity") MeetingRoomEntity meetingRoomEntity);
}
