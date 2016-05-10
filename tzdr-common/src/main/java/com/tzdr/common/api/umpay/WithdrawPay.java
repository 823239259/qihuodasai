package com.tzdr.common.api.umpay;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tzdr.common.api.umpay.data.WithdrawPayExtendInfo;
import com.tzdr.common.api.umpay.data.WithdrawPayInfo;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.utils.EmailUtils;
import com.umpay.api.exception.ReqDataException;
import com.umpay.api.exception.RetDataException;

/**
 * @Description:取款支付接口 后台请求
 * @ClassName: WithdrawPay.java
 * @author Lin Feng
 * @date 2014年12月29日
 */
public class WithdrawPay {

	public static final Logger log = LoggerFactory.getLogger(WithdrawPay.class);

	private static WithdrawPay instance;

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
	 * 收款方账户类型 仅支持银行卡00
	 */
	private static String recvAccountType;

	/**
	 * 收款方账户属性
	 */
	private static String recvBankAccPro;

	/**
	 * 响应数据格式
	 */
	private static String resFormat;

	/**
	 * 服务器异步通知页面路径
	 */
	private static String notifyUrl;

	private static String devEmail;

	
	private void sendToDev(String retCode,String retMsg) {
		// 发送邮件
		List<String> pramas = Lists.newArrayList();
		pramas.add(this.getClass().getName()+"getWithdrawResponse");
		pramas.add("错误码:"+retCode+ ".错误消息:" + retMsg);
		try {
			EmailUtils.getInstance().sendMailTemp(devEmail, "exceptionemail",
					pramas);
		} catch (Exception ex) {
			log.error("email:", ex.getMessage());
		}
	}

	private WithdrawPay() {
		devEmail= ConfUtil.getContext("mail.to.dev");
		service = ConfUtil.getContext("umpay.withdraw.pay.service");
		signType = ConfUtil.getContext("umpay.sign.type");
		charset = ConfUtil.getContext("umpay.charset");
		version = ConfUtil.getContext("umpay.version");
		merId = ConfUtil.getContext("umpay.merId");
		resFormat = ConfUtil.getContext("umpay.resFormat");
		notifyUrl = ConfUtil.getContext("umpay.withdraw.pay.notify.url");
		recvAccountType = ConfUtil
				.getContext("umpay.withdraw.pay.recv.account.type");
		recvBankAccPro = ConfUtil
				.getContext("umpay.withdraw.pay.recv.bank.acc.pro");
	}

	public static synchronized WithdrawPay getInstance() {
		if (instance == null) {
			instance = new WithdrawPay();
		}
		return instance;
	}

	public String getUrl(WithdrawPayInfo withdrawPayInfo,
			WithdrawPayExtendInfo withdrawPayExtendInfo) {

		Map<String, String> map = Maps.newHashMap();
		map.put("service", service);
		map.put("charset", charset);
		map.put("mer_id", merId);
		map.put("sign_type", signType);
		map.put("notify_url", notifyUrl);
		map.put("res_format", resFormat);
		map.put("version", version);
		map.put("order_id", withdrawPayInfo.getOrderId());
		map.put("mer_date", withdrawPayInfo.getMerDate());
		map.put("amount", withdrawPayInfo.getAmount());
		map.put("recv_account_type", recvAccountType);
		map.put("recv_bank_acc_pro", recvBankAccPro);
		map.put("recv_account", withdrawPayInfo.getRecvAccount());
		map.put("recv_user_name", withdrawPayInfo.getRecvUserName());
		map.put("identity_type", withdrawPayExtendInfo.getIdentityType());
		map.put("identity_code", withdrawPayExtendInfo.getIdentityCode());
		map.put("identity_holder", withdrawPayExtendInfo.getIdentityHolder());
		map.put("media_id", withdrawPayExtendInfo.getMediaId());
		map.put("media_type", withdrawPayExtendInfo.getMediaType());
		map.put("recv_gate_id", withdrawPayInfo.getRecvGateId());
		map.put("purpose", withdrawPayInfo.getPurpose());
		map.put("prov_name", withdrawPayExtendInfo.getProvName());
		map.put("city_name", withdrawPayExtendInfo.getCityName());
		map.put("bank_brhname", withdrawPayInfo.getBankBrhname());
		map.put("checkFlag", withdrawPayExtendInfo.getCheckFlag());

		com.umpay.api.common.ReqData reqData;
		String url = "";
		try {
			reqData = com.umpay.api.paygate.v40.Mer2Plat_v40
					.makeReqDataByGet(map);
			url = reqData.getUrl();
		} catch (ReqDataException e) {
			String details=withdrawPayInfo.toString()+","+withdrawPayExtendInfo.toString();		
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e, "提现支付接口报错", this.getClass().getName()+"getUrl",details);
		}
		return url;
	}

	/**
	 * @param withDrawUrl
	 *            取款支付 URL
	 * @return
	 * @throws RetDataException
	 * @throws IOException
	 */
	public JSONObject getWithdrawResponse(WithdrawPayInfo withdrawPayInfo,
			WithdrawPayExtendInfo withdrawPayExtendInfo)
			throws RetDataException, IOException {

		JSONObject reslut = new JSONObject();
		URL url = new URL(getUrl(withdrawPayInfo, withdrawPayExtendInfo));
		Map res = Maps.newHashMap();
		URLConnection conn = url.openConnection();
		HttpURLConnection httpConnection = (HttpURLConnection) conn;
		int responseCode = httpConnection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			InputStream in = httpConnection.getInputStream();
			res = com.umpay.api.paygate.v40.Plat2Mer_v40.getResData(in);
			if ("0000".equals(res.get("ret_code"))) {
				reslut.put("retCode", res.get("ret_code"));
				reslut.put("retMsg", "提交申请提现成功.");
			} else {
				reslut.put("retCode", res.get("ret_code"));
				reslut.put("retMsg", res.get("ret_msg"));
			}
		} else {
			reslut.put("retCode", "-1");
			reslut.put("retMsg", "联动优势平台响应状态异常");
			sendToDev("-1","联动优势平台响应状态异常");
		}
		return reslut;
	}

}
