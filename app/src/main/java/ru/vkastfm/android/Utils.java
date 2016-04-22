package ru.vkastfm.android;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by german on 21.04.16.
 */
public final class Utils {
    public static String md5(String s) {
        StringBuilder md5Builder = new StringBuilder();
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(s.getBytes("UTF-8"));
            for (byte b : digest) {
                md5Builder.append(String.format("%02x", b & 0xff));
            }
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ignored) {
        }
        return md5Builder.toString();
    }
}
