package com.dtask.DTask.externalModule.bo;

import lombok.Data;

/**
 * Created by zhong on 2022-1-1.
 */
@Data
public class ExternalUrlBo {
    private int id;

    private String url;

    private String method;

    private String describe;
}
