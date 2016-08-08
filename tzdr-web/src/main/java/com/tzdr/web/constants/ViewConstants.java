package com.tzdr.web.constants;


/**
 * 页面跳转路径
 * 通用的直接写常量   带有模块性质的  写到内部类相应的模块下面
 * @author zhouchen
 * @version 创建时间：2014年12月5日 下午4:24:53 类说明
 */
public class ViewConstants {
	
	/**
	 * 404页面
	 */
	public static final String NOT_FOUND_VIEW = "/404";

	/**
	 * 异常页面
	 */
	public static final String ERROR_VIEW = "/error";

	/**
	 * 登录页面
	 */
	public static final String ADMIN_LOGIN_VIEW = "/views/admin/login";
	
	/**
	 * test 主页面
	 */
	public static final String ADMIN_MAIN_VIEW = "/views/admin/main";
	
	/**
	 * 登录页面
	 */
	public static final String LOGIN_VIEW = "/login";
	
	/**
	* @Description: TODO(推广挣钱)
	* @ClassName: GeneralizeViewJsp
	* @author wangpinqun
	* @date 2015年1月12日 上午11:20:03
	 */
	public static class GeneralizeViewJsp{
		/**
		 * 推广详情
		 */
		public static final String DETAILS_VIEW = "/views/generalize/details";
	}
	
	/**
	* @Description: TODO(注册页面位置)
	* @ClassName: SignInViewJsp
	* @author wangpinqun
	* @date 2014年12月27日 下午3:12:58
	 */
	public static class SignInViewJsp{
		/**
		 * 注册页面
		 */
		public static final String SIGNIN_VIEW = "/views/signin/signIn";
		/**
		 * 上线推广注册页面
		 */
		public static final String EXTENDSIONSIGN_VEIW = "/views/signin/signinExtensions";
		/**
		 * 上线推广抽奖页面
		 */
		public static final String EXTENDSION_LUCK_VEIW = "/views/activies/skLuckyDraw";
		/**
		 * 上线推广注册成功页面
		 */
		public static final String EXTENDSION_SUCCESS_FUL_VEIW= "/views/signin/activitySucful";
		/**
		 * 跳转到推广码页面
		 */
		public static final String TXTENDSION_INVATION_CODE = "/veiws/invitationCode/invitationCode";
		/**
		 * 注册成功页面
		 */
		public static final String SIGNINSUCESS_VIEW = "/views/signin/signInSucess";
		/**
		 * 注册协议
		 */
		public static final String SIGNINWEBSITEAGREEMENT = "/views/signin/websiteAgreement";
		
		/**
		 * 快速注册页面
		 */
		public static final String FASTSIGNIN_VIEW = "/views/signin/fastSignIn";

		/**
		 * 周年庆快速注册
		 */
		public static final String ANNIVERSARY_SIGH_IN="/views/signin/anniversary";

		/**
		 * 商品综合推广注册
		 */
		public static final String CURRENCY_STOCK_SIGN_IN="/views/signin/currencyStockSignIn";
		/**
		 * 港股推广注册
		 */
		public static final String HKSTOCK_SIGN_IN="/views/signin/hkStockSignIn";
		/**
		 * 快速注册页面
		 */
		public static final String BAZAAR_FASTSIGNIN_VIEW = "/views/signin/bazaarFastSignIn";
		
		/**
		 * 快速注册页面2
		 */
		public static final String NEW_FASTSIGNIN_VIEW = "/views/signin/newFastSignIn";
		
		/**
		 * 快速注册页面2
		 */
		public static final String STOCKSIGNIN_VIEW = "/views/signin/stocksignin";
	}
	
	/**
	 * @Description: TODO(忘记密码页面位置)
	 * @ClassName: ForgetWiewJsp
	 * @author wangpinqun
	 * @date 2014年12月24日 上午9:31:45
	 */
	public static class ForgetViewJsp{
		/**
		 * 忘记密码第一个页面
		 */
		public static final String FORGETONE_VIEW = "/views/forget/forgetOne";

		/**
		 * 忘记密码第二个页面
		 */
		public static final String FORGETTWO_VIEW = "/views/forget/forgetTwo";
		
		/**
		 * 忘记密码第三个页面
		 */
		public static final String FORGETTHREE_VIEW = "/views/forget/forgetThree";
		
		/**
		 * 忘记密码投资达人页面
		 */
		public static final String FORGET_TZDR_VIEW="/views/forget/resetpassword";
		
	}
	
	/**
	* @Description: TODO(用户在线留言页面位置)
	* @ClassName: MessageViewJsp
	* @author wangpinqun
	* @date 2014年12月30日 下午3:55:16
	 */
	public static class MessageViewJsp{
		/**
		 * 在线留言页面
		 */
		public static final String MESSAGE_INDEX_VIEW = "/views/message/message";
	}
	
	/**
	* @Description: TODO(用户个人信息页面位置)
	* @ClassName: UserInfoViewJsp
	* @author wangpinqun
	* @date 2014年12月30日 下午1:28:53
	 */
	public static class UserInfoViewJsp{
		
		/**
		 * 个人信息页面
		 */
		public static final String USERINFO_INDEX_VIEW = "/views/userInfo/userInfo";
		
		/**
		 * 修改个人信息页面
		 */
		public static final String USERINFO_UPDATE_VIEW = "/views/userInfo/upUserInfo";
	}
	
	/**
	 * 账户相关的jsp 页面
	 * @zhouchen
	 * 2014年12月12日
	 */
	public static class AccountViewJsp {
		/**
		 * 个人中心首页
		 */
		public static final String ACCOUNT_INDEX_VIEW = "/views/account/account";
	}
	
	/**
	 * 配资相关页面
	 * @zhouchen
	 * 2014年12月12日
	 */
	public static class MatchFundsViewJsp{
		
	}

	//安全信息页面
	public static class SecurityViewJsp{
		public static final String SECURITY_MAIN_VIEW = "/views/securityInfo/secInfo";
		//到修改密码
		public static final String TO_UPDATE_PWD = "/views/securityInfo/toUpdatePwd";
		
		public static final String FORGET_DRAW_PWD = "/views/securityInfo/fogetdrawPwd";
		
	}
	
	   //安全支付页面
	public static class PayViewJsp{
		//到支付页面
		public static final String PAY_MAIN_VIEW = "/views/pay/payInfo";
		
		//到页面支付
		public static final String NET_PAY_MAIN_VIEW = "/views/pay/netbankpayInfo";
		
		//到币币页面支付
		public static final String BIBI_PAY_MAIN_VIEW = "/views/pay/bbPay";
				
	}
	
	/**
	 * 
	 * 
	 * <p></p>
	 * @author QingLiu
	 * @see 
	 * FSimpleUserTradeViewJsp
	 * @version 2.0
	 * 2015年2月5日下午7:33:13
	 */
	public static class FSimpleUserTradeViewJsp {
		/**
		 * 首页
		 */
		public static final String FUTRUE_INDEX = "/views/futrue/futrue_index";
		
		/**
		 * 天天乐首页
		 */
		public static final String FUTRUE_DAY_INDEX = "/views/futrue/futrue_day_index";
		
		/**
		 * 支付页
		 */
		public static final String FUTRUE_PAY = "/views/futrue/futrue_pay";
		
		/**
		 * 天天乐支付页
		 */
		public static final String FUTRUE_DAY_PAY = "/views/futrue/futrue_day_pay";
		
		/**
		 * 支付成功
		 */
		public static final String FUTRUE_PAY_SUCCESSFUL = "/views/futrue/futrue_pay_successful";
		
		/**
		 * 股指期货列表
		 */
		public static final String FUTRUE_TRADE_LIST = "/views/futrue/futrue_trade_list";
	}
	/**
	 * 商品期货页面
	 * @author LiuYang
	 *
	 * 2015年9月17日 下午6:51:59
	 */
	public static class FSimpleProductUserTradeViewJsp{
		
		/**
		 * 首页/商品期货综合
		 */
		public static final String INDEX = "/views/product/index";
		/**
		 *商品期货综合支付页
		 */
		public static final String PAY = "/views/product/pay";
		/**
		 * 沪金
		 */
		public static final String GOLD_INDEX = "/views/product/gold_index";
		/**
		 * 沪金支付页
		 */
		public static final String GOLD_PAY = "/views/product/gold_pay";
		/**
		 * 沪银
		 */                        
		public static final String SLIVER_INDEX = "/views/product/sliver_index";
		/**
		 * 沪银支付页
		 */
		public static final String SLIVER_PAY = "/views/product/sliver_pay";
		/**
		 * 沪铜
		 */                        
		public static final String COPPER_INDEX = "/views/product/copper_index";
		/**
		 * 沪铜支付页
		 */
		public static final String COPPER_PAY = "/views/product/copper_pay";
		/**
		 * 橡胶
		 */                        
		public static final String RUBBER_INDEX = "/views/product/rubber_index";
		/**
		 * 橡胶支付页
		 */
		public static final String RUBBER_PAY = "/views/product/rubber_pay";
		
		/**
		 * 支付成功页
		 */
		public static final String PAY_SUCCESSFUL = "/views/product/pay_successful";
		/**
		 * 方案列表页
		 */
		public static final String TRADE_LIST="/views/product/trade_list";
	}
	   //配资页面
		public static class UserTradeViewJsp{
			//配资主页面
			public static final String PER_BY_DAY = "/views/trade/perByDay";
			//其它配资页面
			public static final String OTHER_PER_BY_DAY = "/views/trade/otherPerByDay";
			//配资页面
			public static final String TRADE = "/views/trade/trade";				
			//配资成功页面
			public static final String TRADE_OK = "/views/trade/tradeOk";
			//配资列表
			public static final String LIST = "/views/trade/tradeList";
			//配资详细
			public static final String DETAIL = "/views/trade/tradeDetail";
			//追加方案
			public static final String MORE = "/views/trade/tradeAddMore";
			/**
			 * 配资合同
			 */
			public static final String TRADECONTRACT="/views/trade/tradeContract";
			
			/**
			 * 追加配资
			 */
			public static final String MORETRADE="/views/trade/tradeMore";
			/**
			 * 配资失败页面
			 */
			public static final String TRADE_FAIL="/views/trade/tradeFail";
			/**
			 * 8800页面
			 */
			public static final String TRADE_ACTIVITY="/views/trade/tradeActivity";
			/**
			 * 配资8800页面分享HIGH
			 */
			public static final String SHARE_HIGH="/views/wx/activityShareHigh";
			/**
			 * 配资8800页面分享LOW
			 */
			public static final String SHARE_LOW="/views/wx/activityShareLow";
			/**
			 * 定制配资详情页面
			 */
			public static final String BESPOKETRADE="/views/trade/bespaketrade";
			
		}
	
	   //取现页面
	public static class DrawMoney{
		//取款页面
		public static final String DRAW_MAIN_VIEW = "/views/draw/drawmoney";
		
	}
	
	
	/**
	 * 充值明细常量
	 * @author zhangjun
	 *
	 */
	public static class FundDetail{
		
		public static final String FUND_DETAIL="/views/fund/fundDetail";
	}
	
   	// 首页
	public static class HomePageViewJsp{
		//到首页
		public static final String 	HOME_PAGE = "/views/homepage/homepage";
		public static final String 	ABORT_PAGE = "/views/homepage/abort";
		public static final String 	GENERALIZE_PAGE = "/views/homepage/homegeneralize";
		
		public static final String 	COMPANY_PAGE = "/views/homepage/company";
		public static final String 	PARTNER_PAGE = "/views/homepage/partner";
		public static final String 	CONTACT_PAGE = "/views/homepage/contact";
		public static final String 	NET_MAP = "/views/homepage/netmap";
		/**
		 * 公司展示
		 */
		public static final String 	COMPANYPIC_PAGE = "/views/homepage/companypic";
		/**
		 * 注册送
		 */
		public static final String REG_SEND = "/views/homepage/regsend";
	}
	
	/**
	* @Description: TODO(帮助中心信息页面位置)
	* @ClassName: HelpViewJsp
	* @author wangpinqun
	* @date 2015年1月29日 上午9:20:04
	 */
	public static class HelpViewJsp{
		/**
		 * 帮助中心
		 */
		public static final String HELP_INDEX_VIEW = "/views/help/help";
	}

	/**
	 * 新闻页面
	 * @author Administrator
	 *
	 */
	public static class NewsViewJsp{
		
		public static final String NEWS_VIEW = "/views/news/news";
		
		public static final String NEWS_PAGE = "/views/news/newspage";
	}
	/**
	 * 问卷调查
	 * @author Administrator
	 *
	 */
    public static class QuestionnaireJsp{
		
		public static final String TO_QUESTION = "/views/questionnaire/questionnaire";
		
		public static final String TO_TEL = "/views/questionnaire/telphone";
		
		public static final String TO_SUC = "/views/questionnaire/success";
		
		public static final String TO_FAIL= "/views/questionnaire/fail";
	}
    
    /**
     * 活动页面
     * @author Administrator
     *
     */
    public static class ActiviesJsp{
		
 		public static final String ACTIVIES = "/views/activies/activies";
 		public static final String ACTIVIES_DETAIL = "/views/activies/activiesDetail";
 		
 		public static final String WEIXIN_LOTTERY_LOGIN = "/views/activies/weixinLottery/login";
 		public static final String WEIXIN_LOTTERY_INDEX = "/views/activies/weixinLottery/index";
 		public static final String WEIXIN_LOTTERY_PRIZE = "/views/activies/weixinLottery/prize";
 		public static final String WEIXIN_LOTTERY_RULE = "/views/activies/weixinLottery/rule";
 		
 		public static final String NEWYEAR_SPREE_INDEX = "/views/activies/newyearspree";
 		
 		public static final String ANNIVERSARY_SPREE_INDEX = "/views/activies/anniversary";
 	}
    
    /**
     * 劵
     * @author Administrator
     *
     */
    public static class UserVolumeJsp{
    	public static final String USERVOLUME = "/views/userVolume/userVolume";
    }
    
    public static class ContractsaveJsp{
    	public static final String VIEW_INFO = "/views/contractsave/viewInfo";
    }
    
    /**
     * 免息操盘
     * @author WangPinQun
     *
     */
    public static class FreeTradeJsp{
    	public static final String FREE_TRADE = "/views/trade/freeTrade";
    }
        
    /**
     * 富时A50
     * @author WangPinQun
     *
     */
    public static class FSimpleFtseUserTradeJsp{
    	/**
		 * 首页
		 */
		public static final String FTSE_INDEX = "/views/ftse/ftse_index";
		
		/**
		 * 支付
		 */
		public static final String FTSE_PAY = "/views/ftse/ftse_pay";
		

		/**
		 * 支付成功
		 */
		public static final String FTSE_PAY_SUCCESSFUL = "/views/ftse/ftse_pay_successful";
		
		/**
		 * 富A时50列表
		 */
		public static final String FTSE_TRADE_LIST = "/views/ftse/ftse_trade_list";
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
    	 * 现货预约页面
    	 */
    	public static final String SPOT_PAGE = "/views/spot/spot";
    	
    }
    
        
    /**
     * 国际原油
     * @author WangPinQun
     *
     */
    public static class FSimpleCrudeOilUserTradeJsp{
    	/**
		 * 首页
		 */
		public static final String CRUDE_OIL_INDEX = "/views/crudeoil/crudeoil_index";
		
		/**
		 * 支付
		 */
		public static final String CRUDE_OIL_PAY = "/views/crudeoil/crudeoil_pay";
		

		/**
		 * 支付成功
		 */
		public static final String CRUDE_OIL_PAY_SUCCESSFUL = "/views/crudeoil/crudeoil_pay_successful";
		
		/**
		 * 列表
		 */
		public static final String CRUDE_OIL_TRADE_LIST = "/views/crudeoil/crudeoil_trade_list";
    }
    
    /**
     * 恒生指数
     * @author WangPinQun
     *
     */
    public static class FSimpleHSIUserTradeJsp{
    	/**
		 * 首页
		 */
		public static final String HSI_INDEX = "/views/hsi/hsi_index";
		
		/**
		 * 支付
		 */
		public static final String HSI_PAY = "/views/hsi/hsi_pay";
		

		/**
		 * 支付成功
		 */
		public static final String HSI_PAY_SUCCESSFUL = "/views/hsi/hsi_pay_successful";
		
		/**
		 * 列表
		 */
		public static final String HSI_TRADE_LIST = "/views/hsi/hsi_trade_list";
    }
    
    /**
     * 富时A50活动
     * @author WangPinQun
     *
     */
    public static class FtseActiveJsp{
    	/**
		 * 首页
		 */
		public static final String INDEX = "/views/ftseActive/index";
		
		/**
		 * 支付
		 */
		public static final String PAY = "/views/ftseActive/pay";
		

		/**
		 * 支付成功
		 */
		public static final String PAY_SUCCESSFUL = "/views/ftseActive/pay_successful";
		
    }
    
    /**
     * hsi推广注册
     *
     */
    public static class HsiSpreadJsp{
    	/**
		 * 首页
		 */
		public static final String INDEX = "/views/hsispread/index";
		/**
		 * 成功
		 */
		public static final String SUCCESS = "/views/hsispread/success";
		
		
    }
    
    /**
     * 国际原油活动
     * @author WangPinQun
     *
     */
    public static class CrudeActiveJsp{
    	/**
		 * 首页
		 */
		public static final String INDEX = "/views/crudeActive/index";
		
		/**
		 * 支付
		 */
		public static final String PAY = "/views/crudeActive/pay";
		

		/**
		 * 支付成功
		 */
		public static final String PAY_SUCCESSFUL = "/views/crudeActive/pay_successful";
		
    }
    /**
     * 合买相关页面
     * @author LiuYang
     *
     * 2015年12月28日 下午2:16:07
     */
    public static class TogetherTradeJsp{
    	/**
		 * 首页
		 */
		public static final String INDEX = "/views/togetherTrade/index";
		
		/**
		 * 支付
		 */
		public static final String PAY = "/views/togetherTrade/pay";
		

		/**
		 * 支付成功
		 */
		public static final String PAY_SUCCESSFUL = "/views/togetherTrade/success";
		
		/**
		 * 合买列表
		 */
		public static final String LIST = "/views/togetherTrade/list";
		/**
		 * 方案详情
		 */
		public static final String DETAIL = "/views/togetherTrade/detail";
		/**
		 * 公共合买列表
		 */
		public static final String PUBLICLIST = "/views/togetherTrade/publicList";
		/**
		 * 公共方案详情
		 */
		public static final String PUBLICDETAIL = "/views/togetherTrade/publicDetail";
		
		
		
    }
    /**
     * 股指开箱壕礼活动
     * @author WangPinQun
     */
    public static class SIFActivitesJsp{
    	/**
		 * 开箱壕礼首页
		 */
		public static final String KXHL = "/views/activies/kxhl";
    }
    
    /**
     * 国际综合
     * @author Liuyang
     *
     */
    public static class OutDiskJsp{
    	/**
		 * 首页
		 */
		public static final String INDEX = "/views/outDisk/index";
		
		/**
		 * 支付
		 */
		public static final String PAY = "/views/outDisk/pay";
		

		/**
		 * 支付成功
		 */
		public static final String PAY_SUCCESSFUL = "/views/outDisk/pay_successful";
		
    }
    
    /**
    * @Title: ViewConstants.java     
    * @Description: 优惠劵  
    * @author： WangPinQun 
    * @E-mail：wangpinqun@tzdr.com
    * @company： 上海信闳投资管理有限公司重庆分公司
    * @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
    * @date： 2016年3月7日 下午5:18:08    
    * @version： V1.0
     */
    public static class  FSimpleCouponJsp{
    	/**
		 * 首页
		 */
		public static final String LIST = "/views/coupon/fsimple_coupon_list";
    }
    
}
