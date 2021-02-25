package com.dtask.liveMeetingCenter.liveMeetingModule.bo;

import lombok.Data;

@Data
public class MeetingRoomBo {
    private int id;

    private String name;

    private int hostID;

    private boolean hasPwd;
}
