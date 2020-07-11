package com.dtask.center.accountModule.service;

/**
 * Created by zhong on 2020-7-10.
 */
public interface IAccount {
    String remoteLogin(String msg);

    String getParentNodes(int nodeID);
}
