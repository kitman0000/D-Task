package com.dtask.DTask.userModule.bo;

import lombok.Data;

import java.util.List;

/**
 * Created by zhong on 2019-12-14.
 */
@Data
public class RoleBo {
    private int roleID;
    private String roleName;
    List<PermissionBo> permissionBoList;
}
