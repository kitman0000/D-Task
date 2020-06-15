package com.dtask.center.bindingModule.dao;

import com.dtask.center.bindingModule.bo.BindingRequestBo;
import com.dtask.center.bindingModule.bo.NodeBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhong on 2020-5-16.
 */
@Mapper
@Repository
public interface BindingDao {
    void insertNode(@Param("nodeName") String nodeName);

    void addBindingRequest(@Param("requestNodeID") int requestNodeID, @Param("requestBindID") int requestBindID);

    int getUnhandleRequest(int nodeID);

    void updateBindingRequest(@Param("requestID") int requestID, @Param("nodeID") int nodeID, @Param("isAccept") boolean isAccept);

    void updateBindingRp(@Param("requestNodeID") int requestNodeID, @Param("requestBindID") int requestBindID);

    BindingRequestBo getRequest(int requestID);

    int getNodeID(@Param("nodeName") String nodeName);

    List<NodeBo> getAllNodes();

    String getNodeInheritRp(@Param("nodeName") String nodeName);

    String getNodeInheritRpByNodeID(int nodeID);

    void unbindNode(@Param("inheritRp") String inheritRp, @Param("nullValue") String nullValue);

    List<BindingRequestBo> getNodeBindRequest(int nodeID);

    int checkRoot();

    void setRoot(int nodeID);
}
