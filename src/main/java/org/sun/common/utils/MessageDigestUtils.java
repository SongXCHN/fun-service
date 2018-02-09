package org.sun.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * 摘要算法：MD5 < SHA256 < SHA512 (速度)
 * Created by SongX on 2018/2/9.
 */
public class MessageDigestUtils {

    private static final Logger logger = LoggerFactory.getLogger(MessageDigestUtils.class);

    private static final String MD5 = "MD5";
    private static final String SHA_256 = "SHA-256";
    private static final String SHA_512 = "SHA-512";
    private static final String defaultCharset = "utf-8";

    public static String MD5(String str) {
        return hash(str, MD5);
    }

    public static String SHA256(String str) {
        return hash(str, SHA_256);
    }

    public static String SHA512(String str) {
        return hash(str, SHA_512);
    }


    private static String hash(String str, String algorithm) {
        try {
            // 获得 MessageDigest 对象
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            byte[] btInput = str.getBytes(defaultCharset);
            // 使用指定的字节更新摘要
            messageDigest.update(btInput);
            // 获得密文
            byte[] byteBuffer = messageDigest.digest();
            return hexString(byteBuffer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 把密文转换成十六进制的字符串形式
     *
     * @param mdStr
     * @return
     */
    private static String hexString(byte[] mdStr) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        StringBuffer stringBuffer = new StringBuffer();
        for (byte byte0 : mdStr) {
            stringBuffer.append(hexDigits[byte0 >>> 4 & 0xf]);
            stringBuffer.append(hexDigits[byte0 & 0xf]);
        }
        return stringBuffer.toString();
    }

}
