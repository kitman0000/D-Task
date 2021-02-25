package com.dtask.liveMeeting.liveMeetingModule.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zhong on 2021-2-10.
 */
@RequestMapping(value = "pluginPage/liveMeeting")
@Controller
public class PageController {
    @RequestMapping(value = "/liveMeeting",method = RequestMethod.GET)
    public String toTemplate(){
        return "LiveMeeting/liveMeeting";
    }
}
