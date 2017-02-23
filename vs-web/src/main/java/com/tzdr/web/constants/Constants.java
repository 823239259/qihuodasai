package com.tzdr.web.constants;

/**
 * @author zhouchen
 * @version 创建时间：2014年12月5日 下午4:24:53 类说明
 */
public class Constants {

	/**
	 * 字符串间隔符;
	 */
	public static final String SEPERATOR_SEMICOLON  = ";";  
	
	/**
	 * 是否ajax请求
	 */
	public static final String IS_AJAX = "1";

	/**
	 * 常量
	 */
	public static final String AJAX = "ajax";
	
	/**
	 * 渠道(URL参数key)
	 */
	public static final String CHANNEL_KEY = "p";
	
	/**
	 * 推广码key(URL参数key)
	 */
	public static final String GENERALIZE_UID_KEY = "uid";

	/**
	 * session 失效
	 */
	public static final String LOGIN_SESSION = "loginSession";

	/**
	 * 保存投资达人session
	 */
	public static final String TZDR_USER_SESSION = "tzdrUser";

	/**
	 * 登录失败最大允许次数，超过需要验证码验证
	 */
	public static final int LOGIN_FAIL_MAX_COUNT_SESSION = 3;
	
	/**
	 * 注册单个手机号码下发短信最大允许次数，超过需要验证码验证
	 */
	public static final int SIGNIN_SEND_CODE_MAX_COUNT_SESSION = 4;
	
	/**
	 * 取现允许最多错误次数
	 */
	public static final int DRAW_FAIL_MAX_COUNT_SESSION = 5;

	/**
	 * 登录失败最大允许次数，超过需要验证码验证 session key
	 */
	public static final String LOGIN_FAIL_MAX_COUNT_SESSION_KEY = "loginFailCount";

	/**
	 * 取现失败最大允许次数，
	 */
	public static final String DRAW_FAIL_MAX_COUNT_SESSION_KEY = "drawFailCount";

	/**
	 * 注册发送短信验证码最大允许次数，超过需要验证码验证 session key
	 */
	public static final String SIGNIN_SEND_CODE_MAX_COUNT_SESSION_KEY = "signinSendCodeMaxCount";
	
	/**
	 * 允许每次下方短信最大时间间隔60秒
	 */
	public static final int SEND_SMS_MAX_TIME = 60;
	
	/**
	 * 平台web默认用户类型
	 */
	public static final String TZDR_DEFAULT_USERTYPE = "-2";

	/**
	 * 保存投资达人用户名称session
	 */
	public static final String TZDR_USERNAME_SESSION = "userName";
	
	/**
	 * 保存用户真实姓名
	 */
	public static final String TZDR_REALNAME_SESSION = "realName";

	/**
	 * 首次访问页面
	 */
	public static final String FIRSTURL_SESSION = "firstUrl";
	
	/**
	 * 推广uid
	 */
	public static final String TZDR_GENERALIZEUID = "u_generalizeUid";
	
	/**
	 * 渠道
	 */
	public static final String TZDR_CHANNEL = "channel";
	
	/**
	 * 渠道来源
	 */
	public static final String TZDR_CHANNEL_FROM = "channel_from";

	/**
	 * 设置推广uid保存时间
	 */
	public static final int USERID_COOKIEMAX_VALUE = 60 * 60 * 24 * 365;

	/**
	 * 错误码 常量
	 */
	public static final String ERROR_CODE = "errorCode";

	/**
	 * 错误信息
	 */
	public static final String ERROR_MESSAGE = "errorMessage";


	public static class UserFundStatus {
		public static final int TICKET = 24;
	}
	// 支付状态
	public static class PayStatus {
		/**
		 * 未处理
		 */
		public static final int NO_PROCESSING = 0;
		/**
		 * 成功
		 */
		public static final int SUCCESS = 21;
	}

	// 支付类型
	public static class PayType {
		/**
		 * 快捷支付
		 */
		public static final String QUICK = "1";
		/**
		 * 网银：现在连的国付宝
		 */
		public static final String NET_BANK = "2";// 网银
		/**
		 * 支付宝
		 */
		public static final String ALIPAY = "3";// 支付宝
		/**
		 * 银行转账
		 */
		public static final String TRANSBANK = "4";// 银行转账
		/**
		 * 国付宝
		 */
		public static final String GO_WAY = "5";//国付宝
		/**
		 * 微信转账
		 */
		public static final String WECHAT_TYPE = "9";//微信转账
	}

	// 身份证状态
	public static class Idcard {
		/**
		 * 未认证
		 */
		public static final short NOAUTH = 5;
		/**
		 * 照片上传未完成
		 */
		public static final short NOCOMPLETE = 3;
		/**
		 * 未通过
		 */
		public static final short NOPASS = 4;
		
		/**
		 * 待审
		 */
		public static final short PENDING = 0;
		/**
		 * 照片未认证通过
		 */
		public static final short NOAUTHPASS = 1;
		/**
		 * 照片认证通过
		 */
		public static final short AUTHPASS = 2;
		
		/**
		 * 身份证验证通过后并上传照片
		 */
		public static final short UPLOADSTATUS=6;
	}

	// 提现
	public static class Draw {
		/**
		 * 调用取款接口申请返回的成功标志
		 */
		public static final String SUCCESS = "0000";
		/**
		 * 配资用户
		 */
		public static final String USER_TYPE_DOWMENT = "matchEndowment";

	}



	//主页常量
	public static class Mainpage{
		/**
		 * 新闻
		 */
		public static final int  NEWS_STATUS=3;
		/**
		 * 新闻栏目
		 */
		public static final int  NEWSCOLUMN_STATUS=2;
		/**
		 * banner
		 */
		public static final int  BANNER_STATUS=4;
		/**
		 * 友情链接
		 */
		public static final int  LINKS_STATUS=1;
		
		/**
		 * 背景
		 */
		public static final int  BACKGROUND_STATUS=6;
		/**
		 * 合作伙伴
		 */
		public static final int  PARTNER_STATUS=5;
		
		/**
		 * 资金托管银行
		 */
		public static final int  BANK_STATUS=7;
		/**
		 * 设置banner标志
		 */
		public static final int  VALUE_TYPE=1;
		
		/**
		 * 设置banner标志大图
		 */
		public static final int  BIGVALUE_TYPE=2;
		
		public static final String HOME_COOKIE="homecookie";
	}
	
	/**
	 * 公告常量
	 * @ClassName Notice
	 * @author L.Y
	 * @email liuyuan@peigubao.com
	 * @date 2015年4月30日
	 */
	public static class Notice {
		//启用
		public static final Integer UNLOCK = 2;
		
		//停用
		public static final Integer LOCK = 1;
	}
	
	//抵扣劵
	public static class VolumeType {
		//未用
		public static final Integer NOUSE = -1;
		
	}
	
		//配资类型
		public static class UserTradeType {
			//未用
			public static final String tradetype ="bespokeTrade";
			
			//追加配资方案
			public static final String ADD_TRADE_TYPE ="addTrade";
			
		}
		
	
	/**
	 * 上证指数
	 * @ClassName StockIndex
	 * @author wangpinqun
	 * @email wangpinqun@tzdr.com
	 * @date 2015年5月25日
	 */
	public static class StockIndex{
		
		/**
		 * 深圳成指
		 */
		public static final String S_SZ="s_sz399001";
		
		/**
		 * 上证指数
		 */
		public static final String S_SH="s_sh000001";
		
		/**
		 * 创业板
		 */
		public static final String S_SZ_GEM="s_sz399006";
		
		/**
		 * 中小板
		 */
		public static final String S_SZ_SMALL="s_sz399005";
	} 
	
	/**
	 * 支持币币  支持联动
	 */
	public static final int  PAYMENT_SUPPORT = 1;
	
	/**
	 * 交易常量：A股操盘
	 */
	public static class UserTrade {
		/**
		 * A股追加保证金最小金额
		 */
		public static final int TRADE_A_MIN_MARGIN = 1000;
		
		/**
		 * A股追加保证金最大金额
		 */
		public static final int TRADE_A_MAX_MARGIN = 1000000;
	}
}
