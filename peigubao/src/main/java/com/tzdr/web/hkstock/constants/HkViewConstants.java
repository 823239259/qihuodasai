package com.tzdr.web.hkstock.constants;



/**
 * 页面跳转路径
 * 通用的直接写常量   带有模块性质的  写到内部类相应的模块下面
 * @author zhouchen
 * @version 创建时间：2014年12月5日 下午4:24:53 类说明
 */
public class HkViewConstants {
	
	/**
	 * 港股操盘处理相关页面
	 * @author zhouchen
	 * 2015年10月19日 上午9:28:25
	 */
	public static class HkUserTradeViews{
		/**
		 * 港股操盘首页
		 */
		public static final String HK_TRADE_INDEX="/views/hkstock/index";
		
		/**
		 * 确认页面
		 */
		public static final String HK_TRADE_CONFIRM="/views/hkstock/confirm";
		
		
		/**
		 * 配资成功页面
		 */
		public static final String TRADE_OK = "/views/hkstock/tradeOk";
		
		
		/**
		 * 配资失败页面
		 */
		public static final String TRADE_FAIL="/views/hkstock/tradeFail";
		
		/**
		 * 方案列表页面
		 */
		public static final String TRADE_LIST = "/views/hkstock/tradeList";
		
		/**
		 * 方案详情页面
		 */
		public static final String DETAIL = "/views/hkstock/tradeDetail";
		
		
		/**
		 * 操盘协议
		 */
		public static final String TRADECONTRACT="/views/hkstock/hkTradeContract";
	}
}
