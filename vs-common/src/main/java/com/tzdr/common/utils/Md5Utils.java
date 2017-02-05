package com.tzdr.common.utils;

import java.security.MessageDigest;

import jodd.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Md5Utils {

    private static final Logger LOGGER = LoggerFactory.getLogger(Md5Utils.class);


    private static byte[] md5(String s) {
        MessageDigest algorithm;
        try {
            algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(s.getBytes("UTF-8"));
            byte[] messageDigest = algorithm.digest();
            return messageDigest;
        } catch (Exception e) {
            LOGGER.error("MD5 Error...", e);
        }
        return null;
    }

    private static final String toHex(byte hash[]) {
        if (hash == null) {
            return null;
        }
        StringBuffer buf = new StringBuffer(hash.length * 2);
        int i;

        for (i = 0; i < hash.length; i++) {
            if ((hash[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString(hash[i] & 0xff, 16));
        }
        return buf.toString();
    }

    public static String hash(String s) {
        try {
            return new String(toHex(md5(s)).getBytes("UTF-8"), "UTF-8");
        } catch (Exception e) {
            LOGGER.error("not supported charset...{}", e);
            return s;
        }
    }


    public static void main(String[] args) {
		String  str = "ff808081532fd63a01532fdf8d940001";
		String ksksk = Base64.encodeToString(str.getBytes());
		System.out.println(ksksk);
		
		System.out.println(Base64.decodeToString(ksksk));
	}
}
