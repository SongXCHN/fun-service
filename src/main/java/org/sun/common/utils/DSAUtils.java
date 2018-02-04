package org.sun.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import java.security.*;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by SongX on 2018/2/3.
 */
public class DSAUtils {
    private static final Logger logger = LoggerFactory.getLogger(DSAUtils.class);


    private static final String algorithm = "DSA";
    private static final int keySize = 1024;
    private static final String default_charset = "utf-8";

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
            DSAPrivateKey dsaPrivateKey = (DSAPrivateKey)keyPair.getPrivate();
            DSAPublicKey dsaPublicKey = (DSAPublicKey)keyPair.getPublic();
            return keyPair;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 签名
     *
     * @param inputStr
     * @param dsaPrivateKey
     * @return
     */
    public static String sign(String inputStr, DSAPrivateKey dsaPrivateKey) {
        try {
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(dsaPrivateKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
            Signature signature = Signature.getInstance(algorithm);
            signature.initSign(privateKey);
            signature.update(inputStr.getBytes(default_charset));
            return Base64Utils.encodeToString(signature.sign());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 验签
     *
     * @param inputStr
     * @param dsaPublicKey
     * @param sign
     * @return
     */
    public static boolean verify(String inputStr, DSAPublicKey dsaPublicKey, String sign) {
        try {
            X509EncodedKeySpec x509keySpec = new X509EncodedKeySpec(dsaPublicKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            PublicKey publicKey = keyFactory.generatePublic(x509keySpec);
            Signature signature = Signature.getInstance(algorithm);
            signature.initVerify(publicKey);
            signature.update(inputStr.getBytes(default_charset));
            return signature.verify(Base64Utils.decodeFromString(sign));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }




    public static void main(String[] args) {
        KeyPair keyPair = generateKeyPair();
        DSAPrivateKey dsaPrivateKey = (DSAPrivateKey)keyPair.getPrivate();
        DSAPublicKey dsaPublicKey = (DSAPublicKey)keyPair.getPublic();

        String sign = sign("1234", dsaPrivateKey);

        System.out.println(Base64Utils.encodeToString(dsaPublicKey.getEncoded()));
        System.out.println(Base64Utils.encodeToString(dsaPrivateKey.getEncoded()));
        System.out.println(sign);
        System.out.println(verify("1234", dsaPublicKey, sign));
    }
}
