package com.tzdr.common.api.umpay;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tzdr.common.api.umpay.data.FastPayInfo;
import com.tzdr.common.api.umpay.data.PayGoodsInfo;
import com.tzdr.common.api.umpay.data.PayOrderInfo;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.utils.EmailUtils;
import com.umpay.api.exception.ReqDataException;


/**
 * @Description:快捷支付接口 前台页面请求
 * @ClassName: FastPay.java
 * @author Lin Feng
 * @date 2014年12月29日
 */
public class FastPay {

	public static final Logger log = LoggerFactory.getLogger(FastPay.class);

	
	private static FastPay instance;
	
	/**
	 * 接口名称
	 */
	private static String service;
	
	/**
	 * 签名方式
	 */
	private static String signType;
	
	/**
	 * 参数字符编码集
	 */
	private static String charset;
	
	/**
	 * 版本号
	 */
	private static String version;
	
	/**
	 * 商户编号
	 */
	private static String merId;
	
	/**
	 * 响应数据格式
	 */
	private static String resFormat;
	
	/**
	 * 付款币种 仅支持人民币
	 */
	private static String amtType;
	
	/**
	 * 支付方式
	 */
	private static String payType;
	
	/**
	 * 服务器异步通知页面路径
	 */
	private static String notifyUrl;
	
	/**
	 * 页面跳转同步通知页面路径
	 */
	private static String retUrl;
	
	/**
	 * 是否允许用户修改支付要素
	 */
	private static String canModifyFlag;
		
	private FastPay() {
		service=ConfUtil.getContext("umpay.fast.pay.service");
		signType=ConfUtil.getContext("umpay.sign.type");
		charset=ConfUtil.getContext("umpay.charset");
		version=ConfUtil.getContext("umpay.version");
		merId=ConfUtil.getContext("umpay.merId");
		resFormat=ConfUtil.getContext("umpay.resFormat");
		amtType=ConfUtil.getContext("umpay.amt.type");
		payType=ConfUtil.getContext("umpay.fast.pay.type");
		notifyUrl=ConfUtil.getContext("umpay.notify.url");
		retUrl=ConfUtil.getContext("umpay.fast.ret.url");
		canModifyFlag=ConfUtil.getContext("umpay.can.modify.flag");
	}

	public static synchronized FastPay getInstance() {
		if (instance == null) {
			instance = new FastPay();
		}
		return instance;
	}
	

	public  String getUrl(PayOrderInfo payOrderInfo,PayGoodsInfo payGoodsInfo,FastPayInfo fastPayInfo)  {

		Map<String,String> ht = Maps.newHashMap();
		ht.put("service", service);
		ht.put("sign_type", signType);
		ht.put("charset", charset);
		ht.put("version", version);
		ht.put("mer_id", merId);
		ht.put("ret_url", retUrl);
		ht.put("notify_url", notifyUrl);
		ht.put("res_format", resFormat);
		ht.put("goods_id", payGoodsInfo.getGoodsId());
		ht.put("goods_inf", payGoodsInfo.getGoodsInf());
		ht.put("order_id", payOrderInfo.getOrderId());
		ht.put("mer_date", payOrderInfo.getMerDate());
		ht.put("amount", payOrderInfo.getAmount());
		ht.put("amt_type", amtType);
		ht.put("pay_type", payType);
		ht.put("gate_id", payOrderInfo.getGateId());
		ht.put("mer_priv", payGoodsInfo.getMerPriv());
		ht.put("expand", payGoodsInfo.getExpand());
		ht.put("user_ip", payGoodsInfo.getUserIp());
		ht.put("expire_time", payGoodsInfo.getExpireTime());
		// 是否进行协议支付请求参数
		ht.put("mer_cust_id", payGoodsInfo.getMerCustId());
		// 首次支付要素信息
		ht.put("card_id", fastPayInfo.getCardId());
		ht.put("media_id", fastPayInfo.getMediaId());
		ht.put("media_type", fastPayInfo.getMediaType());
		ht.put("valid_date", fastPayInfo.getValidDate());
		ht.put("cvv2", fastPayInfo.getCvv2());
		ht.put("identity_type", fastPayInfo.getIdentityType());
		ht.put("identity_code", fastPayInfo.getIdentityCode());
		ht.put("card_holder", fastPayInfo.getCardHolder());
		// 是否允许用户修改平台页面传递的支付要素
		 ht.put("can_modify_flag",canModifyFlag);
		com.umpay.api.common.ReqData reqData;
		String url="";
		try {
			reqData = com.umpay.api.paygate.v40.Mer2Plat_v40
					.ReqDataByGet(ht);
			url=reqData.getUrl();
		} catch (ReqDataException e) {
			String details=payOrderInfo.toString()+","+payGoodsInfo.toString()+","+fastPayInfo.toString();		
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e, "快捷支付接口报错", this.getClass().getName()+"getUrl",details);
		}
		return url;
	}

}
