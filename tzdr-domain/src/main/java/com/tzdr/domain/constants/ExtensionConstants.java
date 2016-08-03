package com.tzdr.domain.constants;

public class ExtensionConstants {
	/**
	 * 抽奖
	 */
	public static final String REWARD_TYPE_LUCK_DRAW = "0";
	/**
	 * 补贴
	 */
	public static final String REWARD_TYPE_SUBSIDY = "1";
	/**
	 * 交易10手
	 */
	public static final Integer SUBSIDYLEVER10 = 10;
	/**
	 * 交易10手补贴
	 */
	public static final Double  SUBSIDY10MONEY = 500.00;
	/**
	 * 交易20手
	 */
	public static final Integer SUBSIDYLEVER20 = 20;
	/**
	 * 交易20手补贴
	 */
	public static final Double  SUBSIDY20MONEY = 1000.00;
	/**
	 * 交易40手
	 */
	public static final Integer SUBSIDYLEVER40 = 40;
	/**
	 * 交易40手补贴
	 */
	public static final Double  SUBSIDY40MONEY = 2000.00;
	/**
	 * 当前活动代码：目前未定：默认001
	 */
	public static final String ACTIVITY_TYPE = "001";
	public static class  SubsidyType{
		public static final String LUCK_DRAW= "0";//抽奖
		public static final String SUBA50 = "1";//A50补贴
	}
}
