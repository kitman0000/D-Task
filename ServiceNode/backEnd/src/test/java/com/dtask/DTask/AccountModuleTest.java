package com.dtask.DTask;

import com.dtask.DTask.accountModule.controller.AccountCl;
import com.dtask.DTask.accountModule.service.IAccount;
import com.dtask.common.ResponseData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by zhong on 2020-3-16.
 */
@Component
@SpringBootTest
@RunWith(SpringRunner.class)
public class AccountModuleTest {
    @Autowired
    IAccount account;

    Logger logger = LoggerFactory.getLogger(AccountModuleTest.class);

    @Test
    public void login(){
        account.login("admin","e10adc3949ba59abbe56e057f20f883e");
    }
}
