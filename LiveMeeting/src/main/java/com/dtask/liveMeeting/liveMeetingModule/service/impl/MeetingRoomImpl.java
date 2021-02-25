package com.dtask.liveMeeting.liveMeetingModule.service.impl;

import com.MQClouder.EncryptRabbitSender;
import com.dtask.common.util.JsonUtil;
import com.dtask.liveMeeting.liveMeetingModule.entity.AccessToMeetingRoomEntity;
import com.dtask.liveMeeting.liveMeetingModule.entity.MeetingRoomEntity;
import com.dtask.liveMeeting.liveMeetingModule.service.IMeetingRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhong on 2021-2-21.
 */
@Service
public class MeetingRoomImpl implements IMeetingRoom {

    @Autowired
    private EncryptRabbitSender rabbitSender;

    /***
     * 获取所有会议室
     * @return
     */
    @Override
    public String getMeetingRoom() {
        return rabbitSender.encryptSend("dtask.liveMeeting.getMeetingRoom","");
    }

    /**
     * 设置会议室
     * @param meetingRoomEntity
     * @return
     */
    @Override
    public String setMeetingRoom(MeetingRoomEntity meetingRoomEntity) {
        String msg = JsonUtil.objectToJson(meetingRoomEntity);

        return rabbitSender.encryptSend("dtask.liveMeeting.setMeetingRoom",msg);
    }

    /**
     * 进入房间
     * @param accessToMeetingRoomEntity
     * @return token
     */
    @Override
    public String accessToMeetingRoom(AccessToMeetingRoomEntity accessToMeetingRoomEntity) {

        // todo 获取用户信息

        String msg = JsonUtil.objectToJson(accessToMeetingRoomEntity);

        return rabbitSender.encryptSend("dtask.liveMeeting.accessToMeetingRoom",msg);
    }
}
