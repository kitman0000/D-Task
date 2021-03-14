package com.dtask.liveMeetingCenter.liveMeetingModule.service;

import com.dtask.liveMeetingCenter.liveMeetingModule.bo.MeetingRoomBo;
import com.dtask.liveMeetingCenter.liveMeetingModule.entity.AccessToMeetingRoomEntity;
import com.dtask.liveMeetingCenter.liveMeetingModule.entity.MeetingRoomEntity;

import java.util.List;

public interface IMeetingRoom {
    List<MeetingRoomBo> getMeetingRoom();

    String setMeetingRoom(MeetingRoomEntity meetingRoomEntity);

    String accessToMeetingRoom(AccessToMeetingRoomEntity accessToMeetingRoomEntity);
}
