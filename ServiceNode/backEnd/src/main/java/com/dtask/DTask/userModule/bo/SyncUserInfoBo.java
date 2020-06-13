package com.dtask.DTask.userModule.bo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by zhong on 2020-6-11.
 */
@Data
public class SyncUserInfoBo {
    int nodeID;

    @JsonProperty("userListEntity")
    List<UserListBo> userListBo;
}
