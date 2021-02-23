package com.dtask.liveMeetingCenter.liveMeetingModule.service.impl;

import com.dtask.liveMeetingCenter.liveMeetingModule.bo.MeetingRoomBo;
import com.dtask.liveMeetingCenter.liveMeetingModule.dao.MeetingRoomDao;
import com.dtask.liveMeetingCenter.liveMeetingModule.entity.AccessToMeetingRoomEntity;
import com.dtask.liveMeetingCenter.liveMeetingModule.entity.MeetingRoomEntity;
import com.dtask.liveMeetingCenter.liveMeetingModule.service.IMeetingRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingRoomImpl implements IMeetingRoom {

    @Autowired
    private MeetingRoomDao meetingRoomDao;

    @Override
    public List<MeetingRoomBo> getMeetingRoom() {
        return meetingRoomDao.getMeetingRoom();
    }

    @Override
    public String setMeetingRoom(MeetingRoomEntity meetingRoomEntity) {
        meetingRoomDao.setMeetingRoom(meetingRoomEntity);
        return "MEETING_SET_SUCCESS";
    }

    @Override
    public String accessToMeetingRoom(AccessToMeetingRoomEntity accessToMeetingRoomEntity) {
        // todo 登录获取token
        return null;
    }


}
