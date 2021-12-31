package com.dtask.DTask.userModule.bo;

import lombok.Data;

/**
 * Author: Kitman0000
 * Date:   2021-12-28
 * Intro:
 **/
@Data
public class DTaskUser {
    private String username;

    private int DTaskID;

    private boolean available;
}
