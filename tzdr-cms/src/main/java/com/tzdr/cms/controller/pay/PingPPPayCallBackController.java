package com.tzdr.cms.controller.pay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tzdr.business.cms.service.messagePrompt.MessagePromptService;
import com.tzdr.business.cms.service.messagePrompt.PromptTypes;
import com.tzdr.business.pay.pingpp.config.enums.TradeStatus;
import com.tzdr.business.pay.pingpp.example.BackExample;
import com.tzdr.business.pay.pingpp.example.WebhooksVerifyExample;
import com.tzdr.business.service.pay.PayService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.RechargeList;
import com.tzdr.domain.web.entity.WUser;

@Controller
@RequestMapping(value = "/pingpp")
public class PingPPPayCallBackController extends BaseCmsController<RechargeList> {
	private Logger logger = LoggerFactory.getLogger(PingPPPayCallBackController.class);
	@Autowired
	private PayService payService;
	
	@Autowired
	private MessagePromptService messagePromptService;
	@Autowired
	private WUserService wUserService;

	@RequestMapping(value = "/alipay/callback", method = RequestMethod.POST,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String pingppAlipayCallBack(HttpServletRequest request) {
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream()));
			StringBuilder read = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				read.append(line);
			}
			bufferedReader.close();
			String param = read.toString();
			logger.info("result param =======>>>" + param);
				if (param != null && param.length() > 0) {
					JSONObject jsonObject = JSONObject.parseObject(param);
					JSONObject dataJson = jsonObject.getJSONObject("data").getJSONObject("object");
					boolean paid = dataJson.getBooleanValue("paid");
					if (paid) {
						logger.info("paid===>" + paid);
						String orderNo = dataJson.getString("order_no");
						String transactionNo = dataJson.getString("transaction_no");
						Double amount = dataJson.getDouble("amount");
						String timePaid = dataJson.getString("time_paid");
						String channel = dataJson.getString("channel");
						logger.info("orderNo=======>" + orderNo);
						logger.info("transaction_no======>" + transactionNo);
						logger.info("amount===>" + amount);
						logger.info("channel====>" + channel);
						Double money = amount / 100;
						String userId = payService.doUpdatePingPPPaySuccessRecharge(orderNo, channel,money , transactionNo, timePaid,TradeStatus.SUCCESS.getCode(),"支付宝充值"+money+"元");
						try {
							if(userId != null){
								WUser user =  wUserService.getUser(userId);
								if(user != null){
									messagePromptService.sendMessage(PromptTypes.isAlipayRecharge, user.getMobile());
								}
							}
						} catch (Exception e) {
							logger.info("发送邮件失败",e);
						}
						
					}
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return BackExample.paySuccessBack();
	}

	/**
	 * 验证请求是否来自PING++
	 * 
	 * @param request
	 * @param data
	 * @return
	 */
	public boolean verifyRequest(HttpServletRequest request, String data) {
		boolean flag = false;
		Map<Object, Object> map = getHeader(request);
		String signature = null;
		if (map != null) {
			signature = map.get("x-pingplusplus-signature").toString();
		}
		try {
			flag = WebhooksVerifyExample.verifyData(data, signature, WebhooksVerifyExample.getPubKey());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public static Map<Object, Object> getHeader(HttpServletRequest request) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}
		return map;
	}

	@Override
	public BaseService<RechargeList> getBaseService() {
		return null;
	}

}
