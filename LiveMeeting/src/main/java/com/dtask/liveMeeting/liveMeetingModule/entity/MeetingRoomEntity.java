package com.dtask.liveMeeting.liveMeetingModule.entity;

import lombok.Data;

@Data
public class MeetingRoomEntity {
    private int id;

    private String name;

    private int hostID;

    private String password;
}
