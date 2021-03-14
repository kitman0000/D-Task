package com.dtask.center.remoteTaskModule.service.impl;

import com.dtask.center.remoteTaskModule.bo.ChartLevelBo;
import com.dtask.center.remoteTaskModule.bo.ChartStarBo;
import com.dtask.center.remoteTaskModule.bo.ChartStatusBo;
import com.dtask.center.remoteTaskModule.bo.RemoteSubTaskChartBo;
import com.dtask.center.remoteTaskModule.dao.RemoteSubTaskChartDao;
import com.dtask.center.remoteTaskModule.dao.RemoteSubTaskDao;
import com.dtask.center.remoteTaskModule.entity.RemoteChartEntity;
import com.dtask.center.remoteTaskModule.service.IRemoteSubTaskChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RemoteSubTaskChartImpl implements IRemoteSubTaskChart {

    @Autowired
    private RemoteSubTaskChartDao remoteSubTaskChartDao;

    @Override
    public RemoteSubTaskChartBo getRemoteSubTaskChart(RemoteChartEntity chartEntity) {
        int day = chartEntity.getDay();
        int taskID = chartEntity.getTaskID();

        List<ChartLevelBo> levelCountList = remoteSubTaskChartDao.getChartLevelCount(day,taskID);
        List<ChartStarBo> starCountList = remoteSubTaskChartDao.getChartStarCount(day,taskID);
        List<ChartStatusBo> statusCountList = remoteSubTaskChartDao.getChartStatusCount(day,taskID);
        RemoteSubTaskChartBo remoteSubTaskChartBo = new RemoteSubTaskChartBo();
        remoteSubTaskChartBo.setChartLevelBo(levelCountList)
                .setChartStarBo(starCountList)
                .setChartStatusBo(statusCountList);

        return remoteSubTaskChartBo;
    }
}
