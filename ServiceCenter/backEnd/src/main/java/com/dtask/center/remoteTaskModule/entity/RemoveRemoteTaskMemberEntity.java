package com.dtask.center.remoteTaskModule.entity;

import lombok.Data;

/**
 * Created by zhong on 2020-6-9.
 */
@Data
public class RemoveRemoteTaskMemberEntity {
    int taskID;
    int userID;
    int nodeID;

    int removeUserID;
    int removeUserNodeID;
}
