package com.dtask.DTask.userModule.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * Created by zhong on 2020-3-22.
 */
@Data
public class ChildMenuBo {
    private int id;

    private String name;

    private String url;

    private String pObject;

    private String icon;
}
