package com.dtask.DTask.settingModule.service;

import com.dtask.DTask.settingModule.entity.SettingEntity;
import com.dtask.common.ResponseData;

/**
 * Created by zhong on 2020-7-2.
 */
public interface ISetting {
    ResponseData getSetting();

    ResponseData updateSetting(SettingEntity settingEntity);

    ResponseData shutDown();

    String getNodeName();

}
