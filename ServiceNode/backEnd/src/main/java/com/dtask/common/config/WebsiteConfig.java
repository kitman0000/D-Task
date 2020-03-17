package com.dtask.common.config;

/**
 * Created by zhong on 2020-3-14.
 */
public class WebsiteConfig {
    private static int maxLoginTime = 5; // 5分钟内最大登录次数

    public static int getMaxLoginTime() {
        return maxLoginTime;
    }

    public static void setMaxLoginTime(int maxLoginTime) {
        maxLoginTime = maxLoginTime;
    }
}
