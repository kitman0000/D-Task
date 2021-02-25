package com.dtask.liveMeeting.liveMeetingModule.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
/**
 * 用户登录实体类
 */
public class AccessToMeetingRoomEntity {
    private String userID;

    private String userNodeID;

    private int meetingRoomID;

    private String password;
}
