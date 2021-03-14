package com.dtask.liveMeeting.liveMeetingModule.service.impl;

import com.MQClouder.EncryptRabbitSender;
import com.dtask.common.util.JsonUtil;
import com.dtask.liveMeeting.liveMeetingModule.entity.AccessToMeetingRoomEntity;
import com.dtask.liveMeeting.liveMeetingModule.entity.MeetingRoomEntity;
import com.dtask.liveMeeting.liveMeetingModule.service.IMeetingRoom;
import com.dtask.pluginsdk.accountModule.IUserProvider;
import com.dtask.pluginsdk.bindingModule.INodeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by zhong on 2021-2-21.
 */
@Service
public class MeetingRoomImpl implements IMeetingRoom {

    @Autowired
    private EncryptRabbitSender rabbitSender;

    @Autowired
    private IUserProvider userProvider;

    @Autowired
    private INodeProvider nodeProvider;

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

        accessToMeetingRoomEntity.setUserID(getUserID());
        String msg = JsonUtil.objectToJson(accessToMeetingRoomEntity);

        return rabbitSender.encryptSend("dtask.liveMeeting.accessToMeetingRoom",msg);
    }

    static Queue<String> testUser = new LinkedList<>();
    static {
        testUser.add("00000001");
        testUser.add("00000002");
        testUser.add("00000003");
        testUser.add("00000004");
    }

    static String cache = "";
    static int count = 0;

    /**
     * 生成全节点唯一用户ID，前3位为节点ID，后5位为用户ID
     * @return 唯一用户ID
     */
    @Override
    public String getUserID() {
        //return "00000001";
        StringBuilder userID = new StringBuilder(String.valueOf(userProvider.getUserID()));
        StringBuilder nodeID = new StringBuilder(String.valueOf(nodeProvider.getNodeID()));

        // 补充0
        while (userID.length() < 5){
            userID.insert(0,0);
        }

        while (nodeID.length() < 3){
            nodeID.insert(0,0);
        }

        return nodeID.append(userID).toString();
//
//        if (count % 2 == 0){
//            cache = testUser.remove();
//        }
//
//        count++;
//
//        return cache;

    }
}
