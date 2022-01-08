package com.dtask.DTask.externalModule.service.impl;

import com.dtask.DTask.externalModule.bo.ApplicationBo;
import com.dtask.DTask.externalModule.bo.ExternalUrlBo;
import com.dtask.DTask.externalModule.dao.ExternalApplicationDao;
import com.dtask.DTask.externalModule.service.IExternalApplication;
import com.dtask.common.entity.ApplicationToken;
import com.dtask.common.util.AESUtil;
import com.dtask.common.util.EncodeUtil;
import com.dtask.common.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zhong on 2022-1-1.
 */

@Service
public class ExternalApplicationImpl implements IExternalApplication {

    @Value("${external.urlTokenSecretKey}")
    private String tokenSecretKey;

    @Autowired
    private ExternalApplicationDao externalApplicationDao;

    @Override
    public String login(String application, String password) {
        password = EncodeUtil.encodeHmac256(password,"DTask5710");
        ApplicationBo applicationBo = externalApplicationDao.getAvailableApplication(application,password);

        // Login failed
        if (applicationBo == null){
            return null;
        }

        List<ExternalUrlBo> externalUrlBoList = externalApplicationDao.getApplicationUrl(applicationBo.getId());
        ApplicationToken applicationToken = new ApplicationToken(){
            {
                setApplicationID(applicationBo.getId());
                setApiList(externalUrlBoList);
                setTimestamp(new Date());
            }
        };

        // Create the token by AES and Base64
        byte[] aesTokenBytes = AESUtil.encryptAES(JsonUtil.objectToJson(applicationToken),tokenSecretKey);
        String base64Token = EncodeUtil.encodeBase64(aesTokenBytes);

        return base64Token;
    }
}
