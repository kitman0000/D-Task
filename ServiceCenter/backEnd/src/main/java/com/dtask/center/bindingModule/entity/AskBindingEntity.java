package com.dtask.center.bindingModule.entity;

import lombok.Data;

/**
 * Created by zhong on 2020-5-16.
 */
@Data
public class AskBindingEntity {
    private int nodeID; // 自身ID
    private int requestBindID; // 要绑定的ID
}
