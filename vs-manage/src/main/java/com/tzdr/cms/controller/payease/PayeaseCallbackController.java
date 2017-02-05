
/**
 * @zhouchen
 * 2015年10月13日
 */
package com.tzdr.cms.controller.payease;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.capinfo.crypt.RSA_MD5;
import com.tzdr.business.service.pay.PayService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.controller.AgentController;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.web.entity.RechargeList;

@Controller
@RequestMapping("/payease")
public class PayeaseCallbackController{
	private static Logger loger = LoggerFactory.getLogger(AgentController.class);
	
	@Autowired
	private PayService  payService;
	
	private static Object lock = new Object();
	/**
	 * pc网上银行支付后台通知回调
	 */
	@RequestMapping(value = "/callback")
	public void asynreturnURL(HttpServletRequest request,HttpServletResponse response) throws Exception {
		 //订单号
		 String v_oid= request.getParameter("v_oid");
		 if(StringUtil.isBlank(v_oid)){
	    	 WebUtil.printText("error", response);
	    	 return;
	     }
		 
	     //支付方式
	     String v_pmode= request.getParameter("v_pmode");
	     if(StringUtil.isBlank(v_pmode)){
	    	 WebUtil.printText("error", response);
	    	 return;
	     }
	     //支付结果
	     String v_pstatus= request.getParameter("v_pstatus");
	     if(StringUtil.isBlank(v_pstatus)){
	    	 WebUtil.printText("error", response);
	    	 return;
	     }
	     //支付金额
	     String v_amount= request.getParameter("v_amount");   
	     if(StringUtil.isBlank(v_amount)){
	    	 WebUtil.printText("error", response);
	    	 return;
	     }
	     //支付币种
	     String v_moneytype= request.getParameter("v_moneytype");
	     if(StringUtil.isBlank(v_moneytype)){
	    	 WebUtil.printText("error", response);
	    	 return;
	     }
	     // 支付结果组
	     String  v_pstring=request.getParameter("v_pstring");
	     if(StringUtil.isBlank(v_pstring)){
	    	 WebUtil.printText("error", response);
	    	 return;
	     }
	     //数据签名
	     String v_sign = request.getParameter("v_sign");
	     if(StringUtil.isBlank(v_sign)){
	    	 WebUtil.printText("error", response);
	    	 return;
	     }
	     //订单数量
	     String v_count = request.getParameter("v_count");
	     if(StringUtil.isBlank(v_count)){
	    	 WebUtil.printText("error", response);
	    	 return;
	     }
	   
	     RSA_MD5 myRSA = new RSA_MD5();
	     String source=v_oid + v_pstatus + v_amount + v_moneytype + v_count;
	     loger.info("易支付反馈结果：v_oid="+v_oid+",v_pmode="+v_pmode+",v_pstring="+v_pstring+",v_pstatus="+v_pstatus+",v_amount="+v_amount);
	     int verifyStatus = myRSA.PublicVerifyMD5(ConfUtil.getContext("payease.public.key"),v_sign, source);
	     loger.info("易支付反馈校验结果：verifyStatus="+verifyStatus);
	     
	     if(0 != verifyStatus){
	    	 WebUtil.printText("error", response);
	    	 return;
	     }
	     // 解析订单数据入库
	     String [] oids =v_oid.split("\\|_\\|");
		 String [] pmdoes =v_pmode.split("\\|_\\|");
		 String [] pstatus=v_pstatus.split("\\|_\\|");
		 String [] pstrings =v_pstring.split("\\|_\\|");
		 String [] amounts = v_amount.split("\\|_\\|");
		 int orderSize = oids.length;
		 if (orderSize != pmdoes.length 
				 || orderSize != pstatus.length
				 || orderSize != pstrings.length){
			 WebUtil.printText("error", response);
	    	 return;
		 }
		 for (int i=0;i<orderSize;i++){
			 synchronized(lock) {
				    RechargeList rechargeList = payService.findByNo(oids[i]);
					if (ObjectUtil.equals(null, rechargeList)){
						loger.info("根据易支付回调返回值，未找到对应的订单！,达人单号："+oids[i]+",金额："+amounts[i]);
						return;
					}
					if (Double.valueOf(amounts[i]).doubleValue()!=rechargeList.getMoney().doubleValue()){
						loger.info("根据易支付回调返回值，订单充值金额不相符合！");
						return;
					}
					if (Constants.PayStatus.NOPROCESSING ==rechargeList.getStatus()){
						// 充值成功
						if (StringUtil.equals(pstatus[i],"1")){
							payService.updateEntity(oids[i], oids[i],"TRADE_SUCCESS",Dates.format(Dates.CHINESE_DATE_FORMAT_LINE),Constants.PayStatus.SUCCESS);//更新用户			
						}
						// 充值失败
						if (StringUtil.equals(pstatus[i],"3")){
							rechargeList.setStatus(1);
							rechargeList.setOktime(TypeConvert.dbDefaultDate());
							payService.update(rechargeList);
						}
					}
			 }
				
		 }
		 
	     WebUtil.printText("received", response);
	}
}
