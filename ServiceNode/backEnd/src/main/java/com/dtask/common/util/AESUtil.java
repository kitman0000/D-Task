package com.dtask.common.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * Created by zhong on 2021-1-11.
 */
public class AESUtil {
    // 加密算法
    private static final String ENCRY_ALGORITHM = "AES";
    // 加密算法/加密模式/填充类型
    private static final String CIPHER_MODE = "AES/CBC/PKCS7Padding";
    // 设置iv偏移量，ECB加密模式不需要设置 iv 偏移量
    private static final String IV = "0000000000000000";
    // 设置加密字符集
    private static final String CHARACTER = "UTF-8";
    // 加密密码长度。默认 16 byte * 8 = 128 bit
    private static final int PWD_SIZE = 16;

    static {
        // 添加 AES/CBC/PKCS7Padding 支持
        Security.addProvider(new BouncyCastleProvider());
    }



    /**
     * 密码长度不足补"0"
     */
    private static byte[] pwdHandler(String password) throws UnsupportedEncodingException {
        byte[] data = null;
        if (password == null) {
            password = "";
        }
        StringBuffer sb = new StringBuffer(PWD_SIZE);
        sb.append(password);
        while (sb.length() < PWD_SIZE) {
            sb.append("0");
        }
        if (sb.length() > PWD_SIZE) {
            sb.setLength(PWD_SIZE);
        }
        data = sb.toString().getBytes(CHARACTER);
        return data;
    }

    /**
     * AES 加密
     *
     * @param cleartext 明文
     * @param key       密钥
     * @return
     */
    public static byte[] encryptAES(String cleartext, String key) {
        try {
            // 获取加密密钥
            SecretKeySpec keySpec = new SecretKeySpec(pwdHandler(key), ENCRY_ALGORITHM);
            // 获取Cipher实例
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);

            // 查看数据块位数 默认为16（byte） * 8 =128 bit
            // System.out.println("数据块位数(byte)：" + cipher.getBlockSize());

            // 初始化Cipher实例。设置执行模式以及加密密钥
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(IV.getBytes(CHARACTER)));
            // 执行
            byte[] cipherTextBytes = cipher.doFinal(cleartext.getBytes(CHARACTER));
            return cipherTextBytes;
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES 解密
     *
     * @param ciphertext 密文
     * @param key        密钥
     * @return
     */
    public static byte[] decryptAES(byte[] ciphertext, String key) {
        try {
            // 获取解密密钥
            SecretKeySpec keySpec = new SecretKeySpec(pwdHandler(key), ENCRY_ALGORITHM);
            // 获取Cipher实例
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);

            // 查看数据块位数 默认为16（byte） * 8 =128 bit
            // System.out.println("数据块位数(byte)：" + cipher.getBlockSize());

            // 初始化Cipher实例。设置执行模式以及加密密钥
            cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(IV.getBytes(CHARACTER)));
            // 执行
            byte[] clearTextBytes = cipher.doFinal(ciphertext);
            return clearTextBytes;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 解密错误 返回 null
        return null;
    }
}
