package com.dtask.common.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by zhong on 2021-12-28.
 */

@Data
public class ApplicationToken {
    private Date timestamp;

    private List<String> apiList;

    private String applicationID;
}
