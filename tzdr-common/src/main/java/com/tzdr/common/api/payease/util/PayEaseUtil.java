package com.tzdr.common.api.payease.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.tzdr.common.api.payease.PayEase;
import com.tzdr.common.api.payease.vo.PayEaseParams;


/**
 * 首信易支付
 * @zhouchen
 * 2015年10月13日
 */
public class PayEaseUtil {
	
	public static final Logger logger = LoggerFactory.getLogger(PayEaseUtil.class);
	
	/**
	 * 投资达人 网站支付
	 * @param payEaseParams
	 * @return
	 */
	public static PayEaseParams tzdrWebPay(PayEaseParams payEaseParams){
		payEaseParams.setVmid(PayEaseConfigUtil.getContext("tzdr_web_account"));
		payEaseParams.setVrcvname(PayEaseConfigUtil.getContext("tzdr_web_account"));
		payEaseParams.setVrcvaddr(PayEaseConfigUtil.getContext("tzdr_web_account"));
		payEaseParams.setVrcvtel(PayEaseConfigUtil.getContext("tzdr_web_account"));
		payEaseParams.setVrcvpost(PayEaseConfigUtil.getContext("tzdr_web_account"));
		payEaseParams.setVordername(PayEaseConfigUtil.getContext("tzdr_web_account"));
		payEaseParams.setVurl(PayEaseConfigUtil.getContext("tzdr_web_sret_url"));		
		payEaseParams.setSecret(PayEaseConfigUtil.getContext("tzdr_web_secret"));
		return payEaseParams;
	}
	
	/**
	 * 投资达人app支付
	 * @param payEaseParams
	 * @return
	 */
	public static PayEaseParams tzdrAppPay(PayEaseParams payEaseParams){
		payEaseParams.setVmid(PayEaseConfigUtil.getContext("tzdr_app_account"));
		payEaseParams.setVrcvname(PayEaseConfigUtil.getContext("tzdr_app_account"));
		payEaseParams.setVrcvaddr(PayEaseConfigUtil.getContext("tzdr_app_account"));
		payEaseParams.setVrcvtel(PayEaseConfigUtil.getContext("tzdr_app_account"));
		payEaseParams.setVrcvpost(PayEaseConfigUtil.getContext("tzdr_app_account"));
		payEaseParams.setVordername(PayEaseConfigUtil.getContext("tzdr_app_account"));
		payEaseParams.setVurl(PayEaseConfigUtil.getContext("tzdr_app_sret_url"));	
		payEaseParams.setSecret(PayEaseConfigUtil.getContext("tzdr_app_secret"));
		return payEaseParams;
	}
	
	/**
	 * 配股宝网站支付
	 * @param payEaseParams
	 * @return
	 */
	public static PayEaseParams pgbWebPay(PayEaseParams payEaseParams){
		payEaseParams.setVmid(PayEaseConfigUtil.getContext("pgb_web_account"));
		payEaseParams.setVrcvname(PayEaseConfigUtil.getContext("pgb_web_account"));
		payEaseParams.setVrcvaddr(PayEaseConfigUtil.getContext("pgb_web_account"));
		payEaseParams.setVrcvtel(PayEaseConfigUtil.getContext("pgb_web_account"));
		payEaseParams.setVrcvpost(PayEaseConfigUtil.getContext("pgb_web_account"));
		payEaseParams.setVordername(PayEaseConfigUtil.getContext("pgb_web_account"));
		payEaseParams.setVurl(PayEaseConfigUtil.getContext("pgb_web_sret_url"));		
		payEaseParams.setSecret(PayEaseConfigUtil.getContext("pgb_web_secret"));
		return payEaseParams;
	}
	
	
	/**
	 * 投资达人提现  
	 * @param money 提现金额
	 * @param contactNumber  联行号
	 * @param bankCard  提现卡号
	 * @param realName 真实名称
	 * @param orderId 订单id
	 * @param bankName 银行名称
	 * @return
	 */
	public static JSONObject tzdrDrawMony(Double money,String contactNumber,
			String bankCard,String realName,String orderId,String bankName){
				// 调用提现接口
				// 格式：分帐信息总行数|分帐总金额|$收方帐号|收方帐户名|收方开户行|收方省份|收方城市|付款金额| 客户标识|联行号
				StringBuffer vdata = new StringBuffer();
				vdata.append(1+"|");
				vdata.append(money+"|");
				vdata.append("$"+bankCard+"|");
				vdata.append(realName+"|");
				vdata.append(bankName+"|");
				vdata.append("北京市|");
				vdata.append("北京市|");
				vdata.append(money+"|");
				vdata.append("C"+orderId+"|");
				vdata.append(contactNumber);
				return PayEase.getInstance().withdraw(PayEaseConfigUtil.getContext("tzdr_web_account"), 
						PayEaseConfigUtil.getContext("tzdr_web_secret"),vdata.toString());
	}
	
	/**
	 * 配股宝提现  
	 * @param money 提现金额
	 * @param contactNumber  联行号
	 * @param bankCard  提现卡号
	 * @param realName 真实名称
	 * @param orderId 订单id
	 * @param bankName 银行名称
	 * @return
	 */
	public static JSONObject pgbDrawMony(Double money,String contactNumber,
			String bankCard,String realName,String orderId,String bankName){
		// 格式：分帐信息总行数|分帐总金额|$收方帐号|收方帐户名|收方开户行|收方省份|收方城市|付款金额| 客户标识|联行号
		StringBuffer vdata = new StringBuffer();
		vdata.append(1+"|");
		vdata.append(money+"|");
		vdata.append("$"+bankCard+"|");
		vdata.append(realName+"|");
		vdata.append(bankName+"|");
		vdata.append("北京市|");
		vdata.append("北京市|");
		vdata.append(money+"|");
		vdata.append("C"+orderId+"|");
		vdata.append(contactNumber);
		return PayEase.getInstance().withdraw(PayEaseConfigUtil.getContext("pgb_web_account"), 
				PayEaseConfigUtil.getContext("pgb_web_secret"),vdata.toString());
	}
}


