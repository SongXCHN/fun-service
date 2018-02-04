package org.sun.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by SongX on 2018/2/4
 */
public class RSAUtils {
    private static final Logger logger = LoggerFactory.getLogger(RSAUtils.class);


    private static final String algorithm = "RSA";
    private static final int keySize = 1024;
    private static final String defaultCharset = "utf-8";


    /**
     * 随机生成密钥
     *
     * @return
     */
    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algorithm);
            keyGen.initialize(keySize);
            KeyPair keyPair = keyGen.genKeyPair();
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
            return keyPair;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 公钥加密
     *
     * @param plainText
     * @param rsaPublicKey
     * @return
     */
    public static String encrypt(String plainText, RSAPublicKey rsaPublicKey) {
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64Utils.encodeToString(cipher.doFinal(plainText.getBytes(defaultCharset)));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 私钥解密
     *
     * @param decodedText
     * @param rsaPrivateKey
     * @return
     */
    public static String decrypt(String decodedText, RSAPrivateKey rsaPrivateKey) {
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(cipher.doFinal(Base64Utils.decodeFromString(decodedText)));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static void main(String[] args) {
        String plainText = "明天huibbkbkbibguguyguyfuf";
        KeyPair keyPair = generateKeyPair();
        String decodedText = encrypt(plainText, (RSAPublicKey) keyPair.getPublic());
        System.out.println("decodedText :" + decodedText);
        System.out.println("plainText :" + decrypt(decodedText, (RSAPrivateKey) keyPair.getPrivate()));
    }
}
