package com.tzdr.cms.controller.pay;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tzdr.business.cms.service.messagePrompt.MessagePromptService;
import com.tzdr.business.cms.service.messagePrompt.PromptTypes;
import com.tzdr.business.pay.dxtx.DxtxPayResultModel;
import com.tzdr.business.pay.pingpp.config.enums.Channel;
import com.tzdr.business.pay.pingpp.config.enums.TradeStatus;
import com.tzdr.business.service.pay.PayService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.HttpClientUtils;
import com.tzdr.domain.web.entity.WUser;

@Controller
@RequestMapping(value = "/dxtx")
public class DxtxPayCallBackController {
	private Logger logger = LoggerFactory.getLogger(DxtxPayCallBackController.class);
	@Autowired
	private PayService payService;
	@Autowired
	private WUserService wUserService;
	@Autowired
	private MessagePromptService messagePromptService;
	@ResponseBody
	@RequestMapping(value = "/callBck", method = RequestMethod.GET)
	public void payCallBack(HttpServletRequest request,HttpServletResponse response){
		DxtxPayResultModel dxtxPayResultModel = setResultModel(request);
		boolean isSuccess = validationSign(dxtxPayResultModel);
		if(isSuccess){
			//系统内部流水号
			String merOrderNum = dxtxPayResultModel.getTradeNo();
			//支付金额
			String tranAmt = dxtxPayResultModel.getTradePrice();
			Double amount = tranAmt == null?0.00:Double.parseDouble(tranAmt);
			//第三方流水号
			String orderId = dxtxPayResultModel.getTradeCode();
			logger.info("merOrderNum=======>" + merOrderNum);
			logger.info("tranAmt======>" + tranAmt);
			logger.info("orderId===>" + orderId);
			String remark = "";
			Integer status = 0;
			Channel channel = Channel.ALIPAY_WAP;
			String tradeType = dxtxPayResultModel.getTradeType();
			if(tradeType.equals("2")){
				channel = Channel.WX;
			}else if(tradeType.equals("3")){
				channel = Channel.QUICK;
			}
			if(dxtxPayResultModel.getTradeStatus().equals("TRADE_SUCCESS")){
				remark = ""+channel.getChannelNote()+"充值"+tranAmt+"元";
				status = TradeStatus.SUCCESS.getCode();
			}else{
				remark = ""+channel.getChannelNote()+"充值失败";
				status = TradeStatus.FAIL.getCode();
			}
			String userId =  payService.doUpdatePingPPPaySuccessRecharge(merOrderNum, channel.getChannelCode(), amount, orderId, dxtxPayResultModel.getTradeTime(),status, remark);
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
		try {
			PrintWriter printWriter = response.getWriter();
			response.setCharacterEncoding("UTF-8");
			printWriter.write("success");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 取支付回调的数据
	 * @return
	 */
	public DxtxPayResultModel setResultModel(HttpServletRequest request){
		DxtxPayResultModel dxtxPayResultModel = new DxtxPayResultModel();
		dxtxPayResultModel.setTradeNo(request.getParameter("trade_no"));
		dxtxPayResultModel.setTradeCode(request.getParameter("trade_code"));
		dxtxPayResultModel.setTradePaycode(request.getParameter("trade_paycode"));
		dxtxPayResultModel.setTradeType(request.getParameter("trade_type"));
		dxtxPayResultModel.setTradePrice(request.getParameter("trade_price"));
		dxtxPayResultModel.setTradePrivateinfo(request.getParameter("trade_privateinfo"));
		dxtxPayResultModel.setTradeTime(request.getParameter("trade_time"));
		dxtxPayResultModel.setTradeSign(request.getParameter("trade_sign"));
		dxtxPayResultModel.setTradeStatus(request.getParameter("trade_status"));
		logger.info("盾行天下支付成功返回数据---->"+dxtxPayResultModel.toString());
		return dxtxPayResultModel;
	}
	/**
	 * 验证签名
	 * @return
	 */
	public boolean validationSign(DxtxPayResultModel dxtxPayResultModel){
		String result = HttpClientUtils.httpGet("http://api.dunxingpay.com/validate.aspx", "trade_no="+dxtxPayResultModel.getTradeNo()+"&trade_sign="+dxtxPayResultModel.getTradeSign()+"&app_sign=60862F2AE3B01803E935063788A80DE5E9949A68723BB37AAEA387EDD81F26EEB76C9965BE8E15AE2CAED376FB17D1E68158D87ACBCE3844331295A1A4B7F6E6AED319C0FFC319E0");
		if(result.equalsIgnoreCase("SUCCESS")){
			return true;
		}else{
			logger.info("验证支付失败!");
			return false;
		}
	}
}
