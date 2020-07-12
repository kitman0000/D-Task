package com.dtask.center.accountModule.entity;

import lombok.Data;

/**
 * Created by zhong on 2020-7-10.
 */
@Data
public class RemoteLoginEntity {
    String username;
    String pwd;

    // 用户所属节点
    int userNodeID;

    // 要登录的节点
    int loginNodeID;
}
