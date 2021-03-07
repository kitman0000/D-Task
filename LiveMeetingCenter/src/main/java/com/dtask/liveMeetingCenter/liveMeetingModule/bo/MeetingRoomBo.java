package com.dtask.liveMeetingCenter.liveMeetingModule.bo;

import lombok.Data;

@Data
public class MeetingRoomBo {
    private int id;

    private String name;

    private String hostID;

    private boolean hasPwd;
}
