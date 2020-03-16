package com.dtask.common;

import lombok.Data;

/**
 * Created by zhong on 2020-3-16.
 */
@Data
public class ResponseData {
    private int ret;
    private String message;
    private Object[] data;

    public ResponseData(int ret, String message) {
        this.ret = ret;
        this.message = message;
    }
}
