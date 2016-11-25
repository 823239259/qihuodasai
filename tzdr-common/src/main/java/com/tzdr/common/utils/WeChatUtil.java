package com.tzdr.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.alibaba.fastjson.JSONObject;

public class WeChatUtil {
	public enum QrcodeType{
		/**
		 * 临时二维码
		 */
		QR_SCENE,
		/**
		 * 永久二维码
		 */
		QR_LIMIT_STR_SCENE 
	}
	/**
	 * appid
	 */
	private static String APPID = null;
	/**
	 * SECRET
	 */
	private static String SECRET = null;
	/**
	 * 获取token URL
	 */
	public static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
	/**
	 * 二维码的ticket
	 */
	public static final String CREATE_QRCODE_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
	/**
	 * 获取二维码
	 */
	public static final String CREATE_QRCODE_URL = "https://mp.weixin.qq.com/cgi-bin/showqrcode";
	/**
	 * 获取用户基本信息url
	 */
	public static final String GET_WECHAT_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info";
	
	/**
	 * 获取二维码的ticket
	 * @param param 自定义参数
	 * @param qrcodeType  二维码类型
	 * @param time   过期时间
	 * @return
	 */
	public static String getQrcodeTicket(String param,QrcodeType qrcodeType,Long time){
		JSONObject paramJson = getParam(param, qrcodeType, time);
		JSONObject tokentResult = WeChatUtil.getToken();
		String token = tokentResult.getString("access_token");
		String str = HttpClientUtils.httpPost(WeChatUtil.CREATE_QRCODE_TICKET_URL+"?access_token="+token+"" , paramJson.toJSONString() );
		JSONObject ticketJson = JSONObject.parseObject(str);
		try {
			return URLEncoder.encode(ticketJson.getString("ticket"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取token
	 * @return
	 */
	public static JSONObject getToken(){
		String tokentResult = HttpClientUtils.httpGet(WeChatUtil.TOKEN_URL, "grant_type=client_credential&appid="+WeChatUtil.APPID+"&secret="+WeChatUtil.SECRET+"");
		return JSONObject.parseObject(tokentResult);
	}
	/**
	 * 创建获取二维码的参数
	 * @param param
	 * @param qrcodeType
	 * @param time
	 * @return
	 */
	private static JSONObject getParam(String param,QrcodeType qrcodeType,Long time){
		JSONObject sceneIdJson = new JSONObject();
		sceneIdJson.put("scene_id", param);
		JSONObject sceneJson = new JSONObject();
		sceneJson.put("scene", sceneIdJson);
		JSONObject paramJson   = new JSONObject();
		paramJson.put("action_info", sceneJson);
		paramJson.put("action_name", qrcodeType.name());
		if(qrcodeType ==  QrcodeType.QR_SCENE){
			paramJson.put("expire_seconds", time);
		}
		return paramJson;
	}
	/**
	 * 获取用户基本信息
	 * @param openId
	 * @return
	 */
	public static String getWechatUser(String openId){
		JSONObject result = WeChatUtil.getToken();
		String token = result.getString("access_token");
		return HttpClientUtils.httpGet(WeChatUtil.GET_WECHAT_USER_INFO, "access_token="+token+"&openid="+openId+"");
	}
	public static String getAPPID() {
		return APPID;
	}
	public static void setAPPID(String aPPID) {
		APPID = aPPID;
	}
	public static String getSECRET() {
		return SECRET;
	}
	public static void setSECRET(String sECRET) {
		SECRET = sECRET;
	}
	private static boolean isNotEmojiCharacter(char codePoint) 
	{ 
		return (codePoint == 0x0) || 
		(codePoint == 0x9) || 
		(codePoint == 0xA) || 
		(codePoint == 0xD) || 
		((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || 
		((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || 
		((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)); 
	} 
	/** 
	* 过滤emoji 或者 其他非文字类型的字符 
	* @param source 
	* @return 
	*/ 
	public static String filterEmoji(String source) { 
		int len = source.length(); 
		StringBuilder buf = new StringBuilder(len); 
		for (int i = 0; i < len; i++) 
		{ 
			char codePoint = source.charAt(i); 
			if (isNotEmojiCharacter(codePoint)) 
			{ 
				buf.append(codePoint); 
			} else{
				buf.append("*");
		
			}
		} 
		return buf.toString(); 
	}
}
