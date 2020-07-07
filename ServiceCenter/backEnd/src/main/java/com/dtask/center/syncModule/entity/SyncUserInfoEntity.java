package com.dtask.center.syncModule.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by zhong on 2020-6-11.
 */
@Data
public class SyncUserInfoEntity {
    int nodeID;
    List<UserListEntity> userListEntity;
}
