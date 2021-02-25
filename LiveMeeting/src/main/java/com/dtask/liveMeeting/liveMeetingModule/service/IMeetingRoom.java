package com.dtask.liveMeeting.liveMeetingModule.service;

import com.dtask.liveMeeting.liveMeetingModule.entity.AccessToMeetingRoomEntity;
import com.dtask.liveMeeting.liveMeetingModule.entity.MeetingRoomEntity;

/**
 * Created by zhong on 2021-2-21.
 */
public interface IMeetingRoom {
    String getMeetingRoom();

    String setMeetingRoom(MeetingRoomEntity meetingRoomEntity);

    String accessToMeetingRoom(AccessToMeetingRoomEntity accessToMeetingRoomEntity);
}
