package com.tzdr.domain.constants;

import java.math.BigDecimal;

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
		
		/**
		 * 直播 注册
		 */
		public static final int LIVE_REGIST = 6;
		
		/**
		 * 投资达人 APP
		 */
		public static final int APP_TZDR_REGIST = 7;
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
		/**
		 * 易支付
		 */
		public static final int EASE_PAY=3;
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
	 * 系统标记
	 * @author zhouchen
	 * 2016年5月11日 上午11:14:21
	 */
	public static class SystemFlag{
		public static final String TZDR_WEB = "tzdrweb"; 
		public static final String TZDR_APP = "tzdrapp"; 
		public static final String PGB_WEB = "pgbweb"; 
		
	}
	/**
	 * 期货合买游戏常量
	 * @author zhouchen
	 * 2016年5月25日 下午12:06:07
	 */
	public static class  FtogetherGame{
		
		/**
		 * 看涨
		 */
		public static final int CALL_DIRECTION = 1; 
		
		/**
		 * 看跌
		 */
		public static final int PUT_DIRECTION = 2;
		
		/**
		 * 退回
		 */
		public static final int BACK=1;
		
		/**
		 * 5分钟 - 300秒
		 */
		public static final Long FIVE_MINITE_SEC=5*60l;
		
		/**
		 *合买中
		 */
		public static final int JOINT_PURCHASE_STATUS = 1;
		
		/**
		 *操盘中
		 */
		public static final int OPERATE_STATUS = 2;
		
		/**
		 * 结算状态
		 */
		public static final int END_STATUS = 3;
		
		/**
		 *  常量值-0
		 */
		public static final int ZERO = 0;
		
		/**
		 * 活动类型 1
		 */
		public static final int ACTIVITY_TYPE_ONE=1;
		
		/**
		 * 当行情数值为空显示
		 */
		public static final String NULL_LINE_DATA="\"-\"";
		
		/**
		 * 最大购买分数
		 */
		public static final  int MAX_COPIES = 10000;
		
		/**
		 * 活动期间免90元
		 */
		public static final BigDecimal ACTIVITY_FREE_MONEY = new BigDecimal(90);
		
		
		/**
		 *  前端分页每页默认 10条
		 */
		public static final int PAGE_NUMBER = 10;
	}
	
	/**
	 * 投资达人app 新闻动态类目
	 */
	public static final String APP_NEWS_COLUNM="达人动态(APP)";
	
	/**
	 * 月月配 每月固定天数30
	 */
	public static final int MONTH_TRADE_MONTH_DAYS=30;
}
