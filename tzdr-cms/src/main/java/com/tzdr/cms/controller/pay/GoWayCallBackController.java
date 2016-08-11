package com.tzdr.cms.controller.pay;


import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.pay.gopay.GoConfigStatus;
import com.tzdr.business.pay.gopay.handle.GoPayTradeData;
import com.tzdr.business.pay.gopay.model.GoPayCallBackModel;
import com.tzdr.business.pay.pingpp.config.enums.Channel;
import com.tzdr.business.service.pay.PayService;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.RechargeList;
@Controller
@RequestMapping(value = "/goway")
public class GoWayCallBackController  extends BaseCmsController<RechargeList> {
	private Logger logger = LoggerFactory.getLogger(GoWayCallBackController.class);
	@Autowired
	private PayService payService;
	@ResponseBody
	@RequestMapping(value = "/callback",method = RequestMethod.POST)
	public String goWayCallBack(HttpServletRequest request) throws DocumentException{
		GoPayCallBackModel model = signValidation(request);
		if(model != null){
			String respCode = model.getRespCode();
			if(respCode != null && respCode.equals(GoConfigStatus.SUCCESS_STATUS)){
				//系统内部流水号
				String merOrderNum = model.getMerOrderNum();
				//支付金额
				String tranAmt = model.getTranAmt();
				Double amount = tranAmt == null?0.00:Double.parseDouble(tranAmt);
				//第三方流水号
				String orderId = model.getOrderId();
				logger.info("merOrderNum=======>" + merOrderNum);
				logger.info("tranAmt======>" + tranAmt);
				logger.info("orderId===>" + orderId);
				payService.doUpdatePingPPPaySuccessRecharge(merOrderNum, Channel.GO_WAY.getChannelCode(), amount, orderId, null,"国付宝充值"+amount+"元");
			}
		}
		return resultGoWay();
	}
	public GoPayCallBackModel setReqestModel(HttpServletRequest request){
		GoPayCallBackModel model = new GoPayCallBackModel();
		model.setVersion(request.getParameter("version"));
		model.setTradCode(request.getParameter("tranCode"));
		model.setMerchantID(request.getParameter("merchantID"));
		model.setFrontMerUrl(request.getParameter("frontMerUrl"));
		model.setBackgroundMerUrl(request.getParameter("backgroundMerUrl"));
		model.setMerOrderNum(request.getParameter("merOrderNum"));
		model.setTranAmt(request.getParameter("tranAmt"));
		model.setFeeAmt(request.getParameter("feeAmt"));
		model.setTranDateTime(request.getParameter("tranDateTime"));
		model.setOrderId(request.getParameter("orderId"));
		model.setGopayOutOrderId(request.getParameter("gopayOutOrderId"));
		model.setTranIP(request.getParameter("tranIP"));
		model.setRespCode(request.getParameter("respCode"));
		model.setCallBackSign(request.getParameter("signValue"));
		return model;
	}
	public GoPayCallBackModel signValidation(HttpServletRequest request){
		GoPayCallBackModel model = setReqestModel(request);
		if(model != null){
			String signValue;
			try {
				logger.info(GoPayTradeData.callBackEncodeSignValue(model));
				signValue = GoPayTradeData.md5CallBackEncodeSignValue(model);
				if(model.getCallBackSign().equals(signValue)){
					return model;
				}else {
					return null;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	public String resultGoWay(){
		return "RespCode=0000|JumpURL=http://hedaoqing.oicp.net/user/account";
	}
	@Override
	public BaseService<RechargeList> getBaseService() {
		return null;
	}
	
}
