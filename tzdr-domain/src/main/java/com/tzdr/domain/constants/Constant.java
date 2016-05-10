package com.tzdr.domain.constants;

public interface Constant {

	public static class FeeType {
		/**
		 * 0:自动开户(钱江版)，
		 */
		public static final  short AUTO_OPEN_CASH_RIVER = 0;
		/**
		* 1:手工开户(钱江版)
		*/
		public static final short HAND_OPEN_CASH_RIVER  = 1;
		
		/**
		 * ，2：手工开户(涌金版) 
		 */
		public static final short HAND_OPEN_WELL_GOLD  = 2;
		/**
		 * 3:手工开户(同花顺)
		 */
		public static final short HAND_OPEN_TIERCE  = 3;
		
		/**
		 * 0 1 组合 钱江版
		 */
		public static final String CASH_RIVER_FEETYPE="0,1";
	}
	
	
	public static class HandTradeType {
		
		/**
		 * 0：钱江版
		 */
		public static final  int CASH_RIVER_TYPE = 0;
		
		/**
		 *   1：涌金版  
		 */
		public static final int WELL_GOLD_TYPE  =1;
		/**
		 *   2：同花顺手动
		 */
		public static final int TIERCE_TYPE  = 2;
	}
	
	/**
	 * 注册来源
	 * @zhouchen
	 * 2015年6月23日
	 */
	public static class RegistSource {
		
		/**
		 *  web 注册
		 */
		public static final  int WEB_REGIST = 1;
		
		/**
		 *   wap 配股宝 注册
		 */
		public static final int WAP_REGIST  =2;
		/**
		 *   bbs 注册
		 */
		public static final int BBS_REGIST  =3;
		
		/**
		 *   P2P 注册
		 */
		public static final int P2P_REGIST  =4;
		
		
		/**
		 *   wap 配股宝 注册
		 */
		public static final int WAP_PEIGUBAO_REGIST  =5;
	}
	
	/**
	 * 母账户 限制使用次数  
	 */
	public static final int PARENT_ACCOUNT_LIMIT_NUMBER = 1000000000;
	
	/**
	 * 支付渠道
	 * @zhouchen
	 * 2015年12月2日
	 */
	public static class PaymentChannel{
		/**
		 * 联动优势
		 */
		public static final int UM_PAY = 1; 
		/**
		 * 币币支付
		 */
		public static final int BB_PAY = 2;
	}
	
	
	/**
	 * 来源
	 * @zhouchen
	 * 2015年12月2日
	 */
	public static class Source{
		/**
		 * 投资达人
		 */
		public static final int TZDR = 1; 
		/**
		 * 配股宝
		 */
		public static final int PGB = 2;
	}
	
	/**
	 * 投资达人app 新闻动态类目
	 */
	public static final String APP_NEWS_COLUNM="达人动态(APP)";
}
