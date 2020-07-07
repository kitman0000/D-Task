package com.dtask.common.config;

/**
 * Created by zhong on 2020-3-14.
 * 网站配置信息类
 */

public class WebsiteConfig {
    private static int maxLoginTime = 5; // 5分钟内最大登录次数

    private static boolean taskLog = true; // 是否开启任务日志

    private static boolean userLog = true; // 是否开启用户操作日志

    private static String websiteName = ""; // 网站名称

    private static boolean changePasswordCaptcha;

    public static int getMaxLoginTime() {
        return maxLoginTime;
    }

    public static void setMaxLoginTime(int maxLoginTime) {
        maxLoginTime = maxLoginTime;
    }

    public static boolean isTaskLog() {
        return taskLog;
    }

    public static void setTaskLog(boolean taskLog) {
        WebsiteConfig.taskLog = taskLog;
    }

    public static boolean isUserLog() {
        return userLog;
    }

    public static void setUserLog(boolean userLog) {
        WebsiteConfig.userLog = userLog;
    }

    public static String getWebsiteName() {
        return websiteName;
    }

    public static void setWebsiteName(String websiteName) {
        WebsiteConfig.websiteName = websiteName;
    }
}
