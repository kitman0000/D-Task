package com.dtask.liveMeeting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by zhong on 2021-2-6.
 */

@ComponentScan({"com.dtask.*","com.MQClouder"})
@SpringBootApplication
public class LiveMeeting {
    public static void main(String[] args) {
        SpringApplication.run(LiveMeeting.class, args);
    }
}
