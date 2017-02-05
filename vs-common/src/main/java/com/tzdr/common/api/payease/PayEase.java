package com.tzdr.common.api.payease;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.tzdr.common.api.payease.util.PayEaseConfigUtil;
import com.tzdr.common.api.payease.util.PayEaseMd5;
import com.tzdr.common.api.payease.vo.PayEaseParams;
import com.umpay.api.exception.RetDataException;


/**
 * 首信易支付
 * @zhouchen
 * 2015年10月13日
 */
public class PayEase {
	public static final Logger logger = LoggerFactory.getLogger(PayEase.class);
	
	/**
	 * 接口异常码
	 */
	public static final String INTERFACE_EXCEPTION_CODE="100000";
	
	
	/**
	 * 用户提现付款成功
	 */
	public static final String WITHDRAW_PAY_SUCCESS="1";
	
	/**
	 * 用户提现付款失败
	 */
	public static final String WITHDRAW_PAY_FAIL="3";
	
	/**
	 * 提现接口调用成功
	 */
	public static final int WITHDRAW_INTERFACE_SUCCESS=0;
	/**
	 * 银联支付代码
	 */
	public static final String WAP_PAY_PMODE="126";
	
	private static PayEase instance;
	
	//支付接口地址
	private String pay_url;
	
	//提现接口地址
	private String withdraw_url;
	
	//提现状态查询接口
	private String withdraw_status_url;
	
	public static synchronized PayEase getInstance() {
		if (instance == null) {
			instance = new PayEase();
		}
		return instance;
	}
	
	public PayEase() {
		pay_url = PayEaseConfigUtil.getContext("pay_url");
		withdraw_url = PayEaseConfigUtil.getContext("withdraw_url");
		withdraw_status_url = PayEaseConfigUtil.getContext("withdraw_status_url");
		
	}
	
	public  PayEaseParams getPayParams(PayEaseParams payEaseParams) throws IOException{
		StringBuffer vdata = new StringBuffer(payEaseParams.getVmoneytype());
		vdata.append(payEaseParams.getVymd());
		vdata.append(payEaseParams.getVamount());
		vdata.append(payEaseParams.getVrcvname());
		vdata.append(payEaseParams.getVorid());
		vdata.append(payEaseParams.getVmid());
		vdata.append(payEaseParams.getVurl());
		PayEaseMd5 payEaseMd5 = new PayEaseMd5("");
		payEaseMd5.hmac_Md5(vdata.toString(),payEaseParams.getSecret());
		byte b[]= payEaseMd5.getDigest();
		String digestString = payEaseMd5.stringify(b) ;
		//System.out.println (digestString) ;
		payEaseParams.setVmd5info(digestString);
		payEaseParams.setPayurl(pay_url);
		return payEaseParams;
	}
	
	/**
	 * @param withDrawUrl
	 *            取款支付 URL
	 * @return status 0:正常  1:错误
	 * @throws RetDataException
	 * @throws IOException
	 * @param vdata 分帐信息总行数|分帐总金额|$收方帐号|收方帐户名|收方开户行|收方省份|收方城市|付款金额| 客户标识|联行号
	 */
	public JSONObject withdraw(String vmid,String secret,String vdata){
		JSONObject reslut = new JSONObject();
		try {
			PostMethod method = new PostMethod(withdraw_url);
			HttpClient client = new HttpClient();
			client.getParams().setContentCharset("UTF-8");
			PayEaseMd5 payEaseMd5 = new PayEaseMd5("");
			payEaseMd5.hmac_Md5(URLEncoder.encode(vmid+vdata),secret);
			byte b[]= payEaseMd5.getDigest();
			String digestString = payEaseMd5.stringify(b) ;
			method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");
			NameValuePair[] data = { new NameValuePair("v_mid",vmid), new NameValuePair("v_data",vdata),
					new NameValuePair("v_mac",digestString), new NameValuePair("v_version",PayEaseConfigUtil.getContext("payease_v_version")), };
			logger.info("--------------易支付提现接口请求参数----------------------------------------"
					+ "\n-------------------v_mid="+vmid
					+"\n--------------------v_data="+vdata
					+"\n--------------------v_mac="+digestString
					+"\n--------------------v_version="+PayEaseConfigUtil.getContext("payease_v_version")
					+"\n---------------易支付提现接口请求参数------------------------------------------");
			method.setRequestBody(data);
			client.executeMethod(method);
			String SubmitResult = method.getResponseBodyAsString();
			Document doc = DocumentHelper.parseText(SubmitResult);
			Element root = doc.getRootElement();
			reslut.put("vmid", vmid);
			reslut.put("secret", secret);
			reslut.put("status",root.elementText("status"));
			reslut.put("desc",root.elementText("statusdesc"));
			logger.info("易支付提现接口调用返回结果：\n ---------------------------\n"+SubmitResult+"\n------------------------------");
		} catch (Exception e) {
			logger.info("提现接口异常，数据vdata="+vdata,e);
			reslut.put("status",INTERFACE_EXCEPTION_CODE);
			reslut.put("desc","提现接口异常！请稍候再试。");
		}
		return reslut;
	}
	
	
	/**
	 * 查询提现订单状态
	 * @param vmid  商户号
	 * @param vdata 客户标志对私以C开头 即C+订单号
	 * @return
	 * @throws Exception
	 * 
	 * 处理状态标识，
		0－未处理，
		1－已成功
		2－处理中
		3－已失败
		4-待处理（自动处理程序状态）
		8-没有该用户标识对应的代付记录
		9－查询失败
	 */
	public JSONObject queryDrawStatus(String vmid,String secret,String vdata){
		JSONObject reslut = new JSONObject();
		try {
			PostMethod method = new PostMethod(withdraw_status_url);
			HttpClient client = new HttpClient();
			client.getParams().setContentCharset("UTF-8");
			PayEaseMd5 payEaseMd5 = new PayEaseMd5("");
			payEaseMd5.hmac_Md5(vmid+vdata,secret);
			byte b[]= payEaseMd5.getDigest();
			String digestString = payEaseMd5.stringify(b) ;
			method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=UTF-8");
			NameValuePair[] data = {new NameValuePair("v_mid",vmid), new NameValuePair("v_data",vdata),
					new NameValuePair("v_mac",digestString), new NameValuePair("v_version",PayEaseConfigUtil.getContext("payease_v_version")), };
			method.setRequestBody(data);
		
			client.executeMethod(method);
			String SubmitResult = method.getResponseBodyAsString();
			Document doc = DocumentHelper.parseText(SubmitResult);
			Element root = doc.getRootElement();
			reslut.put("status",root.elementText("status"));
			reslut.put("desc",root.elementText("statusdesc"));
			logger.info("易支付提现状态查询接口调用返回结果：\n ----------[订单ID="+vdata+"]----------------\n"+SubmitResult+"\n------------------------------");
		} catch (Exception e) {
			logger.info("订单号【"+vdata+"】,提现状态接口查询异常.",e);
			reslut.put("status",INTERFACE_EXCEPTION_CODE);
			reslut.put("desc","提现状态查询接口异常！请稍候再试。");
		}
		return reslut;
	}
	
}


