package com.dtask.liveMeeting.liveMeetingModule.controller;

import com.MQClouder.EncryptRabbitSender;
import com.dtask.common.util.JsonUtil;
import com.dtask.liveMeeting.liveMeetingModule.bo.MeetingRoom;
import com.dtask.liveMeeting.liveMeetingModule.entity.AccessToMeetingRoomEntity;
import com.dtask.liveMeeting.liveMeetingModule.entity.MeetingRoomEntity;
import com.dtask.liveMeeting.liveMeetingModule.service.IMeetingRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zhong on 2021-2-21.
 */
@RestController
@RequestMapping(value = "/liveMeeting/meetingRoom")
public class MeetingRoomController {

    @Autowired
    private IMeetingRoom meetingRoom;

    /**
     * 获取所有会议室
     * @return
     */
    @RequestMapping(value = "/meetingRoom",method = RequestMethod.GET)
    public String getMeetingRoom(){
        return meetingRoom.getMeetingRoom();
    }

    /**
     * 设置会议室
     * @param meetingRoomEntity 会议室设定
     * @return MEETING_SET_SUCCESS
     */
    @RequestMapping(value = "/meetingRoom",method = RequestMethod.PUT)
    public String setMeetingRoom(MeetingRoomEntity meetingRoomEntity){
        return meetingRoom.setMeetingRoom(meetingRoomEntity);
    }

    /**
     * 进入房间
     * @param accessToMeetingRoomEntity 要加入的房间信息
     * @return token TOKEN_CREATE_FAILED
     */
    @RequestMapping(value = "/access",method = RequestMethod.POST)
    public String accessToMeetingRoom(AccessToMeetingRoomEntity accessToMeetingRoomEntity){
        return meetingRoom.accessToMeetingRoom(accessToMeetingRoomEntity);
    }


}
