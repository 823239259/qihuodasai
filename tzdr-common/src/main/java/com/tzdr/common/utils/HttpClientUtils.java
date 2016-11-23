package com.tzdr.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

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
	 /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String httpGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static InputStream httpGetInputStream(String url, String param) {
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
           return connection.getInputStream();
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String httpPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    

}
