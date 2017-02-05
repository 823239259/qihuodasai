package com.tzdr.business.api.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.SpringUtils;
import com.tzdr.domain.dao.identity.IdentityCardHistoryDao;
import com.tzdr.domain.web.entity.IdentityCardHistory;

public class ApiJuheRealName {
	 public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
    //配置您申请的KEY
    public static final String APPKEY ="65bf28ddfd2952be38fa844ae8827843";
    public static final String url = "http://op.juhe.cn/idcard/query";
    public static void main(String[] args) {
    	Map<String, Object> params = new HashMap<>();
    	params.put("idcard", "510823199307044422");
    	params.put("realname", "何道清");
    	params.put("key", APPKEY);
    	try {
			String result = ApiJuheRealName.net(url, params, "GET");
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    public static Map<String, Object> setValidationDataMap(String realName,String idCard){
    	Map<String, Object> params = new HashMap<>();
    	params.put("idcard", idCard);
    	params.put("realname",realName);
    	params.put("key", APPKEY);
    	return params;
    }
    /**
     * 处理返回结果
     * @param result
     * @return
     */
    public static boolean handleResult(String result){
    	JSONObject jsonObject = JSONObject.parseObject(result);
    	String resultJson = jsonObject.getString("result");
    	boolean flag = false;
    	if(resultJson != null){
    		JSONObject resultData = JSONObject.parseObject(resultJson);
    		addIdentityCardHistory(resultData);
    		Integer res = resultData.getInteger("res");
    		if(res == 1){
    			flag = true;
    		}
    	}
    	return flag;
    }
    //{"reason":"成功","result":{"realname":"何道清","idcard":"510823199307044411","res":1},"error_code":0}  {"reason":"不是一个合法的身份证号或姓名","result":null,"error_code":210304}
    public static void addIdentityCardHistory(JSONObject jsonObject){
    	String idCard = jsonObject.getString("idcard");
    	String realName = jsonObject.getString("realname");
    	String res = jsonObject.getString("res");
    	IdentityCardHistory identityCardHistory =new IdentityCardHistory();
		identityCardHistory.setIdCard(idCard);
		identityCardHistory.setBirthday("");
		identityCardHistory.setResult(res);
		identityCardHistory.setName(realName);
		identityCardHistory.setSex("");
		identityCardHistory.setCreateTime(Dates.getCurrentLongDate());
		SpringUtils.getBean(IdentityCardHistoryDao.class).save(identityCardHistory);
    }
    /**
     * 验证用户实名信息
     * @param realName
     * @param idCard
     * @return
     */
    public static boolean  validationIdCard(String idCard,String realName){
    	Map<String, Object> params = setValidationDataMap(realName, idCard);
    	try {
			String result = ApiJuheRealName.net(url, params, "GET");
			return handleResult(result);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
    /**
    *
    * @param strUrl 请求地址
    * @param params 请求参数
    * @param method 请求方法
    * @return  网络请求字符串
    * @throws Exception
    */
   public static String net(String strUrl, Map<String, Object> params,String method) throws Exception {
       HttpURLConnection conn = null;
       BufferedReader reader = null;
       String rs = null;
       try {
           StringBuffer sb = new StringBuffer();
           if(method==null || method.equals("GET")){
               strUrl = strUrl+"?"+urlencode(params);
           }
           URL url = new URL(strUrl);
           conn = (HttpURLConnection) url.openConnection();
           if(method==null || method.equals("GET")){
               conn.setRequestMethod("GET");
           }else{
               conn.setRequestMethod("POST");
               conn.setDoOutput(true);
           }
           conn.setRequestProperty("User-agent", userAgent);
           conn.setUseCaches(false);
           conn.setConnectTimeout(DEF_CONN_TIMEOUT);
           conn.setReadTimeout(DEF_READ_TIMEOUT);
           conn.setInstanceFollowRedirects(false);
           conn.connect();
           if (params!= null && method.equals("POST")) {
               try {
                   DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                       out.writeBytes(urlencode(params));
               } catch (Exception e) {
               }
           }
           InputStream is = conn.getInputStream();
           reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
           String strRead = null;
           while ((strRead = reader.readLine()) != null) {
               sb.append(strRead);
           }
           rs = sb.toString();
       } catch (IOException e) {
           e.printStackTrace();
       } finally {
           if (reader != null) {
               reader.close();
           }
           if (conn != null) {
               conn.disconnect();
           }
       }
       return rs;
   }

   //将map型转为请求参数型
   public static String urlencode(Map<String,Object> data) {
       StringBuilder sb = new StringBuilder();
       for (Map.Entry<String,Object> i : data.entrySet()) {
           try {
               sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
           } catch (UnsupportedEncodingException e) {
               e.printStackTrace();
           }
       }
       return sb.toString();
   }
}
