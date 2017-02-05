package com.tzdr.api;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.alibaba.fastjson.JSONObject;
import com.tzdr.common.utils.Md5Utils;

/**
 * post test
 * @author zhouchen
 * 2015年6月5日 上午9:52:24
 */
public class TestApi {
	public static void main(String[] args) {
		PostMethod method = new PostMethod("http://c.tzdr.com:999/api/token/create");
		HttpClient client = new HttpClient();
		method.setRequestHeader("source", "web");
		JSONObject params = new JSONObject();
		params.put("loginName", "tzdr00001");
		params.put("password", Md5Utils.hash("tzdr2015"));

		try {
			RequestEntity requestEntity = new StringRequestEntity(
					params.toJSONString(), "application/json", "UTF-8");
			method.setRequestEntity(requestEntity);
			client.executeMethod(method);
			String SubmitResult = method.getResponseBodyAsString();
			System.out.println(SubmitResult);
		} catch (Exception e) {

		}
	}
}
