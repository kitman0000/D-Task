package com.dtask.DTask.localTaskModule.bo;

import lombok.Data;

/**
 * Created by zhong on 2020-5-8.
 */
@Data
public class LocalTaskMemberBo {
    int userID;
    String username;
    boolean isAdmin;
}
