package com.dtask.DTask.userModule.controller;

import com.dtask.DTask.userModule.service.IMenu;
import com.dtask.common.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2020-3-20.
 */
@RestController
public class MenuCl {
    @Autowired
    IMenu menu;

    @RequestMapping(value = "/api/menu/userMenu",method = RequestMethod.GET)
    public ResponseData getUserMenu(){
        return menu.getUserMenu();
    }
}
