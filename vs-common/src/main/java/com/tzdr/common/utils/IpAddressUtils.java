package com.tzdr.common.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
* @Description: TODO(ip归属信息)
* @ClassName: IpAddressUtils
* @author wangpinqun
* @date 2015年2月25日 下午8:22:14
 */
public class IpAddressUtils {
	
	private static Logger log = LoggerFactory.getLogger(IpAddressUtils.class);

	/**
	* @Description: TODO(获取IP归属市区)
	* @Title: getAffiliationCity
	* @param ip    
	* @param encoding  
	* @return String    返回类型
	 */
	public static String getAffiliationCity(String ip, String encoding){
		
		try {
			String returnStr = IpAddressUtils.getAffiliationInfo("ip="+ip, encoding);
			
			if(returnStr != null){

				Object obj = JSONObject.parse(returnStr);
				
				JSONObject json =  obj == null ? null : (JSONObject)obj;

				if(json != null && "0".equals(json.get("code").toString())){

					Object dataObj = json.get("data");
					
					JSONObject data = dataObj == null ? null : (JSONObject)dataObj;
					
					String city = data == null ? null : decodeUnicode(data.getString("city"));  //IP归属市区

					return StringUtil.isBlank(city) ? null : city;

				}else{
					return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error::{}", e.getMessage());
		}
		return null;
	}
	
	/**
	* @Description: TODO(获取ip归属信息)
	* @Title: getAffiliationInfo
	* @param params  查询条件 如：ip=58.17.245.90
	* @param encoding  字符编号 如：utf-8
	* @return String    返回类型
	 */
	public static String getAffiliationInfo(String params, String encoding){

		URL url = null;

		HttpURLConnection connection = null;

		try {
			String path = ConfUtil.getContext("ip.address");
			
			url = new URL(path);

			connection = (HttpURLConnection)url.openConnection();// 新建连接实例

			connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫�?

			connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫�?

			connection.setDoInput(true);// 是否打开输出�? true|false

			connection.setDoOutput(true);// 是否打开输入流true|false

			connection.setRequestMethod("POST");// 提交方法POST|GET

			connection.setUseCaches(false);// 是否缓存true|false

			connection.connect();// 打开连接端口

			DataOutputStream out = new DataOutputStream(connection.getOutputStream());

			out.writeBytes(params);

			out.flush();

			out.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),encoding));
			
			StringBuffer buffer = new StringBuffer();
			
			String line = "";
			
			while ((line = reader.readLine())!= null) {
				buffer.append(line);
			}
			
			reader.close();
			
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error::{}", e.getMessage());
		}finally{
			if(connection != null){
				connection.disconnect();//关闭连接
			}
		}
		return null;
	}
	
	/**
	* @Description: TODO(字符转码)
	* @Title: decodeUnicode
	* @param theString
	* @return String    返回类型
	 */
	public static String decodeUnicode(String theString){

		char aChar;

		int len = theString.length();

		StringBuffer buffer = new StringBuffer(len);

		for (int i = 0; i < len;) {

			aChar = theString.charAt(i++);

			if(aChar == '\\'){

				aChar = theString.charAt(i++);

				if(aChar == 'u'){

					int val = 0;

					for(int j = 0; j < 4; j++){

						aChar = theString.charAt(i++);

						switch (aChar) {

						case '0':

						case '1':

						case '2':

						case '3':

						case '4':

						case '5':

						case '6':

						case '7':

						case '8':

						case '9':

							val = (val << 4) + aChar - '0';

							break;

						case 'a':

						case 'b':

						case 'c':

						case 'd':

						case 'e':

						case 'f':

							val = (val << 4) + 10 + aChar - 'a';

							break;

						case 'A':

						case 'B':

						case 'C':

						case 'D':

						case 'E':

						case 'F':

							val = (val << 4) + 10 + aChar - 'A';

							break;

						default:

							throw new IllegalArgumentException(

									"Malformed      encoding.");
						}
					}
					buffer.append((char) val);

				}else{

					if(aChar == 't'){

						aChar = '\t';
					}

					if(aChar == 'r'){

						aChar = '\r';
					}

					if(aChar == 'n'){

						aChar = '\n';
					}

					if(aChar == 'f'){

						aChar = '\f';

					}

					buffer.append(aChar);
				}

			}else{

				buffer.append(aChar);
			}
		}
		return buffer.toString();
	}
}

