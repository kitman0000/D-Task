package com.dtask.pluginsdk.settingModule;

/**
 * Created by zhong on 2020-7-2.
 */
public interface ISettingEvent {
    void updateSetting(String websiteName,int cache,int maxLogin,boolean taskLog,boolean userLog);

    void shutDown();

}
