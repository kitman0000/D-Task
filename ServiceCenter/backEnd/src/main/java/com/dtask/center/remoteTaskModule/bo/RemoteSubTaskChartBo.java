package com.dtask.center.remoteTaskModule.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class RemoteSubTaskChartBo {
    List<ChartLevelBo> chartLevelBo;

    List<ChartStarBo> chartStarBo;

    List<ChartStatusBo> chartStatusBo;
}
