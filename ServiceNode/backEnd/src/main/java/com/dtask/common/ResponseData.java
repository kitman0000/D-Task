package com.dtask.common;

import com.dtask.common.util.JsonUtil;
import lombok.Data;

/**
 * Created by zhong on 2020-3-16.
 */
@Data
public class ResponseData {
    private int ret;
    private String message;
    private Object data;

    public ResponseData(int ret, String message, Object data) {
        this.ret = ret;
        this.message = message;
        this.data = data;
    }

    public ResponseData(int ret, String message, Object data,boolean standardJson){
        this.ret = ret;
        this.message = message;
        if (standardJson){
            if (data instanceof String){
                this.data = data;
            }else {
                this.data = JsonUtil.objectToJson(data);
            }
        }else {
            this.data = data;
        }
    }
}
