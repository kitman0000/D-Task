package com.dtask.DTask.userModule.bo;

import lombok.Data;

/**
 * Created by zhong on 2019-12-14.
 */
@Data
public class PermissionBo {
    private int id;
    private String pCategory;
    private String pObject;
    private String pOperate;
}
