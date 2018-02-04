package org.sun.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * Created by SongX on 2018/2/4
 */
public class AESUtils {
    private static final Logger logger = LoggerFactory.getLogger(AESUtils.class);

    private static final String algorithm = "AES";
    public static final String ecbPkcs5 = "AES/ECB/PKCS5Padding";
    private static final String defaultCharset = "utf-8";

    /**
     * 随机生成密钥
     *
     * @return
     */
    public static String generateAESSecretKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
            keyGenerator.init(new SecureRandom());
            return Base64Utils.encodeToString(keyGenerator.generateKey().getEncoded());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 还原密钥
     *
     * @param secretKey
     * @return
     */
    public static SecretKey restoreSecretKey(String secretKey) {
        byte[] secretBytes = Base64Utils.decodeFromString(secretKey);
        return new SecretKeySpec(secretBytes, algorithm);
    }

    /**
     * 使用ECB模式加密
     *
     * @param plainText
     * @param
     * @return
     */
    public static String ecbEncode(String plainText, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance(ecbPkcs5);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64Utils.encodeToString(cipher.doFinal(plainText.getBytes(defaultCharset)));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 使用ECB解密
     *
     * @param decodedText
     * @param secretKey
     * @return
     */
    public static String aesEcbDecode(String decodedText, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance(ecbPkcs5);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64Utils.decodeFromString(decodedText)));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static void main(String[] args) {
        String plainText = "明天huibbkbkbibguguyguyfuf";
        String AESSecretKey = generateAESSecretKey();
        System.out.println("AESSecretKey :" + AESSecretKey);
        SecretKey secretKey = restoreSecretKey(AESSecretKey);
        String decodedText = ecbEncode(plainText, secretKey);
        System.out.println("decodedText :" + decodedText);
        System.out.println("plainText :" + aesEcbDecode(decodedText, secretKey));

    }

}
