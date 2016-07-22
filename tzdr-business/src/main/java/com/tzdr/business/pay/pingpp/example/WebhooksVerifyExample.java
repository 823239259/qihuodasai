package com.tzdr.business.pay.pingpp.example;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.X509EncodedKeySpec;
import org.apache.commons.codec.binary.Base64;

import com.tzdr.business.pay.pingpp.config.Config;


/**
 * 验证请求
 * @author HEDAOQING
 * 2016.07.19
 */
public class WebhooksVerifyExample {
	/**
	 * 获得公钥
	 * @return
	 * @throws Exception
	 */
	public static PublicKey getPubKey() throws Exception {
		String pubKeyString =Config.PUBLIC_KEY;
		pubKeyString = pubKeyString.replaceAll("(-+BEGIN PUBLIC KEY-+\\r?\\n|-+END PUBLIC KEY-+\\r?\\n?)", "");
        byte[] keyBytes = Base64.decodeBase64(pubKeyString);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(spec);
		return publicKey;
	}
	public static void main(String[] args) {
		try {
			System.out.println(WebhooksVerifyExample.getPubKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 验证签名
	 * @param dataString
	 * @param signatureString
	 * @param publicKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	public static boolean verifyData(String dataString, String signatureString, PublicKey publicKey)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
        byte[] signatureBytes = Base64.decodeBase64(signatureString);
        Signature signature = Signature.getInstance("SHA256withRSA");
		signature.initVerify(publicKey);
		signature.update(dataString.getBytes("UTF-8"));
		return signature.verify(signatureBytes);
	}
}
