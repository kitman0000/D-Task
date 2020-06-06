package com.dtask.DTask.userModule.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Created by zhong on 2019-12-3.
 */
@Data
public class UserDetailBo {
    private int id;

    private String username;

    private String pwd;

    private String nickName = "";

    private int roleID;

    private String email = "";

    private String phone = "";

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private String roleName;

    private int departmentID;

    private String departmentName;

    private String profileUrl;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date onboardDate;

}
