package com.tzdr.business.pay.gopay.handle;

import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSONObject;
import com.tzdr.business.pay.Util;
import com.tzdr.business.pay.gopay.model.GoPayCallBackModel;
import com.tzdr.business.pay.gopay.model.GoPayRequestModel;

public class GoPayTradeData {
	/**
	 * 创建支付数据
	 * @param requestModel
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String createTradeData(GoPayRequestModel requestModel) throws UnsupportedEncodingException{
		JSONObject param = new JSONObject();
		param.put("Version", GoPayRequestModel.getVersion());
		param.put("Charset", GoPayRequestModel.getMerchantEncod());
		param.put("Language", GoPayRequestModel.getLanguage());
		param.put("signType", GoPayRequestModel.getSignType());
		param.put("tranCode", GoPayRequestModel.getTradCode());
		param.put("merchantID",GoPayRequestModel.getCustomerId());
		param.put("frontMerUrl", GoPayRequestModel.getFrontMerUrl());
		param.put("backgroundMerUrl", GoPayRequestModel.getBackgroundMerUrl());
		param.put("virCardNoIn", GoPayRequestModel.getPayAcctId());
		param.put("currencyType", GoPayRequestModel.getCurrencyType());
		param.put("isRepeatSubmit", GoPayRequestModel.getIsRepeatSubmit());
		param.put("merOrderNum", requestModel.getMerOrderNum());
		param.put("tranAmt", requestModel.getTranAmt());
		param.put("feeAmt", requestModel.getFeeAmt());
		param.put("tranDateTime", requestModel.getTranDateTime());
		param.put("tranIP", requestModel.getTranIP());
		param.put("goodsName", requestModel.getGoodsName());
		param.put("goodsDetail", requestModel.getGoodsDetail());
		param.put("buyerName", requestModel.getBuyerName());
		param.put("buyerContact", requestModel.getBuyerContact());
		param.put("signValue", md5EncodeSignValue(requestModel));
		return param.toJSONString();
	}
	/**
	 * 加密--获取密文串
	 * @param requestModel
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private static String md5EncodeSignValue(GoPayRequestModel requestModel) throws UnsupportedEncodingException{
		return Util.MD5Encode(encodeSignValue(requestModel));
	}
	/**
	 * 加密--获取密文串
	 * @param requestModel
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String md5CallBackEncodeSignValue(GoPayCallBackModel requestModel) throws UnsupportedEncodingException{
		return Util.MD5Encode(callBackEncodeSignValue(requestModel));
	}
	/**
	 * 获取明文串
	 * @param requestModel
	 * @return
	 */
	public static String encodeSignValue(GoPayRequestModel requestModel){
		String signValue = "version=["+GoPayRequestModel.getVersion()+"]"
				+ "tranCode=["+GoPayRequestModel.getTradCode()+"]"
				+ "merchantID=["+GoPayRequestModel.getCustomerId()+"]"
				+ "merOrderNum=["+requestModel.getMerOrderNum()+"]"
				+ "tranAmt=["+requestModel.getTranAmt()+"]"
				+ "feeAmt=["+requestModel.getFeeAmt()+"]"
				+ "tranDateTime=["+requestModel.getTranDateTime()+"]"
				+ "frontMerUrl=["+GoPayRequestModel.getFrontMerUrl()+"]"
				+ "backgroundMerUrl=["+GoPayRequestModel.getBackgroundMerUrl()+"]"
				+ "orderId=[]"
				+ "gopayOutOrderId=[]"
				+ "tranIP=["+requestModel.getTranIP()+"]"
				+ "respCode=[]"
				+ "gopayServerTime=[]"
				+ "VerficationCode=["+GoPayRequestModel.getVerficationCode()+"]";
		return signValue;
	}
	public static String callBackEncodeSignValue(GoPayCallBackModel requestModel){
		String signValue = "version=["+requestModel.getVersion()+"]"
				+ "tranCode=["+requestModel.getTradCode()+"]"
				+ "merchantID=["+requestModel.getMerchantID()+"]"
				+ "merOrderNum=["+requestModel.getMerOrderNum()+"]"
				+ "tranAmt=["+requestModel.getTranAmt()+"]"
				+ "feeAmt=["+requestModel.getFeeAmt()+"]"
				+ "tranDateTime=["+requestModel.getTranDateTime()+"]"
				+ "frontMerUrl=["+requestModel.getFrontMerUrl()+"]"
				+ "backgroundMerUrl=["+requestModel.getBackgroundMerUrl()+"]"
				+ "orderId=["+requestModel.getOrderId()+"]"
				+ "gopayOutOrderId=["+requestModel.getGopayOutOrderId()+"]"
				+ "tranIP=["+requestModel.getTranIP()+"]"
				+ "respCode=["+requestModel.getRespCode()+"]"
				+ "gopayServerTime=[]"
				+ "VerficationCode=[invtoolvsgfb2016]";
		return signValue;
	}
}
