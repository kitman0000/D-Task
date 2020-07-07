package com.dtask.center.syncModule.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhong on 2020-3-24.
 */

@Data
public class UserListEntity implements Serializable{
    private String id;

    private String username;

    private String nickname;

    private String email;

    private String profileUrl;

    private String roleID;

    private String department;

    private int departmentID;

    private Date onboardDate;

    private Date birthDay;

    private boolean available;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date lastUpdateTime;
}
