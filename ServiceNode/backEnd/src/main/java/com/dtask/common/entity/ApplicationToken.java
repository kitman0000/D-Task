package com.dtask.common.entity;

import com.dtask.DTask.externalModule.bo.ExternalUrlBo;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by zhong on 2021-12-28.
 */

@Data
public class ApplicationToken {
    private Date timestamp;

    private List<ExternalUrlBo> apiList;

    private int applicationID;
}
