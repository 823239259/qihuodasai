package com.tzdr.business.pay.pingpp.example;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.pingplusplus.exception.APIConnectionException;
import com.pingplusplus.exception.APIException;
import com.pingplusplus.exception.AuthenticationException;
import com.pingplusplus.exception.ChannelException;
import com.pingplusplus.exception.InvalidRequestException;
import com.pingplusplus.model.Charge;
import com.tzdr.business.pay.pingpp.config.Config;
import com.tzdr.business.pay.pingpp.config.enums.Channel;
import com.tzdr.business.pay.pingpp.model.PingPPModel;

public class ChargeExample {
	private static SecureRandom random = new SecureRandom();
	public static Charge createCharge(PingPPModel pingPPModel){
		Charge charge = null;
		String channel = pingPPModel.getChannel();
		Map<String, Object> chargeMap = new HashMap<String, Object>();
		chargeMap.put("amount", pingPPModel.getAmount() * 100);
		chargeMap.put("currency", pingPPModel.getCurrency());
		chargeMap.put("subject", pingPPModel.getSubject());
		chargeMap.put("body", pingPPModel.getBody());
		chargeMap.put("order_no", pingPPModel.getOrder_no());
		chargeMap.put("channel", channel);
		chargeMap.put("client_ip", getAddresIP());
		Map<String, String> app = new HashMap<String, String>();
        app.put("id", Config.APP_ID);
        chargeMap.put("app", app);
        Map<String, Object> extra = new HashMap<String, Object>();
        if(channel.equals(Channel.ALIPAY_PC_DIRECT.getChannelCode())){
        	extra.put("success_url",Config.ALIPAY_PC_SUCCESS_URL);
        }else if(channel.equals(Channel.ALIPAY_WAP.getChannelCode())){
        	extra.put("success_url",Config.ALIPAY_WAP_SUCCESS_URL);
        }else if(channel.equals(Channel.WX_PUB_QR.getChannelCode())){
        	extra.put("limit_pay","no_credit");//设置表示微信扫码支付不能使用信用卡支付
        	extra.put("product_id", "12345678");
        }else if(channel.equals(Channel.JD_WAP.getChannelCode())){
        	extra.put("success_url", Config.JD_WAP_SUCCESS_URL);
        	extra.put("fail_url", Config.JD_WAP_FAIL_URL);
        }
        chargeMap.put("extra", extra);
        try {
			charge = Charge.create(chargeMap);
		} catch (AuthenticationException e) {
			e.printStackTrace();
		} catch (InvalidRequestException e) {
			e.printStackTrace();
		} catch (APIConnectionException e) {
			e.printStackTrace();
		} catch (APIException e) {
			e.printStackTrace();
		} catch (ChannelException e) {
			e.printStackTrace();
		}
		return charge;
	}
	public static String randomNo(){
		return  new Date().getTime() + randomString(7);
	}
	public static String randomString(int length) {
        String str = new BigInteger(130, random).toString(32);
        return str.substring(0, length);
    }
	public static String getAddresIP(){
		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			return addr.getHostAddress().toString();//获得本机IP
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}
}
