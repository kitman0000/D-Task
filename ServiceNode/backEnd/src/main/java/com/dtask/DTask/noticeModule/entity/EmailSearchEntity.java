package com.dtask.DTask.noticeModule.entity;

import lombok.Data;

/**
 * Created by zhong on 2020-4-14.
 */
@Data
public class EmailSearchEntity {
    String keyword;

    Boolean hasRead;

    Boolean isImportant;

    String sendTimeStart;

    String sendTimeEnd;

    int page;
}
