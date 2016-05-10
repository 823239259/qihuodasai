package com.tzdr.cms.hkstock.constants;

/**
 * 页面跳转路径 通用的直接写常量 带有模块性质的 写到内部类相应的模块下面
 * 
 * @author zhouchen
 * @version 创建时间：2014年12月5日 下午4:24:53 类说明
 */
public class HkViewConstants {

	/**
	 * 港股参数设置
	 * 
	 * @Description:
	 * @author liuhaichuan
	 * @date 2015年10月19日
	 *
	 */
	public static class HkStockParamsJsp {
		/**
		 * 信息页
		 */
		public static String LIST_VIEW = "/views/admin/hkstock/hkStockParams/list";
	}

	/**
	 * 港股开户审核
	 * @author WangPinQun
	 *
	 */
	public static class WellGoldJsp{
		
		/**
		 * 开户审核列表【待审核列表、已审核记录】
		 */
		public static String LIST_VIEW = "/views/admin/hkstock/wellgold/list";
		
		/**
		 * 编辑页面
		 */
		public static  final String WELLGOLD_EDIT_VIEW="/views/admin/hkstock/wellgold/edit";
	}
	
	/**
	 * 港股母账户列表
	 * @Description: 
	 * @author liuhaichuan
	 * @date 2015年10月19日
	 *
	 */
	public static class HkParentAccountJsp{
		
		/**
		 * 信息页
		 */
		public static String LIST_VIEW="/views/admin/hkstock/hkParentAccount/list";
		
		/**
		 * 编辑页
		 */
		public static String EDIT_VIEW="/views/admin/hkstock/hkParentAccount/edit";
	}

	/**
	 * 
	 * 
	 * <p>终结方案审核</p>
	 * @author WangPinQun
	 * @see
	 * @version 2.0
	 * 2015年10月20日上午9:28:29
	 */
	public static class EndTradeViewJsp {
		
		/**
		 * 终结方案列表【待审核列表【审1】、待审核列表【审2】、已审核记录】
		 */
		public static final String LIST_VIEW = "/views/admin/hkstock/endtrade/list";
	}
	
	/**
	 * 港股交易日
	 * @Description: 
	 * @author liuhaichuan
	 * @date 2015年10月20日
	 *
	 */
	public static class HkTradeDayJsp{
		/**
		 * 编辑页
		 */
		public static String EDIT_VIEW="/views/admin/tradeday/hkedit";
	}
	
	/**
	 * 港股欠费方案
	 * @Description: 
	 * @author liuhaichuan
	 * @date 2015年10月20日
	 *
	 */
	public static class HkArrearsEndJsp{
		
		/**
		 * 列表页
		 */
		public static String LIST_VIEW="/views/admin/hkstock/hkArrearsEnd/list";
	}
	
	/**
	 * 港股方案管理
	 * @Description: 
	 * @author liuhaichuan
	 * @date 2015年10月21日
	 *
	 */
	public static class HkTradeManageJsp{
		
		/**
		 * 列表页
		 */
		public static String LIST_VIEW="/views/admin/hkstock/hkTradeManage/list";
		
		/**
		 * 子方案页
		 */
		public static String DETAIL_VIEW="/views/admin/hkstock/hkTradeManage/detail";
	}
	
	/**
	 * 港股佣金过户差
	 * @Description: 
	 * @author liuhaichuan
	 * @date 2015年10月27日
	 *
	 */
	public static class HkFreeDiffJsp{
		/**
		 * 编辑页
		 */
		public static String EDIT_VIEW="/views/admin/freediff/hkedit";
	}
	
	
	/**
	 * 港股追加保证金
	 */
	public static class hkappendLevelMoney{
		public static String LIST_VIEW="/views/admin/hkstock/hkappendLevelMoney/list";
	}
}
