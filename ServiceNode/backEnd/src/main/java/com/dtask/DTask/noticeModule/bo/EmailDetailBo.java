package com.dtask.DTask.noticeModule.bo;

import lombok.Data;

import java.util.Date;

/**
 * Created by zhong on 2020-4-14.
 */

@Data
public class EmailDetailBo {
    private String sender;

    private String receiver;

    private String title;

    private String content;

    private Date sendTime;

    private boolean hasRead;

    private boolean isImportant;

    private String attachment;
}
