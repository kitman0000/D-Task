package com.dtask.center.remoteTaskModule.bo;

import lombok.Data;

/**
 * Created by zhong on 2020-5-8.
 */
@Data
public class RemoteTaskMemberBo {
    int userID;
    int nodeID;
    String username;
    boolean isAdmin;
}
