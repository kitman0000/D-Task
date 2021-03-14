package com.dtask.DTask.localTaskModule.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class LocalSubTaskChartBo {
    List<ChartLevelBo> chartLevelBo;

    List<ChartStarBo> chartStarBo;

    List<ChartStatusBo> chartStatusBo;
}

