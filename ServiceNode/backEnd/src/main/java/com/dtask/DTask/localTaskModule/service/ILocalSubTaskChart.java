package com.dtask.DTask.localTaskModule.service;

import com.dtask.common.ResponseData;

public interface ILocalSubTaskChart {
    ResponseData getLocalSubTaskChart(int day,int taskID);
}
