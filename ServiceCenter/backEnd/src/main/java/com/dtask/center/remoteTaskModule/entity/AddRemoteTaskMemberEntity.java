package com.dtask.center.remoteTaskModule.entity;

import lombok.Data;

/**
 * Created by zhong on 2020-6-9.
 */
@Data
public class AddRemoteTaskMemberEntity {
    int taskID;
    int nodeID;
    int userID;

    int newUserID;
    int newUserNodeID;
}
