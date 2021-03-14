package com.dtask.center.remoteTaskModule.bo;

import lombok.Data;

/**
 * Created by zhong on 2020-5-3.
 */
@Data
public class RemoteTaskBo {
    private int id;

    private String taskName;

    private int creatorID;

    private String creatorName;

    private int creatorNode;

    private boolean allowedMemberChangeStatus;

    private int planning;

    private int working;

    private int finish;

    private int cancel;

}
