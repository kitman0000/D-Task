package com.dtask.DTask.settingModule.controller;

import com.dtask.DTask.settingModule.entity.SettingEntity;
import com.dtask.DTask.settingModule.service.ISetting;
import com.dtask.common.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhong on 2020-7-2.
 */
@RestController
public class SettingCl{
    @Autowired
    ISetting setting;

    @RequestMapping(value = "/api/webSiteSettings/setting",method = RequestMethod.GET)
    public ResponseData getSetting() {
        return setting.getSetting();
    }

    @RequestMapping(value = "/api/webSiteSettings/setting",method = RequestMethod.POST)
    public ResponseData updateSetting(SettingEntity settingEntity) {
        return setting.updateSetting(settingEntity);
    }

    @RequestMapping(value = "/api/webSiteSettings/shutDown",method = RequestMethod.POST)
    public ResponseData shutDown(){
        return setting.shutDown();
    }

    @RequestMapping(value = "/api/webSiteSettings/nodeName",method = RequestMethod.GET)
    public ResponseData getNodeName(){
        String nodeName = setting.getNodeName();
        return new ResponseData(1,"查询成功",nodeName);
    }

}
