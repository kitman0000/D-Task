package com.dtask.DTask.remoteTaskModule.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by zhong on 2020-5-5.
 */
@Data
public class RemoteSubTaskEntity {
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
}
