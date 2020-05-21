package com.dtask.DTask.userModule.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    private String roleID;

    private String department;

    private int departmentID;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date onboardDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDay;

}
