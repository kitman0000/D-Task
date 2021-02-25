package com.dtask.template.templateModule.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zhong on 2021-2-10.
 */
@RequestMapping(value = "pluginPage/pluginName")
@Controller
public class PageController {
    @RequestMapping(value = "/templatePage",method = RequestMethod.GET)
    public String toTemplate(){
        return "pluginName/pluginName";
    }
}
