package com.tzdr.cms.constants;

import javax.swing.Spring;

/**
 * 
 * <B>说明: </B> 页面跳转路径常量配置， 通用的直接写常量   带有模块性质的  写到内部类相应的模块下面。
 * @zhouchen
 * 2016年1月20日 下午1:38:12
 */
public class ViewConstants {
	
	/**
	 * 异常页面
	 */
	public static final String ERROR_VIEW = "/error";

	/**
	 * 主页
	 */
	public static final String MAIN_VIEW="/views/admin/mainframe";
	

	/**
	 * 登录页面
	 */
	public static final String ADMIN_LOGIN_VIEW = "/views/admin/login";

	/**
	 *test 主页面
	 */
	public static final String ADMIN_MAIN_VIEW = "/views/admin/main";


	/**
	 * 权限相关的jsp 页面
	 * @zhouchen
	 * 2014年12月12日
	 */
	public static class PermissionViewJsp {
		
		/**
		 * 进入权限列表
		 */
		public static final String PERMISSION_LIST_VIEW="/views/admin/permission/permissionList";
		
		/**
		 * 进入授权角色给实体
		 */
		public static final String GRANT_ROLE_VIEW="/views/admin/permission/grantRole";
		
		
		/**
		 * 进入资源列表
		 */
		public static final String RESOURCE_LIST_VIEW="/views/admin/resource";
		
		
		/**
		 * 角色列表
		 */
		public static final String ROLE_LIST_VIEW="/views/admin/permission/roleList";
		
		
		
		/**
		 * 角色 新增或修改 权限
		 */
		public static final String EDIT_ROLE_VIEW="/views/admin/permission/editRole";
	}
	
	/**
	 * 
	 * <p></p>
	 * @author QingLiu
	 * @see 静态页面
	 * @version 2.0
	 * 2015年2月4日下午2:52:47
	 */
	public static class StatusPageViewJsp {
		/**
		 * 用户列表页面
		 */
		public static final String LIST_VIEW="/views/admin/statusPage/page";
	}
	
	/**
	 * User相关页面  管理员用户
	 * @zhouchen
	 * 2014年12月12日
	 */
	public static class UserViewJsp{
		/**
		 * 用户列表页面
		 */
		public static final String  LIST_VIEW="/views/admin/user/list";
		/**
		 * 新增或编辑页面 共用
		 */
		public static final String  EDIT_VIEW="/views/admin/user/edit";
	}
	
	/**
	 * 
	 * 
	 * <p>终结方案审核</p>
	 * @author LiuQing
	 * @see
	 * @version 2.0
	 * 2015年5月11日上午9:28:29
	 */
	public static class EndPlanViewJsp {
		public static final String LIST_VIEW = "/views/admin/plan/end/list";
	}
	
	/**
	 * 
	 * 
	 * <p>终结方案审核</p>
	 * @author LiuQing
	 * @see
	 * @version 2.0
	 * 2015年5月25日上午9:28:29
	 */
	public static class ParentAccountExpireEndViewJsp {
		public static final String LIST_VIEW = "/views/admin/parentAccount/expire/list";
	}
	
	
	/**
	 * @see
	 * @author LiuQing
	 *
	 */
	public static class EarningsReportViewJsp {
		public static final String LIST_VIEW = "/views/admin/reports/earnings/report";
	}
	/**
	 * 过户费差
	 * @author 张军
	 *
	 */
	public static class FreeDiffViewJsp {
		public static final String LIST_VIEW="/views/admin/freediff/freediffList";
		/**
		 * 充值审核规则编辑表单
		 */
		public static final String EDIT_FREEDIFF = "/views/admin/freediff/editFreediff";
		
		
	}
	
	/**
	 * 第三方数据保存
	 * @author Administrator
	 *
	 */
	public static class ContractsaveViewJsp {
		public static final String LIST_VIEW="/views/admin/contractsave/contractsaveList";
		public static final String VIEW_INFO = "/views/admin/contractsave/viewInfo";
	}
	/**
	 * 
	 * <p>页面地址映射</p>
	 * @author LiuQing
	 * @see Spring MVC 地址
	 * @version 2.0
	 * 2014年12月30日上午9:37:14
	 */
	public static class WuserViewJsp {
		/**
		 * 显示列表name
		 */
		public static final String LIST_VIEW="/views/admin/wuser/list";
		
		/**
		 * 显示列表name
		 */
		public static final String LIST_AGENTS_VIEW="/views/admin/wuser/agents_list";
		
		/**
		 * 显示列表name
		 */
		public static final String LIST_AGENTS_CHILD_VIEW="/views/admin/wuser/agents_child_list";
		
		/**
		 * 活动列表显示页面
		 */
		public static final String LIST_ACTIVITY_VIEW="/views/admin/activityWuser/activity_wuser_list";
		
		/**
		 *      
		 */
		public static final String LIST_CHECK_VIEW="/views/admin/wuser/check_list";
		
		/**
		 *    设置预警值  
		 */
		public static final String LIST_SETTING_WARNING_VIEW="/views/admin/wuser/setting_warning_list";
		
		/**
		 *    终结方案
		 */
		public static final String LIST_END_VIEW="/views/admin/wuser/end_list";
		
		
		/**
		 *    身份证查询
		 */
		public static final String LIST_IDENTITY_VIEW="/views/admin/wuser/identity_list";
		
		/**
		 *    短信通道列表页面
		 */
		public static final String SMS_CHANNEL_LIST_VIEW="/views/admin/wuser/smsChannel_list";
		
		/**
		 *    短信通道编辑页面
		 */
		public static final String SMS_CHANNEL_EDIT_VIEW="/views/admin/wuser/smsChannel_edit";
		
	}
	
	/**
	 * 
	 * 
	 * <p></p>
	 * @author LiuQing
	 * @see
	 * @version 2.0
	 * 2015年4月28日上午9:13:03
	 */
	public static class VolumeViewJsp {
		
		/**
		 * 
		 */
		public static final String LIST_VIEW = "/views/admin/volume/volume_list";
		
		/**
		 * 
		 */
		public static final String DETAIL_VIEW = "/views/admin/volume/volume_detail";
		
	}
	
	/**
	 * 
	 * <p></p>
	 * @author QingLiu
	 * @see
	 * @version 2.0
	 * 2015年2月7日上午10:19:12
	 */
	public static class FreezeFailInfoViewJsp {
		
		/**
		 * 失败方案
		 */
		public static final String LIST_VIEW = "/views/admin/freezeFailInfo/list";
	}
	
	/**
	 * 
	 * <p></p>
	 * @author LiuQing
	 * @see
	 * @version 2.0
	 * 2015年1月5日下午5:18:25
	 */
	public static class RechargeViewJsp {
		
		/**
		 * 
		 */
		public static final String LIST_VIEW = "/views/admin/recharge/recharge_list";
		
		/**
		 * 手工充值记录列表
		 */
		public static final String LIST_VIEW_HANDLER = "/views/admin/recharge/handler_recharge_list";
		/**
		 * 充值记录查询列表
		 */
		public static final String LIST_VIEW_RECHARGE_QUERY = "/views/admin/recharge/recharge_query";
		
		/**
		 * 补录充值记录查询列表
		 */
		public static final String LIST_VIEW_RECHARGE_ADDITIONAL = "/views/admin/recharge/rechargeAdditional";
		
		/**
		 * 补录充值表单
		 */
		public static final String EIDT_VIEW_RECHARGE_ADDITIONAL = "/views/admin/recharge/editRechargeAdditional";
		
		
		/**
		 * 充值审核规则查询列表
		 */
		public static final String LIST_VIEW_RECHARGE_AUDITRULE = "/views/admin/recharge/rechargeAuditrule";
		
		/**
		 * 充值审核规则编辑表单
		 */
		public static final String EIDT_VIEW_RECHARGE_AUDITRULE = "/views/admin/recharge/editRechargeAuditrule";
		
		/**
		 * 手工批量现金充值
		 */
		public static final String BATCH_HAND_RECHARGE_LIST = "/views/admin/recharge/batchHandRecharge";
		
		
	}
	
	
	/**
	 * 恒生账户管理页面
	 * @zhouchen
	 * 2014年12月12日
	 */
	public static class HundsunAccountViewJsp{
		/**
		 * 母账户列表页面
		 */
		public static final String  PARENT_ACCOUNT_LIST_VIEW="/views/admin/hundsunAccount/parentList";
		/**
		 * 子账户列表页面
		 */
		public static final String  SUB_ACCOUNT_LIST_VIEW="/views/admin/hundsunAccount/subList";
		
		/**
		 * 编辑母账户页面
		 */
		public static final String  PARENT_ACCOUNT_EDIT_VIEW="/views/admin/hundsunAccount/editParent";
		/**
		 * 编辑母账户余额
		 */
		public static final String  EIDT_PARENT_BALANCE_VIEW="/views/admin/hundsunAccount/editParentBalance";
		
		/**
		 * 编辑子账户
		 */
		public static final String  SUB_ACCOUNT_EDIT_VIEW="/views/admin/hundsunAccount/editSub";
		
		
		/**
		 * 导入子账户
		 */
		public static final String IMPORT_ACCOUNT_VIEW="/views/admin/hundsunAccount/importSub";
		
		/**
		 * 已终结 未注销的账户
		 */
		public static final String NOT_CANCEL_LIST_VIEW="/views/admin/hundsunAccount/notCancelList";
		
		/**
		 * 修改母账户密码
		 */
		public static final String  CHANGE_PARENT_ACCOUNT_PASSWORD_VIEW="/views/admin/hundsunAccount/changeParentAccountPassword";
	}
	
	/**
	 * 组织机构相关页面
	 * @zhouchen
	 * 2014年12月12日
	 */
	public static class OrganizationViewJsp{
		/**
		 * 组织机构
		 */
		public static final String  LIST_VIEW="/views/admin/organization/list";
		
		/**
		 * 点击组织结构树节点 显示他下面的节点列表
		 */
		public static final String NODE_LIST_VIEW="/views/admin/organization/nodeList";
		
		/**
		 * 新增或编辑页面 共用
		 */
		public static final String  EDIT_VIEW="/views/admin/organization/edit";
	}
	
	
	/**
	 * 提现记录  相关页面
	 * @zhouchen
	 * 2015年1月3日
	 */
	public static class WithdrawalViewJsp{
		/**
		 * 列表页面
		 */
		public static final String LIST_VIEW="/views/admin/withdrawal/list";
		
		/**
		 * 提现审核金额维护
		 */
		public static final String MONEY_VIEW="/views/admin/withdrawal/moneyList";
		
		/**
		 * 提现审核金额维护
		 */
		public static final String MONEY_EDIT_VIEW="/views/admin/withdrawal/editMoney";
		
		/**
		 * 获取资金明细
		 */
		public static final String USER_FUND_LIST_VIEW="/views/admin/withdrawal/userFundDetail";
		
		/**
		 * 审核不通过原因显示页面
		 */
		public static final String NOT_PASS_VIEW="/views/admin/withdrawal/notPass";
		
		/**
		 * 审核不通过原因显示页面
		 */
		public static final String NO_AUTHORITY_VIEW="/views/admin/withdrawal/noAuthority";
		/**
		 * 待审核列表
		 */
		public static final String AUDIT_LIST_VIEW="/views/admin/withdrawal/auditList";
		
		

		/**
		 *  恒生资金列表
		 */
		public static final String HUNDSUN_FUND_LIST_VIEW="views/admin/reports/hundSunFund/report";
		
		/**
		 *  达人资金列表
		 */
		public static final String USER_FUNDS_REPORT_LIST_VIEW="views/admin/reports/userfund/report";
		
		/**
		 *  A50收益报表显示列表
		 */
		public static final String PROFIT_REPORT_A50_LIST_VIEW="views/admin/profitReportA50/list";
		
		/**
		 *  国际综合收益报表显示列表
		 */
		public static final String PROFIT_REPORT_OUTDISK_LIST_VIEW="views/admin/profitReportOutDisk/list";
		
	}
	

	/**
	 * 交易日维护  相关页面
	 * @zhouchen
	 * 2015年1月3日
	 */
	public static class TradeDayViewJsp{
		/**
		 * 列表页面
		 */
		public static final String LIST_VIEW="/views/admin/tradeday/list";
		
		
		/**
		 *  更改是否为交易日
		 */
		public static final String EDIT_VIEW="/views/admin/tradeday/edit";
		
		/**
		 * 每日汇率维护页面
		 */
		public static final String PARITIES_LIST_VIEW="/views/admin/parities/list";
		/**
		 * 每日汇率维护页面
		 */
		public static final String PARITIES_EDIT_VIEW="/views/admin/parities/edit";
	}
		
	/**
	 * 配资参数维护  相关页面
	 * @zhouchen
	 * 2015年1月26日
	 */
	public static class TradeConfigViewJsp{
		/**
		 * 列表页面
		 */
		public static final String LIST_VIEW="/views/admin/tradeConfig/list";
		
		
		/**
		 * 编辑页面
		 */
		public static final String EDIT_VIEW="/views/admin/tradeConfig/edit";
	}
	/**
	 * 配资相关页面
	 * @zhouchen
	 * 2014年12月12日
	 */
	public static class MatchFundsViewJsp{
		/**
		 * 补仓提醒数据
		 */
		public static  final String MARGINREMIND_LIST_VIEW="/views/admin/userTrade/marginRemindLlist";
		
		/**
		 * 余额不足提醒
		 */
		public static  final String NOTENOGH_BALANCE_LIST_VIEW="/views/admin/userTrade/notEnoghBalanceLlist";
		
		/**
		 * 限制买入
		 */
		public static  final String LIMIT_BUY_LIST_VIEW="/views/admin/limitBuy/list";
		
		
		/**
		 * 取消限制买入
		 */
		public static  final String CANCEL_LIMIT_BUY_LIST_VIEW="/views/admin/limitBuy/cancleList";

		
		/**
		 * 方案监控列表
		 */
		public static  final String MONITOR_SCHEME_LIST_VIEW="/views/admin/userTrade/monitorList";
		
		/**
		 * 补仓审核列表
		 */
		public static  final String MONITOR_SCHEME_COVER_AUDIT_LIST_VIEW="/views/admin/userTrade/coverAuditList";
		

		/**
		 * 配资失败列表
		 */
		public static  final String TRADE_FAIL_LIST_VIEW="/views/admin/userTrade/failList";
		

		/**
		 * 所有配资列表
		 */
		public static  final String ALL_TRADE_LIST_VIEW="/views/admin/userTrade/allList";
		
		/**
		 * 配资子列表
		 */
		public static  final String TRADE_DETAIL_VIEW="/views/admin/userTrade/tradeDetail";
		
		
		/**
		 * 方案明细列表
		 */
		public static  final String USERFUND_DETAIL_VIEW="/views/admin/userTrade/userFundDetail";
		
		/**
		 * 查看代码
		 */
		public static  final String QUERY_THING_VIEW="/views/admin/userTrade/queryThing";
		
		
		/**
		 * 终结划账失败列表
		 */
		public static  final String END_FAIL_LIST_VIEW="/views/admin/userTrade/endFailList";
		
		
		
		/**
		 * 追加保证金失败 列表
		 */
		public static  final String APPENDMONEY_FAIL_LIST_VIEW="/views/admin/userTrade/appendLevelMoneyFail";
		
		
		/**
		 * 手工开户列表页面
		 */
		public static  final String HAND_TRADE_LIST_VIEW="/views/admin/handTrade/list";
		
		
		/**
		 * 手工开户编辑页面
		 */
		public static  final String HAND_TRADE_EDIT_VIEW="/views/admin/handTrade/edit";
		
		
		/**
		 * 涌金版审核列表页面
		 */
		public static  final String WELLGOLD_LIST_VIEW="/views/admin/wellGold/list";
		
		
		/**
		 * 涌金版审核编辑页面
		 */
		public static  final String WELLGOLD_EDIT_VIEW="/views/admin/wellGold/edit";
		
		/**
		 *涌金版终结方案弹出框
		 */
		public static  final String END_TRADE="/views/admin/userTrade/endTrade";
		
		/**
		 * 富时A50开户审核列表页面
		 */
		public static  final String WELLGOLDA50_LIST_VIEW="/views/admin/ftse/list";
		
	}	
	
		
	
	
	/**
	 * 追加保证金
	 * @author 张军
	 *
	 */
	public static class UserFundMoneyViewJsp{
		public static  final String LIST_VIEW="/views/admin/userfundmoney/list";
		
		public static  final String LIST_PROFIT_VIEW="/views/admin/userfundmoney/listProfit";
	}
	
	/**
	 * 配资用户汇总信息
	 * @author 张军
	 *
	 */
	public static class UserTradeSummaryViewJsp{
		public static  final String LIST_VIEW="/views/admin/userTradeSummary/list";
	}
	
	/**
	 * 运营维护配置相关页面
	 * @zhouchen
	 * 2014年12月12日
	 */
	public static class OpertinalConfigViewJsp{
		/**
		 * 股票合买参数设置页面
		 */
		public static  final String TOGETHER_TRADE_CONFIG_VIEW="/views/admin/togetherTradeConfig/list";
		
		/**
		 * 列表页面
		 */
		public static  final String LIST_VIEW="/views/admin/operationalConfig/list";
		
		/**
		 * 友情连接编辑页面
		 */
		public static  final String FRIENDLY_LINK_EDIT_VIEW="/views/admin/operationalConfig/editFriendlyLink";
		

		/**
		 *合作伙伴编辑页面
		 */
		public static  final String PARTNERS_EDIT_VIEW="/views/admin/operationalConfig/editPartners";
		
		
		/**
		 * BANNER编辑页面
		 */
		public static  final String BANNER_EDIT_VIEW="/views/admin/operationalConfig/editBanner";
		
		
		/**
		 * 新闻编辑页面
		 */
		public static  final String NEWS_EDIT_VIEW="/views/admin/operationalConfig/editNews";
		
		
		/**
		 * 新闻栏目编辑页面
		 */
		public static  final String NEWS_COLUMN_EDIT_VIEW="/views/admin/operationalConfig/editNewsColumn";
		
		
		
		/**
		 * 数据字典列表
		 */
		public static  final String DATA_MAP_LIST_VIEW="/views/admin/datamap/list";
		
		
		/**
		 * 编辑数据字典
		 */
		public static  final String DATA_MAP_EDIT_VIEW="/views/admin/datamap/edit";
	}	
	
	/**
	 * 出资人信息  相关页面
	 * @zhouchen
	 * 2015年1月26日
	 */
	public static class InvestorViewJsp{
		/**
		 * 列表页面
		 */
		public static final String LIST_VIEW="/views/admin/investor/list";
		
		
		/**
		 * 编辑页面
		 */
		public static final String EDIT_VIEW="/views/admin/investor/edit";
	}
	
	
	/**
	 * 意见反馈  相关页面
	 * @zhouchen
	 * 2015年1月26日
	 */
	public static class MessageViewJsp{
		/**
		 * 列表页面
		 */
		public static final String LIST_VIEW="/views/admin/message/list";
		
		
		/**
		 * 编辑页面
		 */
		public static final String EDIT_VIEW="/views/admin/message/edit";
	}
	
	/**
	 * 用户返利  相关页面
	 * @wangpinqun
	 * 2015年3月19日
	 */
	public static class CommissionViewJsp{
		/**
		 * 列表页面
		 */
		public static final String LIST_VIEW="/views/admin/commission/list";
		
	}
	
	
	/**
	 * 股票信息  相关页面
	 * @zhouchen
	 * 2015年1月26日
	 */
	public static class StockViewJsp{
		/**
		 * 列表页面
		 */
		public static final String LIST_VIEW="/views/admin/stock/list";
		
		
		/**
		 * 编辑页面
		 */
		public static final String EDIT_VIEW="/views/admin/stock/edit";
		
		
		 /**
		  * 拥有停牌股的用户列表
		 */
		public static final String USER_SUSPENDED_VIEW="/views/admin/stock/userSuspended";
	}
	
	/**
	 * 
	 * 公告管理
	 * @author liuhaichuan
	 * @date 2015年4月29日
	 *
	 */
	public static class NoticeViewJsp{
		
		/**
		 * 列表页面
		 */
		public static final String LIST_VIEW="/views/admin/notice/list";
		
		/**
		 * 编辑页面
		 */
		public static final String EDIT_VIEW="/views/admin/notice/edit";
	}
	
	/**
	 * 配资倍数和金额
	 * @ClassName FundConfigJsp
	 * @author L.Y
	 * @email meiluohai@163.com
	 * @date 2015年4月27日
	 */
	public static class FundConfigJsp {
		/**
		 * 列表页面
		 */
		public static final String LIST_VIEW = "/views/admin/fundConfig/list";
		
		/**
		 * 编辑数据
		 */
		public static  final String EDIT_VIEW="/views/admin/fundConfig/edit";
	}
	
	/**
	 * 提现黑名单
	 * @author Administrator
	 *
	 */
	public static class DrawBlackListViewJsp{
		public static final String LIST_VIEW = "/views/admin/drawBlackList/list";
		public static String EDIT_DRAW_BLACK= "/views/admin/drawBlackList/editDrawBlackList";
	}
	
	public static class DrawMoneyDataViewJsp{
		public static String LIST_VIEW = "/views/admin/drawMoneyData/list";
		public static String EDIT= "/views/admin/drawMoneyData/edit";
		public static String EDIT_DRAW_MONEY_DATA= "/views/admin/drawMoneyData/editDrawMoneyData";
	}
	/**
	 * 
	 *定制配资
	 */
	public static class BespokeTradeJsp{
		public static String LIST_VIEW = "/views/admin/bespokeTrade/list";
		
		/**
		 * 
		 */
		public static String SCHEME_SETPARAMETER_LIST_VIEW = "/views/admin/schemeSetParameter/list";
	}
	
	/**
	 * 销售客户信息
	 */
	public static class CustomerJsp{
		/**
		 * 销售客户信息列表
		 */
		public static String LIST_VIEW = "/views/admin/customer/list";
		
		/**
		 * 销售客户联系详情
		 */
		public static String DETAILS_VIEW = "/views/admin/customer/details";
	}
	
	/**
	 * 穿仓列表
	 * @ClassName BreakStoreJsp
	 * @author L.Y
	 * @email liuyuan@peigubao.com
	 * @date 2015年7月6日
	 */
	public static class BreakStoreJsp {
		public static final String LIST_VIEW = "/views/admin/breakStore/list";
	}
	/**
	 * 
	 *股指期货抢先版
	 */
	public static class SpifJsp{
		public static String LIST_VIEW = "/views/admin/spif/list";
		
	}
	
	/**
	 * 运营维护——商品期货参数设置
	 *
	 */
	public static class CommodityFutureParamJsp{
		/**
		 * 信息列表页
		 */
		public static String LIST_VIEW="/views/admin/commodityFuture/futureParam/list";
		
		/**
		 * 编辑页
		 */
		public static String EDIT_VIEW="/views/admin/commodityFuture/futureParam/edit";
	}
	
	/**
	 * 风控管理——商品期货
	 *
	 */
	public static class CommodityFutureRiskJsp{
		/**
		 * 信息列表页
		 */
		public static String LIST_VIEW="/views/admin/commodityFuture/futureRisk/list";
		
		/**
		 * 分配账户页
		 */
		public static String ACCOUNT_VIEW="/views/admin/commodityFuture/futureRisk/account";
		
		/**
		 * 录入结果页
		 */
		public static String RESULT_VIEW="/views/admin/commodityFuture/futureRisk/result";
	}
	
	/**
	 * 资金管理——商品期货收益报表
	 *
	 */
	public static class CommodityFutureEarningJsp{
		/**
		 * 信息列表页
		 */
		public static String LIST_VIEW="/views/admin/commodityFuture/futureEarning/list";
	}
	
	/**
	 * 现货
	 * @ClassName SpotJsp
	 * @author L.Y
	 * @email liuyuan@peigubao.com
	 * @date 2015年10月9日
	 */
	public static class SpotJsp {
		/**
		 * 列表页面
		 */
		public static final String LIST_VIEW = "/views/admin/spot/list";
		
		/**
		 * 编辑数据
		 */
		public static  final String EDIT_VIEW="/views/admin/spot/edit";
	}
	
		
	/**
	 * 恒生指数
	 *
	 */
	public static class HSIJsp{
		/**
		 * 参数配置
		 */
		public static String PARAMETER_LIST_VIEW="/views/admin/shi/parameterList";
		/**
		 * 方案管理
		 */
		public static String TRADE_LIST_VIEW="/views/admin/shi/tradeList";
		/**
		 * 收益报表
		 */
		public static String PROFIT_LIST_VIEW="/views/admin/shi/profitList";
	}
	
	/**
	 * 国际原油
	 *
	 */
	public static class CrudeJsp{
		/**
		 * 参数配置
		 */
		public static String PARAMETER_LIST_VIEW="/views/admin/crude/parameterList";
		/**
		 * 方案管理
		 */
		public static String TRADE_LIST_VIEW="/views/admin/crude/tradeList";
		/**
		 * 收益报表
		 */
		public static String PROFIT_LIST_VIEW="/views/admin/crude/profitList";
	}
	
	/**
	 * 主力合约
	 * @author LiuYang
	 *
	 * 2015年10月20日 上午11:06:34
	 */
	public static class ContractParitiesJsp{
		/**
		 * 参数配置
		 */
		public static String LIST_VIEW="/views/admin/contractParities/list";
	}

	/**
	 * 首页配置信息
	 * @author WangPinQun
	 *
	 */
	public static class HomepageCinfigJsp{
		
		/**
		 * 首页配置信息列表
		 */
		public static  final String HOME_PAGE_CINFIG_LIST_VIEW="/views/admin/homepagecinfig/list";
		
		
		/**
		 * 编辑首页配置信息
		 */
		public static  final String HOME_PAGE_CINFIG_EDIT_VIEW="/views/admin/homepagecinfig/edit";
	}
	
		
	/**
	 * 国际期货页面处理
	 * @zhouchen
	 * 2015年11月20日
	 */
	public static class InternationFutureViewJsp{
		
		public static String LIST_VIEW="/views/admin/internationFuture/list";
	}

	public static class futureAccount{
		public static String LIST_VIEW = "/views/admin/futureAccount/list";
	}

	/**
	 * 期货合买
	 */
	public static class togetherFuture{
		public static String LIST_VIEW = "/views/admin/togetherFuture/list";
		public static String TRADE_LIST = "/views/admin/fTogetherTrade/list";
		public static String RECORD_LIST = "/views/admin/fTogetherTrade/recordList";
	}

	/**
	 * 支付银行管理页面处理
	 */
	public static class paymentSupportBank{
		public static String LIST_VIEW="/views/admin/paymentSupportBank/list";
	}
	
	/**
	 * 网银通道设置
	 */
	public static class bankChannel{
		public static String LIST_VIEW="/views/admin/bankChannelSet/list";
	}
	/**
	 * 开箱壕礼活动
	 */
	public static class Activity{
		public static String LIST_VIEW="/views/admin/activity/list";
	}
	public static class ComperhensiveCommodity{
		public static String SETTING_PARAMS="/views/admin/comprehensiveCommodity/settingParam";
	}
	/**
	 * 国际综合
	 */
	public static class OutDisk{
		/**
		 * 参数设置
		 */
		public static String LIST_PA_VIEW="/views/admin/internationFuture/OutPara";
	}
	
	/**
	 * 期货优惠券
	 */
	public static class SimpleCoupon{
		/**
		 * 优惠券
		 */
		public static String LIST_VIEW="/views/admin/Future/list";
	}

	public static class channelPromotion{
		public static String LIST_VIEW="/views/admin/channelPromotion/list";
	}
	
	
	public static class MonthTrade{
		public static String LIST_VIEW="/views/admin/monthTradeParams/list";
	}
	public static class CrawlerView{
		public static String LIST_VIEW = "/views/admin/crawler/list";
	}
	public static class SocketConfig{
		public static String LIST_LIVE = "/views/admin/socketconfig/list";
	}
}
