package com.dtask.center.remoteTaskModule.bo;

import lombok.Data;

/**
 * Created by zhong on 2020-5-3.
 */
@Data
public class RemoteTaskBo {
    private String taskName;

    private int creatorID;

    private String creatorName;
}