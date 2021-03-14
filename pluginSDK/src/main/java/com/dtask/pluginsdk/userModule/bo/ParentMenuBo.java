package com.dtask.pluginsdk.userModule.bo;

import java.util.List;

/**
 * Created by zhong on 2020-3-22.
 */

public class ParentMenuBo {
    private int id;
    private String name;
    private List<ChildMenuBo> childMenuBoList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChildMenuBo> getChildMenuBoList() {
        return childMenuBoList;
    }

    public void setChildMenuBoList(List<ChildMenuBo> childMenuBoList) {
        this.childMenuBoList = childMenuBoList;
    }
}
