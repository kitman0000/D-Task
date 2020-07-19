package com.dtask.DTask.localTaskModule.bo;

import lombok.Data;

/**
 * Created by zhong on 2020-5-3.
 */
@Data
public class LocalTaskBo {
    private int id;

    private String name;

    private int creator;

    private String creatorName;

    private int departmentID;

    private boolean allowedMemberChangeStatus;
}
