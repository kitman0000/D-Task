package com.dtask.center.remoteTaskModule.entity;

import lombok.Data;

/**
 * Created by zhong on 2020-7-14.
 */
@Data
public class GetTaskUserRole {
    int userID;
    int nodeID;
    int taskID;
}
