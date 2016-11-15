package com.tzdr.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.alibaba.fastjson.JSONObject;

public class WeChatQrcodeUtil {
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
	 * 获取二维码的ticket
	 * @param param 自定义参数
	 * @param qrcodeType  二维码类型
	 * @param time   过期时间
	 * @return
	 */
	public static String getQrcodeTicket(String param,QrcodeType qrcodeType,Long time){
		JSONObject paramJson = getParam(param, qrcodeType, time);
		JSONObject tokentResult = WeChatQrcodeUtil.getToken();
		String token = tokentResult.getString("access_token");
		String str = HttpClientUtils.httpPost(WeChatQrcodeUtil.CREATE_QRCODE_TICKET_URL+"?access_token="+token+"" , paramJson.toJSONString() );
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
		String tokentResult = HttpClientUtils.httpGet(WeChatQrcodeUtil.TOKEN_URL, "grant_type=client_credential&appid="+WeChatQrcodeUtil.APPID+"&secret="+WeChatQrcodeUtil.SECRET+"");
		return JSONObject.parseObject(tokentResult);
	}
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
}
