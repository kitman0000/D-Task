package com.dtask.center.bandingModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by zhong on 2020-5-13.
 */
@Component
public class BandingCl {
    Logger logger = LoggerFactory.getLogger(BandingCl.class);

    @RabbitListener(queues = "dtask.binding")
    public void askBanding(String msg){
        logger.info(msg);
    }
}
