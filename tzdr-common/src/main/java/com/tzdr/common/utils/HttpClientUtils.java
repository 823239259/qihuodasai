package com.tzdr.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class HttpClientUtils {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
	
	public static final String ERRCODE = "errcode";
	public static final String ERRMSG = "errmsg";
	
	public static String getErrorMsg(int errorCode) {
		String msg = "未知错误";
		switch (errorCode) {
		case -1:
			msg = "系统繁忙";
			break;
		case 0:
			msg = "请求成功";
			break;
		
		}
		return msg;
	}

	/**
	 * 使用http协议访问一个url
	 * @param strUrl
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws JSONException 
	 */
	public static String httpRequest2Json(String strUrl) throws UnsupportedEncodingException, JSONException {
		String result = "";
		String errmsg = null;

		try {
			//避免出现错误：
			//javax.net.ssl.SSLProtocolException: handshake alert: unrecognized_name
			System.setProperty("jsse.enableSNIExtension", "false");
			
			URL url = new URL(strUrl);
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestProperty("Content-type", "text/html");
			httpConnection.setRequestProperty("Accept-Charset", "utf-8");
			httpConnection.setRequestMethod("GET");
			httpConnection.setDoInput(true);
			httpConnection.setAllowUserInteraction(true);
			httpConnection.connect();
			int responseCode = httpConnection.getResponseCode();
			if (responseCode >= 400) {
				errmsg = "responseCode: " + responseCode;
			} else {
				InputStream inputStream = httpConnection.getInputStream();
				InputStreamReader streamReader = new InputStreamReader(inputStream, "utf-8");
				BufferedReader reader = new BufferedReader(streamReader);
				
				StringBuilder sb = new StringBuilder();
				String line = null;
				do {
					line = reader.readLine();
					if (line != null) {
						sb.append(line);
					}
				} while (line != null);
				result = sb.toString();
				
				reader.close();
				streamReader.close();
				inputStream.close();
			}
			httpConnection.disconnect();
		} catch (MalformedURLException e) {
			errmsg = e.getMessage();
		} catch (IOException e) {
			errmsg = e.getMessage();
		}

		if (errmsg == null) {
			return result;
		} else {
			logger.error(errmsg);
			logger.error(strUrl);
			
			JSONObject json = new JSONObject();
			json.put(ERRCODE, -1);
			json.put(ERRMSG, errmsg);
			return json.toString();
		}
	}
	
	/**
	 * 使用http协议访问一个url
	 * @param strUrl
	 * @param strContent
	 * @return
	 * @throws JSONException 
	 */
	public static String httpRequest2Json(String strUrl, String strContent) throws JSONException {
		String result = "";
		String errmsg = null;

		try {
			//避免出现错误：
			//javax.net.ssl.SSLProtocolException: handshake alert: unrecognized_name
			System.setProperty("jsse.enableSNIExtension", "false");
			
			URL url = new URL(strUrl);
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestProperty("Content-type", "text/html");
			httpConnection.setRequestProperty("Accept-Charset", "utf-8");
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setAllowUserInteraction(true);
			//httpConnection.connect();
			
			java.io.OutputStream output = httpConnection.getOutputStream();
			output.write(strContent.getBytes("UTF-8"));
			output.flush();
			output.close();
			
			int responseCode = httpConnection.getResponseCode();
			if (responseCode >= 400) {
				errmsg = "responseCode: " + responseCode;
			} else {
				InputStream inputStream = httpConnection.getInputStream();
				InputStreamReader streamReader = new InputStreamReader(inputStream, "utf-8");
				BufferedReader reader = new BufferedReader(streamReader);
				
				StringBuilder sb = new StringBuilder();
				String line = null;
				do {
					line = reader.readLine();
					if (line != null) {
						sb.append(line);
					}
				} while (line != null);
				result = sb.toString();
				
				reader.close();
				streamReader.close();
				inputStream.close();
			}
			httpConnection.disconnect();
		} catch (MalformedURLException e) {
			errmsg = e.getMessage();
		} catch (IOException e) {
			errmsg = e.getMessage();
		}

		if (errmsg == null) {
			return result;
		} else {
			logger.error(errmsg);
			logger.error(strUrl);
			logger.error(strContent);
			
			JSONObject json = new JSONObject();
			json.put(ERRCODE, -1);
			json.put(ERRMSG, errmsg);
			return json.toString();
		}
	}
	

}
