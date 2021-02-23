package com.dtask.liveMeetingCenter.liveMeetingModule.controller;

import com.MQClouder.MessageReceiver;
import com.dtask.common.util.JsonUtil;
import com.dtask.liveMeetingCenter.liveMeetingModule.entity.AccessToMeetingRoomEntity;
import com.dtask.liveMeetingCenter.liveMeetingModule.entity.MeetingRoomEntity;
import com.dtask.liveMeetingCenter.liveMeetingModule.service.IMeetingRoom;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MeetingRoomController {
    @Autowired
    private IMeetingRoom meetingRoom;

    /**
     * 获取所有会议室
     */
    @MessageReceiver
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dtask.liveMeeting.getMeetingRoom"),
            exchange = @Exchange(value = "topicExchange",type = "topic"),
            key = "dtask.liveMeeting.getMeetingRoom"
    ))
    public String getMeetingRoom(String msg){
        Object result = meetingRoom.getMeetingRoom();
        return JsonUtil.objectToJson(result);
    }

    /**
     * 编辑会议室
     */
    @MessageReceiver
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dtask.liveMeeting.setMeetingRoom"),
            exchange = @Exchange(value = "topicExchange",type = "topic"),
            key = "dtask.liveMeeting.setMeetingRoom"
    ))
    public String setMeetingRoom(String msg){
        return meetingRoom.setMeetingRoom((MeetingRoomEntity) JsonUtil.jsonToObject(msg, MeetingRoomEntity.class));
    }

    /**
     * 进入会议室
     * @return token
     */
    @MessageReceiver
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("dtask.liveMeeting.accessToMeetingRoom"),
            exchange = @Exchange(value = "topicExchange",type = "topic"),
            key = "dtask.liveMeeting.accessToMeetingRoom"
    ))
    public String accessToMeetingRoom(String msg){
        return meetingRoom.accessToMeetingRoom((AccessToMeetingRoomEntity)JsonUtil.jsonToObject(msg,AccessToMeetingRoomEntity.class));
    }

}
