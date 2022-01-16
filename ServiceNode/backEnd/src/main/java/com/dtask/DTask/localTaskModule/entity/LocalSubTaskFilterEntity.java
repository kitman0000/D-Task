package com.dtask.DTask.localTaskModule.entity;

import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.Date;

/**
 * Created by zhong on 2022-1-16.
 */
@Data
public class LocalSubTaskFilterEntity {
    @Nullable
    private String name;

    @Nullable
    private Integer assignee;

    @Nullable
    private String timeStart;

    @Nullable
    private String timeEnd;

    @Nullable
    private Integer level;

    @Nullable
    private Integer status;

    @Nullable
    private Integer star;
}
