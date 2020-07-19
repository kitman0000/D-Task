package com.dtask.DTask.remoteTaskModule.entity;

import lombok.Data;

/**
 * Created by zhong on 2020-5-3.
 */
@Data
public class RemoteTaskSearchEntity {
    String taskName;
    int page;
    // 获得用户的任务使用
    int userID;
    int nodeID;
}
