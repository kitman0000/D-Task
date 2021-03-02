package com.dtask.DTask.remoteTaskModule.service.impl;


import com.dtask.DTask.remoteTaskModule.service.IRemoteSubTaskChart;
import com.dtask.common.ResponseData;
import org.springframework.stereotype.Service;

@Service
public class RemoteSubTaskChartImpl implements IRemoteSubTaskChart {
    public ResponseData getRemoteSubTaskChart(int day,int taskID) {
        return new ResponseData(1, "2", null);
    }
}
