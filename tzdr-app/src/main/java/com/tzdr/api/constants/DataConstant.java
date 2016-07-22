package com.tzdr.api.constants;

import java.util.concurrent.ConcurrentHashMap;

import com.tzdr.api.support.CacheUser;

/**
 * <B>说明: </B>数据显示 常量 
 * @zhouchen
 * 2015年5月21日 下午1:11:08
 */
public interface DataConstant {

	/**
	 * token 唯一标志值
	 */
	public static final String APP_TOKEN="token";
	
	/**
	 * 加密密钥
	 */
	public static final String SECRET_KEY="secret";
	
	/**
	 * 请求来源
	 */
	public static final String SOURCE="source";
	
	/**
	 * 请求来源web
	 */
	public static final String SOURCE_WAP="wap";
	
	
	/**
	 * wap 配股宝 请求来源
	 */
	public static final String SOURCE_PEIGUBAO="peigubao";
	
	
	/**
	 * 请求来源android
	 */
	public static final String SOURCE_ANDROiD="android";
	
	/**
	 * 请求来源ios
	 */
	public static final String SOURCE_IOS="ios";
	
	/**
	 * UUID 分割符
	 */
	public static final String UUID_SPLIT="-";
	
	/**
	 * 替换uuid 分隔符
	 */
	public static final String REPLACE_UUID_SPLIT="";
	
	
	/**
	 * 注册验证码短信
	 */
	public static final int  SEND_SMS_TYPE_REGIST=1;
	/**
	 * 忘记密码短信
	 */
	public static final int  SEND_SMS_TYPE_FORGET_PWD=2;
	
	/**
	 * 修改绑定手机短信 旧手机号短信
	 */
	public static final int  SEND_SMS_TYPE_UPDATE_PHONE=1;
	
	
	/**
	 * 忘记提现密码短信
	 */
	public static final int  SEND_SMS_TYPE_FORGET_WITHDRAW_PWD=2;
	
	
	/**
	 * 修改绑定手机短信 新手机号验证
	 */
	public static final int  SEND_SMS_TYPE_UPDATE_PHONE_NEW=3;
	/**
	 * 注册验证码短信
	 */
	public static final String  SEND_SMS_TYPE_REGIST_MODULE="注册";
	/**
	 * 忘记密码短信
	 */
	public static final String  SEND_SMS_TYPE_FORGET_PWD_MODULE="忘记密码";
	
	/**
	 * 修改绑定手机短信
	 */
	public static final String  SEND_SMS_TYPE_UPDATE_PHONE_MODULE="安全信息-修改绑定手机";
	
	
	/**
	 * 忘记提现密码短信
	 */
	public static final String  SEND_SMS_TYPE_FORGET_WITHDRAW_PWD_MODULE="忘记提现密码";
	
	
	/**
	 * 忘记密码验证码验证
	 */
	public static final int  VALIDATE_CODE_TYPE_FORGET_PWD=1;
	/**
	 * 修改绑定手机验证码验证
	 */
	public static final int  VALIDATE_CODE_TYPE_UPDATE_PHONE=2;
	
	/**
	 * 忘记提现密码验证码验证
	 */
	public static final int  VALIDATE_CODE_TYPE_FORGET_WITHDRAW_PWD=3;
	
	/**
	 * 验证码失效时间
	 */
	public static final int VALIDATE_CODE_INVALID_TIME=5*60;
	
	
	/**
	 * 平台web默认用户类型
	 */
	public static final String TZDR_DEFAULT_USERTYPE = "-2";
	
	
	
	/**
	 * 渠道
	 */
	public static final String TZDR_CHANNEL = "channel";
	
	
	/**
	 * 推广uid
	 */
	public static final String TZDR_GENERALIZEUID = "u_generalizeUid";
	
	
	/**
	 * 身份认证最多3次
	 */
	public static final int VALIDATE_CARD_MAX_TIME=3;
	
	
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
	
	/**
	 * 逗号分隔符
	 */
	public static final String SPLIT_SIGN=",";
	
	/**
	 * 配资 最小倍数
	 */
	public static final int LEVER_MIN=1;
	/**
	 * 配资 最大倍数
	 */
	public static final int LEVER_MAX=5;
	
	/**
	 * 配资 最大倍数 6 
	 */
	public static final int LEVER_MAX_SEX=6;
	
	/**
	 * 配资 最小使用天数
	 */
	public static final int BORROWPERIOD_MIN=2;
	/**
	 * 配资最多使用天数
	 */
	public static final int BORROWPERIOD_MAX=180;
	
	/**
	 * 配资保证金最小250
	 */
	public static final int CAPITALMARGIN_MIN=300;	
	
	/**
	 * 配资保证金最小300  为区别配股宝和达人平台网站 最低保证金不同
	 */
	public static final double PLATFORM_CAPITALMARGIN_MIN=300;	
	
	/**
	 * 当前月明细
	 */
	public static final int FUND_DETAIL_MONTH_TYPE=0;
	
	/**
	 * 所有明细
	 */
	public static final int FUND_DETAIL_ALL_TYPE=1;	
	
	/**
	 * 支付最小金额
	 */
	public static final int ALIPAY_MIN_MONEY=1;	
	
	/**
	 * 支付未处理
	 */
	public static final int PAY_NO_PROCESSING = 0;
	
	
	/**
	 * 充值成功paystatus
	 */
	public final static int RECHARGE_LIST_PAYS_STATUS_SUCCESS = 21;
	
	/**
	 * 支付宝充值类型
	 */
	public static final String ALIPAY_TYPE = "3";// 支付宝
	

	/**
	 * 银行转账
	 */
	public static final String TRANSBANK_TYPE = "4";
	
	/**
	 * 支付宝充值
	 */
	public static final String ALIPAY = "alipay";// 支付宝
	
	/**
	 * 是否开启当前时段配资最大金额
	 */
	public static final String IS_OPEN_MAXLEVERMONEY = "1";
	
	
	/**
	 * 处理配资
	 */
	public static final int  TYPE_HANDLE = 1;
	
	/**
	 *  确认配资
	 */
	public static final int  TYPE_CONFIRM = 2;
	
	/**
	 * yyyyMMdd 日期长度
	 */
	public static final int CHINESE_DATE_FORMAT_LONG_LENGTH=8;
	
	/**
	 * 方案操作中状态
	 */
	public static final int TRADE_STATUS_HANDLING=1;
	
	/**
	 * 金额为 0 
	 */
	public static final double ZERO_MONEY=0;
	
	
	/**
	 * 数据中星号标记
	 */
	public static final String ASTERISK_FLAG="*";
	
	
	/**
	 * 常量 int 0
	 */
	public static final int  ZERO=0;
	
	/**
	 * 常量 int 1
	 */
	public static final int  ONE=1;
	
	/**
	 * 数据中百分号标记
	 */
	public static final String SIGN_FLAG="%";
	
	/**
	 * 提现次数限制5次
	 */
	public static final int  WITHDRAW_TIME=5;
	
	
	/**
	 * 最小提现金额10元
	 */
	public static final int  MIN_WITHDRAW_MONEY=10;
	
	
	/**
	 * 打开自动提现，即可可以自动调用接口 进行提现无需审核
	 */
	public static final String  OPEN_AUTO_WITHDRAW="1";
	
	/**
	 * 提现订单号长度
	 */
	public static final int WITHDRAW_ORDERID_LENGTH=20;
	
	
	/**
	 * 调用取款接口申请返回的成功标志
	 */
	public static final String WITHDRAW_SUCCESS = "0000";
	
	/**
	 * 提现 接口 检查标志
	 */
	public static final String WITHDRAW_EXTENDINFO_CHECK_FLAG="1";
	
	
	/**
	 * 短信次数限制
	 */
	public static ConcurrentHashMap<String,Integer> SMS_LIMIT_MAPS =  new ConcurrentHashMap<String, Integer>();
	
	/**
	 * 短信次数限制
	 */
	public static int  SMS_LIMIT_NUMBER= 5;
	
	
	/**
	 * 短信发送 时间间隔限制 60秒
	 */
	public static int  SMS_SEND_INTERVAL_TIME= 60;
	
	/**
	 * 支持币币  支持联动
	 */
	public static final int  PAYMENT_SUPPORT = 1;
	
	/**
	 * 缓存系统登录过的用户
	 */
	public static ConcurrentHashMap<String,CacheUser> CACHE_USER_MAP =  new ConcurrentHashMap<String,CacheUser>();
	
	/**
	 * banner 类型
	 */
	public static final int BANNER_TYPE = 9;
	
	/**
	 * 汇率类型
	 */
	public static final int PARITIESTYPE = 1;
	
	/**
	 * 业务类型【追加保证金】
	 */
	public static final int BUSINESSTYPE_ADDBOND = 1;
	
	/**
	 * 业务类型【申请终结】
	 */
	public static final int BUSINESSTYPE_ENDTRADE = 2;

	
	/**
	 * 业务类型【申请终结】
	 */
	public static final int BUSINESSTYPE_E_BANK_RECHARGE = 3;
	
	/**
	 * 业务类型【申请终结-折扣劵类型】
	 */
	public static final int BUSINESSTYPE_ENDTRADE_COUPONS = 3;
	
	/**
	 * 业务类型【申请操盘-代金劵类型】
	 */
	public static final int BUSINESSTYPE_APPLY_COUPONS = 2;
	
	/**
	 * 期货方案终结状态
	 */
	public static final int FTRADE_IS_OVER = 6;
	
	/**
	 * 期货默认最小追加保证金
	 */
	public static final double FTRADE_DEFAULT_MIN_ADD_BOND = 2000.00;
	
	/**
	 * 优惠劵类型【折扣劵】
	 */
	public static final int COUPON_TYPE_3 = 3;
	
	/**
	 * 方案状态【申请结算】
	 */
	public static final int FTRADE_STATE_TYPE_2 = 2;
	
	/**
	 * 方案状态【操盘中】
	 */
	public static final int FTRADE_STATE_TYPE_4 = 4;
	
	/**
	 * 优惠劵类型【现金红包】
	 */
	public static final int COUPON_TYPE_1 = 1; 
	
	/**
	 * 优惠劵使用情况【已发放】
	 */
	public static final int COUPON_STATUS_2 = 2; 
	
	/**
	 * 优惠劵使用情况【已使用】
	 */
	public static final int COUPON_STATUS_3 = 3; 
	
	
	/**
	 * 未配置时默认提现手续费
	 */
	public static final Double DEFAULT_HANDLE_FEE = 5.0; 
	
	/**
	 * 超过5000的联动优势提现手续费设置
	 */
	public static final String MAX_5000_HANDLE_FEE_CONFIG = "5000"; 
	
	
	/**
	 * 业务类型【提现配置查询】
	 */
	public static final int BUSINESSTYPE_WITHDRAW = 4;
	
	

	/**
	 * 业务类型【期货合买活动】
	 */
	public static final int BUSINESSTYPE_FTOGETHER_ACTIVITY= 5;
	
	/**
	* @Title: DataConstant.java     
	* @Description:  支付状态   
	* @author： WangPinQun 
	* @E-mail：wangpinqun@tzdr.com
	* @company： 上海信闳投资管理有限公司重庆分公司
	* @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
	* @date： 2016年3月28日 下午4:18:20    
	* @version： V1.0
	 */
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
	
	/**
	 * 银行选择页面
	 */
	public static final String BBPAY_PNC = "11002";
	
	/**
	 * app支付成功，商户前端返回地址
	 */
	public static final String APP_SRETURL ="sreturl_app";
}
