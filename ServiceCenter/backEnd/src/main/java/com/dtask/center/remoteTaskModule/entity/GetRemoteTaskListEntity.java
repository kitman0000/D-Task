package com.dtask.center.remoteTaskModule.entity;

import lombok.Data;

/**
 * Created by zhong on 2020-6-9.
 */
@Data
public class GetRemoteTaskListEntity {
    RemoteTaskSearchEntity remoteTaskSearchEntity;
    int page;
}
