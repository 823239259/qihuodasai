
/**
 * @zhouchen
 * 2015年10月13日
 */
package com.tzdr.web.controller.bbpay;

import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.bebepay.component.encrypt.AES;
import com.bebepay.component.encrypt.EncryUtil;
import com.bebepay.component.encrypt.RSA;
import com.tzdr.business.service.pay.PayService;
import com.tzdr.common.api.bbpay.util.BbConfigUtil;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.web.entity.RechargeList;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.utils.WebUtil;

@Controller
@RequestMapping("/bbpay")
public class BbpayCallbackController{
	private static Logger loger = LoggerFactory.getLogger(BbpayCallbackController.class);
	
	@Autowired
	private PayService  payService;

	/**
	 * pc网上银行支付后台通知回调
	 */
	@RequestMapping(value = "/callback")
	public void asynreturnURL(String data,String encryptkey,HttpServletResponse response) throws Exception {
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		String bbPublicKey =  BbConfigUtil.getContext("bebepayPublicKey");
		String merchantPrivateKey = BbConfigUtil.getContext("merchantPrivateKey");
		TreeMap<String, String> map = EncryUtil.checkDecryptAndSign(data, encryptkey, bbPublicKey, merchantPrivateKey);
		// 验签失败
		if (CollectionUtils.isEmpty(map)){
			loger.info("币币支付接口回调，验签失败！");
			return;
		}
		// 验签成功
		String yb_aeskey = RSA.decrypt(encryptkey,merchantPrivateKey);
		String payresult_view = AES.decryptFromBase64(data,yb_aeskey);
		loger.info("币币支付接口请求的结果（aes解密后的明文）：" + payresult_view);
		JSONObject result = JSONObject.parseObject(payresult_view);
		// 获取返回值
		String bborderid = result.getString("bborderid");  // 币币的订单号
		String order = result.getString("order"); //商户订单号
		int status = result.getIntValue("status"); // 订单状态
		double amount = result.getDoubleValue("amount");
		RechargeList rechargeList = payService.findByNo(order);
		if (ObjectUtil.equals(null, rechargeList)){
			loger.info("根据币币支付回调返回值，未找到对应的订单！币币单号："+bborderid+",达人单号："+order+",金额："+amount);
			return;
		}
		if (BigDecimalUtils.divRound(amount,100)!=rechargeList.getMoney()){
			loger.info("根据币币支付回调返回值，订单充值金额不相符合！");
			return;
		}
		if (Constants.PayStatus.NO_PROCESSING ==rechargeList.getStatus()){
			// 充值成功
			if (status==1){
				payService.updateEntity(order, bborderid,"TRADE_SUCCESS",Dates.format(Dates.CHINESE_DATE_FORMAT_LINE),Constants.PayStatus.SUCCESS);//更新用户			
			}
			// 充值失败
			if (status==0){
				rechargeList.setStatus(1);
				rechargeList.setOktime(TypeConvert.dbDefaultDate());
				payService.update(rechargeList);
			}
		}
		
		WebUtil.printText("YES", response);
	}
}
