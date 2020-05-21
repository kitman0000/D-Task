package com.dtask.DTask.userModule.entity;

import lombok.Data;

import java.util.Date;

/**
 * Created by zhong on 2020-3-30.
 */
@Data
public class UserAddEntity {
    private int id;

    private String username;

    private String pwd;

    private String nickname;

    private String phone;

    private String email;

    private int roleID;

    private int departmentID;

    private String birthday;

    private String onboardDate;


}
