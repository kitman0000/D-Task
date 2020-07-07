package com.dtask.center.remoteTaskModule.entity;

import lombok.Data;

/**
 * Created by zhong on 2020-6-9.
 */
@Data
public class ToggleTaskAdminEntity {
    int taskID;
    int userID;
    int nodeID;
    boolean isAdmin;

    int toggleUserID;
    int toggleUserNodeID;
}
