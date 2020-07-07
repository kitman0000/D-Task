package com.dtask.DTask.settingModule.entity;

import lombok.Data;

/**
 * Created by zhong on 2020-7-2.
 */
@Data
public class SettingEntity {
    private String websiteName;

    private int cache;

    private int maxLogin;

    private boolean taskLog;

    private boolean userLog;
}
