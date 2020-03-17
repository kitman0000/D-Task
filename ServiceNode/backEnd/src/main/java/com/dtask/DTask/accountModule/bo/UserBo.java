package com.dtask.DTask.accountModule.bo;

import lombok.Data;

/**
 * Created by zhong on 2019-12-3.
 */
@Data
public class UserBo {
    private int userID;
    private String username;
    private String pwd;
    private int status;
    private int role;
}
