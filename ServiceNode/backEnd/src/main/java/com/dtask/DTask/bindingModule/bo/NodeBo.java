package com.dtask.DTask.bindingModule.bo;

import lombok.Data;

/**
 * Created by zhong on 2020-5-17.
 */
@Data
public class NodeBo {
    int id;
    String nodeName;
    String inheritRp;
    int parentNode;
}
