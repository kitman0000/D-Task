package com.dtask.liveMeetingCenter.liveMeetingModule.service.impl;

import com.dtask.liveMeetingCenter.liveMeetingModule.bo.MeetingRoomBo;
import com.dtask.liveMeetingCenter.liveMeetingModule.dao.MeetingRoomDao;
import com.dtask.liveMeetingCenter.liveMeetingModule.entity.AccessToMeetingRoomEntity;
import com.dtask.liveMeetingCenter.liveMeetingModule.entity.MeetingRoomEntity;
import com.dtask.liveMeetingCenter.liveMeetingModule.service.IMeetingRoom;
import io.agora.media.AccessToken;
import io.agora.media.RtcTokenBuilder;
import io.agora.rtm.RtmTokenBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingRoomImpl implements IMeetingRoom {

    private final int EXPIRATION_TIME_IN_SECONDS = 3600; // token 有效期（秒）

    @Value("${agora.appId}")
    private String appId;

    @Value("${agora.appCertificate}")
    private String appCertificate;

    private int expireTimestamp = 1446455471;

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
        String meetingRoomID = String.valueOf(accessToMeetingRoomEntity.getMeetingRoomID());
        String userID = accessToMeetingRoomEntity.getUserID();

        String token = "";


        try {
            RtcTokenBuilder builder = new RtcTokenBuilder();
            int timestamp = (int)(System.currentTimeMillis() / 1000 + EXPIRATION_TIME_IN_SECONDS); // token过期时间
            token = builder.buildTokenWithUid(appId,appCertificate,meetingRoomID,
                    Integer.valueOf(userID), RtcTokenBuilder.Role.Role_Publisher,timestamp);
        }catch (Exception ex){
            ex.printStackTrace();
            return "TOKEN_CREATE_FAILED";
        }

        return token;
    }


}
