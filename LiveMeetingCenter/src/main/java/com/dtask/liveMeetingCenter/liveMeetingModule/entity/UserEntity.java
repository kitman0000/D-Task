package com.dtask.liveMeetingCenter.liveMeetingModule.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.websocket.Session;

/**
 * Created by zhong on 2021-2-28.
 */
@Data
@Accessors(chain = true)
public class UserEntity {
    private String userID;

    private int roomID;

    private String role;

    private boolean isEditor = false;

    private String username;

    @JsonIgnore
    private Session session;
}
