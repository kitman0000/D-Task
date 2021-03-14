package com.dtask.center.remoteTaskModule.service;


import com.dtask.center.remoteTaskModule.bo.RemoteSubTaskChartBo;
import com.dtask.center.remoteTaskModule.entity.RemoteChartEntity;

public interface IRemoteSubTaskChart {
    RemoteSubTaskChartBo getRemoteSubTaskChart(RemoteChartEntity chartEntity);
}
