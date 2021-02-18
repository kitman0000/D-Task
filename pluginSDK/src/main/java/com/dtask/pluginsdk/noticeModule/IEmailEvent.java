package com.dtask.pluginsdk.noticeModule;

/**
 * Created by zhong on 2020-4-13.
 */
public interface IEmailEvent {
    void sendMail(int[] receiverID, String title, String content, boolean isImportant, String filenames);
}
