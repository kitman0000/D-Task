package com.dtask.DTask.accountModule.provider;

import com.dtask.common.UserCommon;
import com.dtask.pluginsdk.accountModule.IUserProvider;
import org.springframework.stereotype.Component;

@Component
public class UserProviderImpl implements IUserProvider {

    @Override
    public int getUserID() {
        return UserCommon.getUserBo().getUserID();
    }
}
