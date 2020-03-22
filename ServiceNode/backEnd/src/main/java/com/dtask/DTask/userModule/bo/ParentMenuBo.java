package com.dtask.DTask.userModule.bo;

import lombok.Data;

import java.util.List;

/**
 * Created by zhong on 2020-3-22.
 */
@Data
public class ParentMenuBo {
    private int id;
    private String name;
    private List<ChildMenuBo> childMenuBoList;
}
