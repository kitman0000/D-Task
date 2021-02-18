package com.dtask.DTask.userModule.service;

import com.dtask.common.ResponseData;
import com.dtask.pluginsdk.userModule.bo.ParentMenuBo;

import java.util.List;

/**
 * Created by zhong on 2020-3-20.
 */
public interface IMenu {
    ResponseData getUserMenu();

    ResponseData getAllMenu();

    ResponseData addMenu();

    ResponseData editMenu();

    ResponseData deleteMenu();
}
