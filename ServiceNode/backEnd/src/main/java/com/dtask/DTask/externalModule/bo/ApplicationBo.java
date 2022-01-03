package com.dtask.DTask.externalModule.bo;

import lombok.Data;

/**
 * Created by zhong on 2022-1-1.
 */
@Data
public class ApplicationBo {
    private int id;

    private String name;

    private String password;

    private boolean available;
}
