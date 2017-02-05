package com.tzdr.business.pay.gopay.handle;

import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSONObject;
import com.tzdr.business.pay.Util;
import com.tzdr.business.pay.gopay.model.GoTakeRequestModel;

/**
 * 国付宝交易数据的处理
 * @author HEDAOQING
 * 2016.07.27
 */
public class GoTakeTradeData {
	/**
	 * 创建交易数据
	 * @throws UnsupportedEncodingException
	 */
	public static String createTradData(GoTakeRequestModel goRequestModel) throws UnsupportedEncodingException{
		 JSONObject param = new JSONObject();
		 param.put("version", GoTakeRequestModel.getVersion());
		 param.put("tradCode", GoTakeRequestModel.getTradCode());
		 param.put("customerId", GoTakeRequestModel.getCustomerId());
		 param.put("payAcctId", GoTakeRequestModel.getPayAcctId());
		 param.put("merchantEncod", GoTakeRequestModel.getMerchantEncod());
		 param.put("corpPersonFlag", GoTakeRequestModel.getCorpPersonFlag());
		 param.put("approve", GoTakeRequestModel.getApprove());
		 param.put("signValue", goRequestModel.getSignValue());
		 param.put("merOrderNum", goRequestModel.getMerOrderNum());
		 param.put("merURL", goRequestModel.getMerURL());
		 param.put("tranAmt", goRequestModel.getTranAmt());
		 param.put("recvBankAcctName", goRequestModel.getRecvBankAcctName());
		 param.put("recvBankAcctNum", goRequestModel.getRecvBankAcctNum());
		 param.put("recvBankProvince", goRequestModel.getRecvBankProvince());
		 param.put("recvBankCity", goRequestModel.getRecvBankCity());
		 param.put("recvBankName", goRequestModel.getRecvBankName());
		 param.put("recvBankBranchName", goRequestModel.getRecvBankBranchName());
		 param.put("tranDateTime", goRequestModel.getTranDateTime());
		 param.put("description", goRequestModel.getDescription());
		 return param.toJSONString();
	}
	/**
	 * 加密-获取密文串
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encodeSignValue(GoTakeRequestModel goRequestModel) throws UnsupportedEncodingException{
		String sign = "version=["+GoTakeRequestModel.getVersion()+"]"
					+ "tranCode=["+GoTakeRequestModel.getTradCode()+"]"
					+ "customerId=["+GoTakeRequestModel.getCustomerId()+"]"
					+ "merOrderNum=["+goRequestModel.getMerOrderNum()+"]"
					+ "tranAmt=["+goRequestModel.getTranAmt()+"]"
					+ "feeAmt=[]"
					+ "totalAmount=[]"
					+ "merURL=["+goRequestModel.getMerURL()+"]"
					+ "recvBankAcctNum=["+goRequestModel.getRecvBankAcctNum()+"]"
					+ "tranDateTime=["+goRequestModel.getTranDateTime()+"]"
					+ "orderId=[]"
					+ "respCode=[]"
					+ "payAcctId=["+GoTakeRequestModel.getPayAcctId()+"]"
					+ "approve=["+GoTakeRequestModel.getApprove()+"]"
					+ "VerficationCode=["+GoTakeRequestModel.getVerficationCode()+"]";
		return Util.MD5Encode(sign);
	}
}
