package com.dtask.liveMeetingCenter.init;

import com.dtask.liveMeetingCenter.liveMeetingModule.dao.MeetingRoomDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
public class InitRunner implements ApplicationRunner {

    @Autowired
    private MeetingRoomDao meetingRoomDao;

    /***
     * 初始化插件
     */
    @Order(value = 1)
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //meetingRoomDao.initMeetingRoom();
    }
}
