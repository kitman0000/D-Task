package com.dtask.DTask.localTaskModule.bo;

import lombok.Data;

/**
 * Created by zhong on 2022-1-1.
 */
@Data
public class PublicEditPermissionBo {
    private boolean allowChangeStatus;

    private boolean allowChangeAssignee;
}
