package com.dtask.DTask.remoteTaskModule.service;

import com.dtask.common.ResponseData;

public interface IRemoteSubTaskChart {
    ResponseData getRemoteSubTaskChart(int day, int taskID);
}
