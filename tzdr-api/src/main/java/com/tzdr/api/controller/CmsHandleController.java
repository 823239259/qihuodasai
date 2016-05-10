package com.tzdr.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tzdr.api.request.AuditWithdrawRequest;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.drawMoney.DrawMoneyService;
import com.tzdr.business.service.pay.PaymentSupportBankService;
import com.tzdr.business.service.withdrawal.WithdrawalService;
import com.tzdr.common.api.bbpay.Bibipay;
import com.tzdr.common.api.bbpay.util.BbUtil;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.web.entity.DrawList;
import com.tzdr.domain.web.entity.PaymentSupportBank;
import com.tzdr.domain.web.entity.WUser;

/**
 * 
 * <B>说明: </B>专供cms 业务处理的接口
 * @zhouchen
 * 2016年1月20日
 */
@Controller
@RequestMapping(value = "/cmshandler")
public class CmsHandleController {
	private static Logger log = LoggerFactory.getLogger(CmsHandleController.class);

	@Autowired
	private WithdrawalService  withdrawalService;
	
	
	@Autowired
	private DataMapService  dataMapService;
	
	@Autowired
	private DrawMoneyService  drawMoneyService;
	
	
	@Autowired
	private PaymentSupportBankService  paymentSupportBankService;
	
	
	/**
	 * 此接口主要用于cms 提现审核时调用。若提现来源为配股宝则调用此接口
	 * @param requestObj
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/audit_withdraw",method=RequestMethod.POST)
	@ResponseBody 
	public JsonResult auditWithdraw(@RequestBody AuditWithdrawRequest auditWithdrawRequest,
			HttpServletRequest request,HttpServletResponse response){
		String withdrawId = auditWithdrawRequest.getWithdrawId();
		if (StringUtil.isBlank(withdrawId)){
			return new JsonResult(false,"审核失败，未找到对应的提现记录。请联系技术部门核实！");
		}
		
		DrawList vdrawList = withdrawalService.get(withdrawId);
		if (ObjectUtil.equals(null, vdrawList)){
			return new JsonResult(false,"审核失败，未找到对应的提现记录。请联系技术部门核实！");
		}
		
		// 币币支付调用接口
		if (Constant.PaymentChannel.BB_PAY == vdrawList.getPaymentChannel()){
			return bbDrawMoney(vdrawList);
		}
		
		// 更新审核为成功状态
		withdrawalService.updateApiAuditStatus(withdrawId,1);
		// 联动优势调用接口
		JSONObject jsonObject =  drawMoneyService.drawMoney(withdrawId);
		String loginfo = "用户：【"+vdrawList.getUser().getMobile()+"】提现,调用支付接口返回结果::::code="+jsonObject.getString("retCode")+","
				+ "message="+jsonObject.getString("retMsg")+";提现记录ID:"+vdrawList.getId()+";提现金额："+vdrawList.getMoney();
		log.info(loginfo);
		
		if (!StringUtil.equals("0000",jsonObject.getString("retCode"))){
			 EmailExceptionHandler.getInstance().HandleHintWithData("cms-提现审核接口返回异常",this.getClass().getSimpleName()+":: audit()",loginfo);
		}
		return new JsonResult("审核成功！");
		
	} 

	
	
	/**
	 * 币币支付提现接口调用
	 * @param drawList
	 * @return
	 */
	private JsonResult bbDrawMoney(DrawList drawList){
		//提现手续费
		String handleFeeStr = CacheManager.getDataMapByKey(DataDicKeyConstants.WITHDRAW_HANDLE_FEE,DataDicKeyConstants.BB_FEE);
		Double handleFee = 0.00;
		
		if(!StringUtil.isBlank(handleFeeStr)) {
			handleFee = Double.valueOf(handleFeeStr);
		}
		
		Double dmoney = drawList.getMoney();
		if(BigDecimalUtils.compareTo(dmoney, 5000) < 0) {
			//扣除手续费
			dmoney = BigDecimalUtils.subRound(dmoney, handleFee);
		} 
		WUser user=drawList.getUser();
		//校验是否支持该银行提现
		PaymentSupportBank paymentSupportBank = paymentSupportBankService.findByAbbreviation(drawList.getSubbank());
		// 调用提现接口
		// 格式：联行号~|~银行卡号~|~开户人~|~结算金额~|~1：私人，2：公司
	
		JSONObject bbResult = BbUtil.withDrawMony(dmoney, paymentSupportBank.getBbpayContactNumber(), drawList.getCard(), user.getUserVerified().getTname(), drawList.getNo());
		log.info("cms处理配股宝币币支付提现订单调用接口返回："+bbResult);
		String resultMsg = bbResult.getString("msg");
		if (Bibipay.HANDLE_SUCCESS_STATUS != bbResult.getIntValue("status")){
			log.info("cms处理配股宝币币支付提现订单委托结算失败，返回结果："+bbResult);
			EmailExceptionHandler.getInstance().HandleHintWithData("cms处理配股宝币币支付提现订单委托结算失败","bbDrawMoney", bbResult.toJSONString());
			return new JsonResult(false,resultMsg);
		}
		withdrawalService.updateApiAuditStatus(drawList.getId(),1);
		// 更新币币订单号到提现记录中
		drawMoneyService.updatDrawBybbOrderId(drawList.getId(),bbResult.getString("order_number"));
		return new JsonResult(resultMsg);
	}
}
