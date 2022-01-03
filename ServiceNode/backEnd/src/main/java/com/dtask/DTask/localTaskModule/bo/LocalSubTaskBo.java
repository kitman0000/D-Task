package com.dtask.DTask.localTaskModule.bo;

import lombok.Data;

import java.util.Date;

/**
 * Created by zhong on 2020-5-5.
 */
@Data
public class LocalSubTaskBo {
    private int taskID;

    private int id;

    private String name;

    private String content;

    private Date deadline;

    private int status;

    private Date startTime;

    private int level;

    private String tag;

    private int star;

    private int assignee;

    private String assigneeName;
}
