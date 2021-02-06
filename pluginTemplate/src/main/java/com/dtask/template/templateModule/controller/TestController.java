package com.dtask.template.templateModule.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2021-2-6.
 */
@RestController
@RequestMapping("/api/template")
public class TestController {

    @RequestMapping(method = RequestMethod.GET,value = "test")
    public String test(){
        return "Welcome to D-Task";
    }
}
