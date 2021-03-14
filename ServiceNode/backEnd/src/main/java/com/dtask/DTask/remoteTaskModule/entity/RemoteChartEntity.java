package com.dtask.DTask.remoteTaskModule.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by zhong on 2021-3-7.
 */
@Data
@Accessors(chain = true)
public class RemoteChartEntity {
    private int day;

    private int taskID;
}
