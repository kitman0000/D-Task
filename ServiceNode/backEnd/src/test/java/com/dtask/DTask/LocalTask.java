package com.dtask.DTask;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by zhong on 2021-3-11.
 */
@Data
@Accessors(chain = true)
public class LocalTask{
    private String name;
}