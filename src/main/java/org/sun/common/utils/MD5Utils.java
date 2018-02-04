package org.sun.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sun.common.constant.AlgorithmConstant;

import java.security.MessageDigest;

/**
 * Created by SongX on 2018/2/3.
 */
public class MD5Utils {
    private static final Logger logger = LoggerFactory.getLogger(MD5Utils.class);

    private static final String algorithm = "MD5";
    private static final String default_charset = "utf-8";

    public static String getMD5(String str) {
        try {
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance(algorithm);
            byte[] btInput = str.getBytes(default_charset);
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] mdStr = mdInst.digest();
            return hexString(mdStr);
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


    public static void main(String[] args) {

        System.out.println(getMD5("202CB962AC59075B964B07152D234B70"));
    }
}
