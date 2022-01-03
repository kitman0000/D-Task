package com.dtask.DTask;

import com.dtask.common.util.AESUtil;
import com.dtask.common.util.EncodeUtil;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * Created by zhong on 2022-1-2.
 */
public class ExternalLoginTest {

    @Test
    public void login(){
        String token = "abc";
        String secretKey = "123";

        byte[] bytes = AESUtil.encryptAES(token,secretKey);
        String base64Token = EncodeUtil.encodeBase64(bytes);

        System.out.println(base64Token);

        bytes = EncodeUtil.decodeBase64ToByte(base64Token);
        try {
            System.out.println(new String(AESUtil.decryptAES(bytes,secretKey),"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
}
