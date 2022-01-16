package com.dtask.DTask.localTaskModule.bo;

import lombok.Data;

/**
 * Created by zhong on 2020-5-8.
 */
@Data
public class LocalTaskMemberBo {
    private int userID;

    private String username;

    private boolean isAdmin;

    private String nickname;

    private boolean isAutoAssign;
}
