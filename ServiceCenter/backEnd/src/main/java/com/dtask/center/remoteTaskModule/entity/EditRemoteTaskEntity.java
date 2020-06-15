package com.dtask.center.remoteTaskModule.entity;

import lombok.Data;

/**
 * Created by zhong on 2020-6-9.
 */
@Data
public class EditRemoteTaskEntity {
    int id;
    String name;
    int creator;
    boolean allowedMemberChangeStatus;
    int userID;
    int creatorNode;
}
