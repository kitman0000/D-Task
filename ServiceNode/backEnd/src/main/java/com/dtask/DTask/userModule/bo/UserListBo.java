package com.dtask.DTask.userModule.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhong on 2020-3-24.
 */

@Data
public class UserListBo implements Serializable{
    private int id;

    private String username;

    private String nickname;

    private String email;

    private String profileUrl;

    private String role;

    private String department;

    private Date birthDay;

    private Date onboardDate;
}
