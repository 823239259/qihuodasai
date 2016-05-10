package com.tzdr.api.thread;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.utils.Exceptions;

/**
 * 
 * <B>说明: </B>同步注册p2p账户 线程
 * @zhouchen
 * 2016年1月20日
 */
public class RegistP2pThread extends Thread {
	
	public  final int REGIST_SUCCESS_ERROR=-1;
	public  final String REGIST_SUCCESS_MSG="注册成功";
	
	private static Logger log = LoggerFactory
			.getLogger(RegistP2pThread.class);
	
	private String mobile;
	private String pwd;
	private String salt;

	public RegistP2pThread(String mobile,String pwd,String salt){
		this.mobile=mobile;
		this.pwd=pwd;
		this.salt=salt;
	}

	public void run() {
		PostMethod method = new PostMethod(ConfUtil.getContext("p2p.server.regist.url"));
		HttpClient client=new HttpClient();	
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType",
				"application/x-www-form-urlencoded;charset=UTF-8");
		NameValuePair[] data = {
				new NameValuePair("mobile",this.mobile),
				new NameValuePair("pwd",this.pwd),
				new NameValuePair("salt",this.salt)};

		method.setRequestBody(data);
		Exception ex = null;
		for (int i = 0; i < 3; i++) {
			ex = null;
			try {
				client.executeMethod(method);
				String SubmitResult = method.getResponseBodyAsString();
				log.info("p2p return registResult:"+SubmitResult);
				if (StringUtil.isBlank(SubmitResult)){
					EmailExceptionHandler.getInstance().HandleHintWithData("用户【"+this.mobile+"】p2p同步注册时，注册失败！","RegistP2pThread.run()","method.status.code="+method.getStatusCode()+",method.status.text="+method.getStatusText());
					break;
				}
				JSONObject result = (JSONObject) JSONObject.parse(SubmitResult);
				if (ObjectUtil.equals(null, result) 
						|| result.getIntValue("error")!=this.REGIST_SUCCESS_ERROR
						|| !StringUtil.equals(result.getString("msg"),this.REGIST_SUCCESS_MSG)){
					EmailExceptionHandler.getInstance().HandleHintWithData("用户【"+this.mobile+"】p2p同步注册时，注册失败！","RegistP2pThread.run()",SubmitResult);
				}
				break;
			}
			catch(Exception e){
				ex = e;
			}
		}
		if(ex != null){
			EmailExceptionHandler.getInstance().HandleHintWithData("用户【"+this.mobile+"】p2p同步注册时，接口处出现异常，请核实！","RegistP2pThread.run()",Exceptions.getStackTraceAsString(ex));
		}
	}
}
