package com.dtask.liveMeeting.liveMeetingModule.dao;

import com.dtask.liveMeeting.liveMeetingModule.bo.MeetingRoom;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhong on 2021-2-21.
 */
@Repository
@Mapper
public interface IMeetingRoomDao {
    List<MeetingRoom> getAllMeetingRoom();
}
