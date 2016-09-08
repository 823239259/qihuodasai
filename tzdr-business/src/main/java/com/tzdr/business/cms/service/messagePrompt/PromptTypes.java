package com.tzdr.business.cms.service.messagePrompt;

/**
 * 邮件消息提示种类
 * 
 * @author
 *
 */
public class PromptTypes {

	/**
	 * 申请操盘
	 */
	public static final String isFutures = "isFutures";

	/**
	 * 补充保证金
	 */
	public static final String isAddBond = "isAddBond";

	/**
	 * 终结方案
	 */
	public static final String isEndScheme = "isEndScheme";

	/**
	 * 充值审核（支付宝）
	 */
	public static final String isAlipayRecharge = "isAlipay";

	/**
	 * 充值审核（银行）
	 */
	public static final String isBankReCharge = "isBank";
	/**
	 * 网银充值
	 */
	public static final String isInternetBanking = "isInternetBanking";

	/**
	 * 线下转账审核
	 */
	public static final String isLineTransfer = "isLine";

	/**
	 * 提现初审
	 */
	public static final String isTheTrial = "isTheTrial";

	/**
	 * 提现复审
	 */
	public static final String isReview = "isReview";
}
