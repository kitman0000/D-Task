package com.dtask.DTask.bindingModule.service;

import com.dtask.common.ResponseData;

/**
 * Created by zhong on 2020-6-14.
 */
public interface IBinding {
    ResponseData getAllNodes();

    ResponseData askBinding(int requestBindID);

    ResponseData getBindRequest();

    ResponseData handleBindingRequest(int request, boolean accept);

    ResponseData setRoot();

    ResponseData unbind();

    /** 以下为自动操作 **/

    void addNode();

    void getNodeID();

    ResponseData apiGetNodeID();
}
