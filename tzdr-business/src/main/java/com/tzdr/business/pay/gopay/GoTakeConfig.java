package com.tzdr.business.pay.gopay;

import java.io.IOException;

import com.tzdr.business.pay.gopay.handle.GoTakeTradeData;
import com.tzdr.business.pay.gopay.model.GoTakeRequestModel;


public class GoTakeConfig extends GoConfig{
	/**
	 * 编码方式
	 */
	private static String merchantEncod;
	/**
	 * 网关版本号
	 */
	private static String version;
	/**
	 * 交易代码
	 */
	private static String tradCode;
	/**
	 * 公私标识
	 */
	private static String corpPersonFlag;
	/**
	 * 是否需要企业审核
	 */
	private static String approve;
	
	
	public static String getCorpPersonFlag() {
		return corpPersonFlag;
	}

	public static String getApprove() {
		return approve;
	}

	public static void setCorpPersonFlag(String corpPersonFlag) {
		GoTakeConfig.corpPersonFlag = corpPersonFlag;
	}

	public static void setApprove(String approve) {
		GoTakeConfig.approve = approve;
	}

	public static String getMerchantEncod() {
		return merchantEncod;
	}

	public static void setMerchantEncod(String merchantEncod) {
		GoTakeConfig.merchantEncod = merchantEncod;
	}

	public static String getVersion() {
		return version;
	}

	public static void setVersion(String version) {
		GoTakeConfig.version = version;
	}

	public static String getTradCode() {
		return tradCode;
	}

	public static void setTradCode(String tradCode) {
		GoTakeConfig.tradCode = tradCode;
	}

	public static void main(String[] args) throws IOException {
		GoTakeRequestModel goRequestModel = new GoTakeRequestModel();
		goRequestModel.setDescription("test");
		goRequestModel.setMerOrderNum("A_8780_0");
		goRequestModel.setMerURL("http://www.xxx.com/reponse.do");
		goRequestModel.setRecvBankAcctName("何道清");//收款人银行 开户名 
		goRequestModel.setRecvBankAcctNum("6217003810053801323");//收款方银行 帐号
		goRequestModel.setRecvBankBranchName("成都天府新谷支行");//收款方银行 网点名称 
		goRequestModel.setRecvBankCity("成都");//收款方银行 所在城市
		goRequestModel.setRecvBankName("中国建设银行");//收款方银行 名
		goRequestModel.setRecvBankProvince("四川");// 收款方银行 所在省
		goRequestModel.setTranAmt("10.00");//交易金
		goRequestModel.setTranDateTime(DateUtil.getDate("yyyyMMddHHmmss"));//交易时间 
		goRequestModel.setSignValue(GoTakeTradeData.encodeSignValue(goRequestModel));//签名
		System.out.println(GoTakeTradeData.createTradData(goRequestModel));
	}
	
}
