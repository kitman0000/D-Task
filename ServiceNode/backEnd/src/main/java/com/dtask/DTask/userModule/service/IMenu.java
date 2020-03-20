package com.dtask.DTask.userModule.service;

import com.dtask.common.ResponseData;

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
