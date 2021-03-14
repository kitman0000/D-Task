package com.dtask.DTask.remoteTaskModule.service;

import com.dtask.DTask.remoteTaskModule.entity.RemoteChartEntity;
import com.dtask.common.ResponseData;

public interface IRemoteSubTaskChart {
    ResponseData getRemoteSubTaskChart(RemoteChartEntity chartEntity);
}
