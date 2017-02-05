package com.tzdr.web.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class ValidationWeixin{
	  /** 
     * 将字节数组转换成16进制字符串 
     * @param b 
     * @return 
     */  
    private static String byte2hex(byte[] b) {  
        StringBuilder sbDes = new StringBuilder();  
        String tmp = null;  
        for (int i = 0; i < b.length; i++) {  
            tmp = (Integer.toHexString(b[i] & 0xFF));  
            if (tmp.length() == 1) {  
                sbDes.append("0");  
            }  
            sbDes.append(tmp);  
        }  
        return sbDes.toString();  
    }  
    private String encrypt(String strSrc) throws NoSuchAlgorithmException {
    	MessageDigest  digest = MessageDigest.getInstance("SHA-1"); 
        String strDes = null;  
        byte[] bt = strSrc.getBytes();  
        digest.update(bt);  
        strDes = byte2hex(digest.digest());  
        return strDes;  
    }  
    /** 
     * 校验请求的签名是否合法 
     *  
     * 加密/校验流程： 
     * 1. 将token、timestamp、nonce三个参数进行字典序排序 
     * 2. 将三个参数字符串拼接成一个字符串进行sha1加密 
     * 3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信 
     * @param signature 
     * @param timestamp 
     * @param nonce 
     * @return 
     * @throws NoSuchAlgorithmException 
     */  
    public boolean validate(String signature, String timestamp, String nonce) throws NoSuchAlgorithmException{  
        //1. 将token、timestamp、nonce三个参数进行字典序排序  
        String token = getToken();  
        String[] arrTmp = { token, timestamp, nonce };  
        Arrays.sort(arrTmp);  
        StringBuffer sb = new StringBuffer();  
        //2.将三个参数字符串拼接成一个字符串进行sha1加密  
        for (int i = 0; i < arrTmp.length; i++) {  
            sb.append(arrTmp[i]);  
        }  
        String expectedSignature = encrypt(sb.toString());  
        //3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信  
        if(expectedSignature.equals(signature)){  
            return true;  
        }  
        return false;  
    }  
    private String getToken(){  
        return "vsweixinpay";  
    }  
    public static void main(String[] args) throws NoSuchAlgorithmException {  
        
        String signature="4baef8677f95e505204ba03cdb8a4e99112eeb15";//加密需要验证的签名  
        String timestamp="1469698505";//时间戳  
        String nonce="1551871076";//随机数  
          
        ValidationWeixin wxDigest = new ValidationWeixin();  
        boolean bValid = wxDigest.validate(signature, timestamp, nonce);          
        if (bValid) {  
            System.out.println("token 验证成功!");  
        }else {  
            System.out.println("token 验证失败!");  
        }  
    }  
}
