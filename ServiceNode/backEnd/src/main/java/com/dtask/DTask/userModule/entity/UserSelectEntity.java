package com.dtask.DTask.userModule.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by zhong on 2020-3-24.
 */
@Data
public class UserSelectEntity implements Serializable{
    private String username;

    private String nickname;

    private String phone;

    private String email;

    private String onboardDateStart;

    private String onboardDateEnd;

    private int roleID;

    private int departmentID;

    private String birthdayStart;

    private String birthdayEnd;
}
