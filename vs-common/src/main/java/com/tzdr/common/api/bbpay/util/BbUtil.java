package com.tzdr.common.api.bbpay.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.tzdr.common.api.bbpay.Bibipay;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.BigDecimalUtils;

/**
 * 币币支付提现处理
 * @author zhouchen
 * @version 创建时间：2015年12月16日 下午2:07:39
 * 类说明
 */
public class BbUtil {
	private static Logger log = LoggerFactory.getLogger(BbUtil.class);
	
	public  static final int WITHDRAW_INTERFACE_EXCEPTION=100000;
	/**
	 * 币币支付提现处理
	 * @param money 提现金额
	 * @param bbpayContactNumber 银行联行号
	 * @param bankCard 银行卡号
	 * @param realName 真实姓名
	 * @param orderId  达人订单id
	 * @return
	 */
	public static JSONObject withDrawMony(Double money,String bbpayContactNumber,
			String bankCard,String realName,String orderId){
		int dmoneyInt = new Double(BigDecimalUtils.mul(100,money)).intValue();
		// 调用提现接口
		// 格式：联行号~|~银行卡号~|~开户人~|~结算金额~|~1：私人，2：公司~|~商户订单号
		StringBuffer bankConfig = new StringBuffer();
		bankConfig.append(bbpayContactNumber+"~|~");
		bankConfig.append(bankCard+"~|~");
		bankConfig.append(realName+"~|~");
		bankConfig.append(dmoneyInt+"~|~");
		bankConfig.append(1+"~|~");
		bankConfig.append(orderId);
		JSONObject bbResult=new JSONObject();
		try {
			bbResult = Bibipay.getInstance().settleMoney(dmoneyInt, bankConfig.toString());
		} catch (Exception e) {
			log.info("币币支付委托结算接口调用异常，"+bankConfig,e);
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,"币币支付委托结算接口调用异常.","Bibipay.getInstance().settleMoney().", bankConfig.toString());
			bbResult.put("status",WITHDRAW_INTERFACE_EXCEPTION);
			bbResult.put("msg","币币支付调用取款接口异常!");
		}
		return bbResult;
	}
}
