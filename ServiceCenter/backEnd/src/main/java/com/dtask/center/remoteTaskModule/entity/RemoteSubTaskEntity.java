package com.dtask.center.remoteTaskModule.entity;

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

    private String deadline;

    private int status;

    private String startTime;

    private int level;

    private String tag;

    private int star;

    // 操作用户信息
    private int userID;

    private int nodeID;

    // 分页查找
    private int page;
}
