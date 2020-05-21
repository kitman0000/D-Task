package com.dtask.center.bandingModule.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Created by zhong on 2020-5-16.
 */
@Mapper
@Repository
public interface BandingDao {
    void insertNode(String nodeName);
}
