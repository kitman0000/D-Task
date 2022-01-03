package com.dtask.DTask.localTaskModule.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by zhong on 2020-5-5.
 */
@Data
public class LocalSubTaskEntity {
    private int taskID;

    private int id;

    private String name;

    private String content;

    private String deadline;

    private int status;

    private int assignee;

    private String startTime;

    private int level;

    private String tag;

    private int star;
}
