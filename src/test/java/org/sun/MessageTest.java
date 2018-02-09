package org.sun;


import org.sun.common.utils.MessageDigestUtils;

/**
 * Created by SongX on 2018/2/9.
 */
public class MessageTest {
    public static void main(String[] args) {

        System.out.println(MessageDigestUtils.MD5("233"));
        System.out.println(MessageDigestUtils.SHA256(("233")));
        System.out.println(MessageDigestUtils.SHA512(("233")));
    }

}
