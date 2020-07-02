package com.dtask.common;

import com.dtask.common.util.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhong on 2020-6-15.
 */
@Component
public class NodeCommon {
    @Autowired
    public CacheUtil cacheUtil;

    public int getNodeID(){
        Object nodeIDObj = cacheUtil.read("nodeID");
        if(nodeIDObj == null){
            // 如果缓存中没有没有nodeID，返回-1
            return -1;
        }
        return Integer.parseInt(nodeIDObj.toString());
    }
}
