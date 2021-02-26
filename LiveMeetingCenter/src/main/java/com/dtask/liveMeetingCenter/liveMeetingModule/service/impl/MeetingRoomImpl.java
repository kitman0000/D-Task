package com.dtask.liveMeetingCenter.liveMeetingModule.service.impl;

import com.dtask.liveMeetingCenter.liveMeetingModule.bo.MeetingRoomBo;
import com.dtask.liveMeetingCenter.liveMeetingModule.dao.MeetingRoomDao;
import com.dtask.liveMeetingCenter.liveMeetingModule.entity.AccessToMeetingRoomEntity;
import com.dtask.liveMeetingCenter.liveMeetingModule.entity.MeetingRoomEntity;
import com.dtask.liveMeetingCenter.liveMeetingModule.service.IMeetingRoom;
import io.agora.media.AccessToken;
import io.agora.rtm.RtmTokenBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingRoomImpl implements IMeetingRoom {
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
        // todo 登录获取token
        String meetingRoomID = String.valueOf(accessToMeetingRoomEntity.getMeetingRoomID());
        // todo userID需要重新获取
        String userID = accessToMeetingRoomEntity.getUserID();

        String token = "";
        try {
            RtmTokenBuilder builder = new RtmTokenBuilder();
            token = builder.buildToken(appId, appCertificate, userID,meetingRoomID, RtmTokenBuilder.Role.Rtm_User, expireTimestamp);
        }catch (Exception ex){
            ex.printStackTrace();
            return "TOKEN_CREATE_FAILED";
        }

        return token;
    }


}
