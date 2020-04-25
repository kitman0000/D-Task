package com.dtask.common.util;

import lombok.Data;

import java.util.List;

/**
 * Created by zhong on 2020-4-25.
 */
@Data
public class DTaskFile {
    List<String> filenameList;

    public void addFilename(String fileName){
        filenameList.add(fileName);
    }
}
