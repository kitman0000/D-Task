package com.dtask.liveMeeting.liveMeetingModule.controller;

import com.dtask.liveMeeting.liveMeetingModule.bo.MeetingRoom;
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

    @RequestMapping(value = "/meetingRoom",method = RequestMethod.GET)
    List<MeetingRoom> getMeetingRoom(){

    }
}
