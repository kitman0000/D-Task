package com.dtask.common.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Base64;

/**
 * Created by zhong on 2019-7-10.
 */
public class EncodeUtil {
    public static String encodeBase64(String str)
    {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    public static String decodeBase64(String str)
    {
        return new String(Base64.getDecoder().decode(str));
    }

    public static String encodeHmac256(String str,String secret)
    {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            return Base64.getEncoder().encodeToString(sha256_HMAC.doFinal(str.getBytes()));
        }catch (Exception ex)
        {
            return null;
        }
    }

    /**
     * BASE64 加密
     *
     * @param cleartext 明文
     * @return 密文
     */
    public static String encodeBase64(byte[] cleartext) {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String cipherText = base64Encoder.encode(cleartext);
        return cipherText.replace("\r","").replace("\n","");

    }

    /**
     * BASE64 解密
     *
     * @param cipherText 密文
     * @return 明文
     */
    public static byte[] decodeBase64ToByte(String cipherText) {
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] cipherTextBytes = base64Decoder.decodeBuffer(cipherText);
            return cipherTextBytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 解密错误返回 null
        return null;
    }

}
