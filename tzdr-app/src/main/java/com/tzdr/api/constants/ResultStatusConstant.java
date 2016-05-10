package com.tzdr.api.constants;

/**
 * <B>说明: </B>接口状态返回
 * @zhouchen
 * 2015年5月21日 下午2:02:50
 */
public class ResultStatusConstant {
	
	/**
	 * 请求未填写来源
	 */
	public static final String NO_SOURCE="-2";
	
	/**
	 * 认证失败，参数错误或为空
	 */
	public static final String AUTH_PARAMS_ERROR="-1";
	
	
	/**
	 * 处理成功
	 */
	public static final String SUCCESS="1";
	
	
	/**
	 * 处理失败
	 */
	public static final String FAIL="2";
	
	/**
	 * 生成token
	 * @author zhouchen
	 * 2015年5月26日 上午10:46:41
	 */
	public class TokenCreate{
		public static final String AUTH_FAIL="3";
	}
	
	/**
	 * 发送短信验证码
	 * @author zhouchen
	 * 2015年5月26日 上午10:46:41
	 */
	public class SendSms{
		/**
		 * 手机号码已经存在
		 */
		public static final String MOBILE_EXIST="3";
		
		/**
		 * 手机号码不存在
		 */
		public static final String MOBILE_NOT_EXIST="4";
		
		/**
		 * 操作过于频繁，请稍候再试 一分钟内提交多次
		 */
		public static final String FREQUENT_OPERATION="5";
	}
	
	/**
	 * 短信验证码校验
	 * @author zhouchen
	 * 2015年5月26日 上午10:46:41
	 */
	public class ValidateCode{
		/**
		 * 验证码错误
		 */
		public static final String ERROR_CODE="3";
		
		/**
		 * 用户信息不存在
		 */
		public static final String USER_INFO_NOT_EXIST="4";
		
		/**
		 * 验证码失效
		 */
		public static final String INVALID_CODE="5";
	}
	
	
	/**
	 * 注册
	 * @author zhouchen
	 * 2015年5月26日 上午10:46:41
	 */
	public class Regist{
		/**
		 * 手机号码已经存在
		 */
		public static final String MOBILE_EXIST="3";
		
		/**
		 * 验证码错误或为空
		 */
		public static final String ERROR_CODE="4";
		
		/**
		 * 验证码失效
		 */
		public static final String INVALID_CODE="5";
		
		/**
		 * 推广码错误
		 */
		public static final String ERROR_GENERALIZE_CODE="6";
		
		/**
		 * 密码格式有误
		 */
		public static final String PASSWORD_PATTERN_ERROR="7";
	}
	
	
	/**
	 * 登录
	 * @author zhouchen
	 * 2015年5月26日 上午10:46:41
	 */
	public class Login{
		/**
		 *登录信息为空
		 */
		public static final String LOGIN_INFO_ERROR="3";
		/**
		 * 密码格式有误
		 */
		public static final String PASSWORD_PATTERN_ERROR="4";
	}
	
	
	/**
	 * 忘记密码 并修改
	 * @author zhouchen
	 * 2015年5月26日 上午10:46:41
	 */
	public class ForgetPwd{
		/**
		 *登录信息为空
		 */
		public static final String USER_INFO_NOT_EXIST="3";
		
		/**
		 * 验证码失效
		 */
		public static final String INVALID_CODE="4";
		
		/**
		 * 验证码错误或为空
		 */
		public static final String ERROR_CODE="5";
		
		/**
		 * 密码格式有误
		 */
		public static final String PASSWORD_PATTERN_ERROR="6";
	}
	
	
	/**
	 * 修改密码
	 * @author zhouchen
	 * 2015年5月26日 上午10:46:41
	 */
	public class UpdatePwd{
		/**
		 *用户信息不存在
		 */
		public static final String USER_INFO_NOT_EXIST="3";
		
		/**
		 * 原密码错误
		 */
		public static final String ERROR_OLD_PASSWORD="4";
		
		/**
		 * 与提现密码相同
		 */
		public static final String SAME_WITH_DRAWMONEY_PASSWORD="5";
		
		/**
		 * 密码格式有误
		 */
		public static final String PASSWORD_PATTERN_ERROR="6";
	}
	
	
	/**
	 * 身份认证
	 * @author zhouchen
	 * 2015年5月26日 上午10:46:41
	 */
	public class ValidateCard{
		/**
		 *用户信息不存在
		 */
		public static final String USER_INFO_NOT_EXIST="3";
		
		/**
		 * 身份证格式有误
		 */
		public static final String CARD_PATTERN_ERROR="4";
		
		/**
		 * 该身份证已经被认证过了
		 */
		public static final String CARD_EXIST="5";
		
		/**
		 * 已经认证不能重复认证
		 */
		public static final String ALREADY_AUTHENTICATION="6";
	}
	
	
	/**
	 * 获取账户信息
	 * @author zhouchen
	 * 2015年5月26日 上午10:46:41
	 */
	public class UserInfo{
		/**
		 *用户信息不存在
		 */
		public static final String USER_INFO_NOT_EXIST="3";
	}
	
	/**
	 * 配资方案列表
	 * @author zhouchen
	 * 2015年6月9日 下午2:50:25
	 */
	public class TradeList{
		/**
		 *用户信息不存在
		 */
		public static final String USER_INFO_NOT_EXIST="3";
	}
	
	/**
	 * 国际期货操盘支付确认
	 * @author zhouchen
	 * 2015年5月26日 上午10:46:41
	 */
	public class HandleFTrade{
		/**
		 *余额不足
		 */
		public static final String USER_INSUFFICIENT_BALANCE="3";
		
		/**
		 * 没有实名认证
		 */
		public static final String NOT_AUTH_REALNAME="4";
		
		/**
		 * 倍数只能是1-15倍
		 */
		public static final String LEVER_ERROR="5";
		
		
		/**
		 * 保证金错误
		 */
		public static final String CASH_FUND_ERROR="6";
		
		/**
		 * 借用期限错误
		 */
		public static final String BORROW_PERIOD_ERROR="7";
		
		
		/**
		 * 超过最大金额限制 
		 */
		public static final String OVER_MAX_TRADE_MONEY="8";
		
		
		/**
		 * 总操盘资金最多600万
		 */
		public static final String MAX_TRADE_MONEY_600="9";
		
		/**
		 * 利息配置有误
		 */
		public static final String NO_INTEREST_CONFIG="10";
		
		
		/**
		 * 不能选择立即交易
		 */
		public static final String NO_TRADE_START="11";
		
		/**
		 * 超过当前时段最大配资限制金额
		 */
		public static final String OVER_MAX_LEVERMONEY="12";
		
		
		/**
		 * 超过当前时段最大配资方案个数
		 */
		public static final String OVER_MAX_LIMIT_TRADENUM="13";
		
		/**
		 * 超过最大操盘配资方案个数
		 */
		public static final String OVER_MAX_HOLD_TRADE_NUM="14";
	}
	
	
	/**
	 * 配资
	 * @author zhouchen
	 * 2015年5月26日 上午10:46:41
	 */
	public class ConfirmTrade{
		/**
		 *用户信息不存在
		 */
		public static final String USER_INFO_NOT_EXIST="3";
		/**
		 * 利息配置有误
		 */
		public static final String NO_INTEREST_CONFIG="4";
		
		/**
		 * 不能选择立即交易
		 */
		public static final String NO_TRADE_START="5";
		
		/**
		 * 超过当前时段最大配资限制金额
		 */
		public static final String OVER_MAX_LEVERMONEY="6";
		
		
		/**
		 * 超过当前时段最大配资方案个数
		 */
		public static final String OVER_MAX_LIMIT_TRADENUM="7";
		
		/**
		 * 超过最大操盘配资方案个数
		 */
		public static final String OVER_MAX_HOLD_TRADE_NUM="8";
		
		/**
		 * 超过倍数对应的最大金额
		 */
		public static final String OVER_MAX_TRADE_MONEY="9";
	}
	
	
	
	/**
	 * 修改绑定手机
	 * @author zhouchen
	 * 2015年5月26日 上午10:46:41
	 */
	public class UpdatePhone{
		/**
		 *用户信息不存在
		 */
		public static final String USER_INFO_NOT_EXIST="3";
		
		/**
		 * 手机号码已经存在
		 */
		public static final String MOBILE_EXIST="4";
		
		/**
		 * 验证码失效
		 */
		public static final String INVALID_CODE="5";
		
		/**
		 * 验证码错误或为空
		 */
		public static final String ERROR_CODE="6";
	}
	
	

	/**
	 * 管理费
	 * @author zhouchen
	 * 2015年5月26日 上午10:46:41
	 */
	public class ManageFee{
		/**
		 *用户信息不存在
		 */
		public static final String USER_INFO_NOT_EXIST="3";
	}
	
	/**
	 * 资金明细
	 * @author zhouchen
	 * 2015年5月26日 上午10:46:41
	 */
	public class FundDetail{
		/**
		 *用户信息不存在
		 */
		public static final String USER_INFO_NOT_EXIST="3";
	}
	
	
	/**
	 * 支付宝支付
	 * @author zhouchen
	 * 2015年5月26日 上午10:46:41
	 */
	public class Alipay{
		/**
		 *用户信息不存在
		 */
		public static final String USER_INFO_NOT_EXIST="3";
		
		/**
		 *支付宝账号不能为空
		 */
		public static final String ACCOUNT_IS_NULL="4";
		
		/**
		 *支付必须大于1元
		 */
		public static final String UNDER_MIN_PAY_MONEY="5";
	}
	
	
	/**
	 * 追加保证金
	 * @author zhouchen
	 * 2015年5月26日 上午10:46:41
	 */
	public class AddBond{
		/**
		 *用户信息不存在
		 */
		public static final String USER_INFO_NOT_EXIST="3";
		
		/**
		 *系统维护时间，请稍后再试。
		 */
		public static final String SYSTEM_MAINTENANCE_TIME="4";
		
		/**
		 * 追加金额最少为总操盘资金的1%。
		 */
		public static final String ERROR_ADD_BOND_MONEY="5";
		
		/**
		 * 追加金额超过单个账号最大值
		 */
		public static final String OVER_ACCOUNT_MAX_MONEY="6";
		
		/**
		 * 现在无法追加方案或保证金，请联系客服
		 */
		public static final String UNABLE_ADD_BOND="7";
		
		/**
		 * 未找到对应的配资方案
		 */
		public static final String PROGRAM_NOT_EXIST="8";
		
		
		/**
		 * 只有操盘中的方案才能追加保证金
		 */
		public static final String ONLY_HANDLING_TRADE_CAN_ADDBOND="9";
	}
	/**
	 * 终结方案
	 * @author zhouchen
	 * 2015年5月26日 上午10:46:41
	 */
	public class EndProgram{
		/**
		 *用户信息不存在
		 */
		public static final String USER_INFO_NOT_EXIST="3";
		
		/**
		 * 方案不存在
		 */
		public static final String PROGRAM_NOT_EXIST="4";
		
		/**
		 * 您已提交过终结方案申请不能再次提交！
		 */
		public static final String REPEAT_SUBMIT="5";
		
		/**
		 *系统维护时间，请稍后再试。
		 */
		public static final String SYSTEM_MAINTENANCE_TIME="6";
		
		/**
		 * 只有操盘中的方案才能终结
		 */
		public static final String ONLY_HANDLING_TRADE_CAN_END="7";
		
	}
	
	
	/**
	 * 支付宝自动支付
	 * @author zhouchen
	 * 2015年5月26日 上午10:46:41
	 */
	public class AutoAlipay{
		/**
		 * 流水号已经存在
		 */
		public static final String SERIAL_NO_EXIST="3";
		
	}
	
	
	
	/**
	 * 设置提现密码
	 * @author zhouchen
	 * 2015年5月26日 上午10:46:41
	 */
	public class SetWithDrawPwd{
		/**
		 *用户信息不存在
		 */
		public static final String USER_INFO_NOT_EXIST="3";
		 
		/**
		 * 提款密码不能与登录密码相同
		 */
		public static final String SAME_WITH_LOGIN_PASSWORD="4";
		
		/**
		 * 密码格式有误
		 */
		public static final String PASSWORD_PATTERN_ERROR="5";
		
		/**
		 * 验证码不对
		 */
		public static final String VALIDATE_CODE_ERROR="6";
		
	}
	
	
	/**
	 * 修改提现密码
	 * @author zhouchen
	 * 2015年5月26日 上午10:46:41
	 */
	public class UpdateWithDrawPwd{
		/**
		 *用户信息不存在
		 */
		public static final String USER_INFO_NOT_EXIST="3";
		
		/**
		 * 提款密码不能与登录密码相同
		 */
		public static final String SAME_WITH_LOGIN_PASSWORD="4";
		
		/**
		 * 新密码不能与原密码相同
		 */
		public static final String SAME_WITH_OLD_PASSWORD="5";
		
		/**
		 * 原提款密码输入错误
		 */
		public static final String OLD_PASSWORD_ERROR="6";
		
		
		/**
		 * 密码格式有误
		 */
		public static final String PASSWORD_PATTERN_ERROR="7";
		
		
	}
	
	
	/**
	 * 用户绑定的银行卡信息
	 * @author zhouchen
	 * 2015年5月26日 上午10:46:41
	 */
	public class BankList{
		/**
		 *用户信息不存在
		 */
		public static final String USER_INFO_NOT_EXIST="3";
		 
	}
	
	
	/**
	 * 新增银行卡
	 * @author zhouchen
	 * 2015年5月26日 上午10:46:41
	 */
	public class AddBank{
		/**
		 *用户信息不存在
		 */
		public static final String USER_INFO_NOT_EXIST="3";
		
		/**
		 * 银行卡卡号已经存在
		 */
		public static final String THE_BANK_IS_EXIST="4";
		
		/**
		 * 请先实名认证方可添加银行卡
		 */
		public static final String IDENTITY_AUTHENTICATION="5";

	}
	
	
	/**
	 * 删除银行卡
	 * @author zhouchen
	 * 2015年5月26日 上午10:46:41
	 */
	public class DelBank{
		/**
		 *用户信息不存在
		 */
		public static final String USER_INFO_NOT_EXIST="3";
		
		/**
		 * 银行卡信息不存在
		 */
		public static final String THE_BANK_NOT_EXIST="4";
		
		
		/**
		 * 该银行卡正在提现处理中，不能删除
		 */
		public static final String WITHDRAWING_CANTOT_DELETE="5";

	}
	
	/**
	 * 设置默认银行卡
	 * @author zhouchen
	 * 2015年5月26日 上午10:46:41
	 */
	public class SetDefaultBank{
		/**
		 * 银行卡信息不存在
		 */
		public static final String THE_BANK_NOT_EXIST="3";

	}
	
	/**
	 * 提现操作
	 * @zhouchen
	 * 2015年5月26日 上午10:46:41
	 */
	public class WithDrawHandle{
		/**
		 *用户信息不存在
		 */
		public static final String USER_INFO_NOT_EXIST="3";
		
		/**
		 * 银行卡信息不存在
		 */
		public static final String THE_BANK_NOT_EXIST="4";
		
		
		/**
		 *存在欠费无法提现
		 */
		public static final String EXIST_ARREARAGE="5";
		
		/**
		 * 系统升级期间无法提现
		 */
		public static final String SYSTEM_UPGRADE_TIME="6";
		
		/**
		 * 余额不足不能提现
		 */
		public static final String NOT_SUFFICIENT_FUNDS="7";
		
		/**
		 * 当天取款次数不能超过5次
		 */
		public static final String OVER_LIMT_WITHDRAW_NUMBER="8";
		
		/**
		 * 每次提现金额不能小于10元
		 */
		public static final String MIN_WITHDRAW_MONEY="9";
		
		/**
		 * 提现密码错误
		 */
		public static final String WITHDRAW_PASSWORD_ERROR="10";
		
		/**
		 * 网银平台暂不支持此银行提现，请重新添加银行！
		 */
		public static final String NOT_SUPPORT_THE_BANK="11";
		
		/**
		* 网银限制，单笔提现金额不能超过50000元，每天可提现5笔。
		*/

		public static final String BB_LIMIT_MAX_MONEY="12";
		
		
		/**
		* 未实名认证
		*/
		public static final String NOT_CERTIFICATION="13";
	}
	
	

	/**
	 * 绑定支付宝帐号
	 * @author zhouchen
	 * 2015年5月26日 上午10:46:41
	 */
	public class BindAlipay{
		/**
		 *用户信息不存在
		 */
		public static final String USER_INFO_NOT_EXIST="3";
		
		/**
		 * 支付宝帐号已经存在
		 */
		public static final String ALIPAY_ACCOUNT_EXIST="4";
		
	}
	
	
	/**
	 * 银行卡转账
	 * @zhouchen
	 * 2015年12月15日
	 */
	public class BankTransfer{
		/**
		 *用户信息不存在
		 */
		public static final String USER_INFO_NOT_EXIST="3";
		
		/**
		 * 充值金额不能小于1
		 */
		public static final String MONEY_CANNOT_LESS_THAN_ONE="4";
		
	}
	
	/**
	* @Title: ResultStatusConstant.java     
	* @Description: 支付宝返回常量信息    
	* @author： WangPinQun 
	* @E-mail：wangpinqun@tzdr.com
	* @company： 上海信闳投资管理有限公司重庆分公司
	* @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
	* @date： 2016年3月22日 下午2:32:19    
	* @version： V1.0
	 */
	public static class BindAlipayConstant{
		
		/**
		 * 支付宝账户已经存在
		 */
		public static final String ALIPAYACOUNT_EXIST = "2"; 
		
		/**
		 * 支付宝账户不能为空
		 */
		public static final String ALIPAYACOUNT_NOT_NULL = "3"; 
		
		/**
		 * 未找到支付宝账户信息
		 */
		public static final String NOT_FIND_ALIPAYACOUNT = "4"; 
	}
	
	/**
	* @Title: ResultStatusConstant.java     
	* @Description: 账户实名认证业务返回常量信息   
	* @author： WangPinQun 
	* @E-mail：wangpinqun@tzdr.com
	* @company： 上海信闳投资管理有限公司重庆分公司
	* @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
	* @date： 2016年3月22日 下午3:09:08    
	* @version： V1.0
	 */
	public static class ValidateCardConstant{
		
		/**
		 * 实名认证失败
		 */
		public static final String CARD_SECURITY_FAIL = "2";
		
		/**
		 * 身份证号码格式错误
		 */
		public static final String CARD_FORMAT_ERROR = "3";
		
		/**
		 * 该实名已被认证过
		 */
		public static final String CARD_IS_SECURITY= "4";
		
		/**
		 * 实名不能为空
		 */
		public static final String NAME_NOT_NULL = "5";
		
		/**
		 * 身份证号码不能为空
		 */
		public static final String CARD_NOT_NULL = "6";
		
		/**
		 * 已经实名认证过
		 */
		public static final String IS_SECURITY = "7";
		
		/**
		 * 实名认证失败超过最高次数
		 */
		public static final String VALIDATE_OVER_LIMITED = "8";
	}
	
	/**
	* @Title: BindPhoneConstant.java     
	* @Description: 绑定手机号码    
	* @author： WangPinQun 
	* @E-mail：wangpinqun@tzdr.com
	* @company： 上海信闳投资管理有限公司重庆分公司
	* @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
	* @date： 2016年3月22日 下午4:07:47    
	* @version： V1.0
	 */
	public static class BindPhoneConstant{
		
		/**
		 * 手机号码已经存在
		 */
		public static final String PHONE_EXIST = "2";
		
		/**
		 * 原验证码超时
		 */
		public static final String OLD_CODE_OVER_TIME = "3";

		/**
		 * 原验证码错误
		 */
		public static final String OLD_CODE_ERROR = "4";
		
		/**
		 * 新验证码超时
		 */
		public static final String NEW_CODE_OVER_TIME = "5";

		/**
		 * 新验证码错误
		 */
		public static final String NEW_CODE_ERROR = "6";
		
		/**
		 * 原验证码不能为空
		 */
		public static final String OLD_CODE_NOT_NULL = "7";

		/**
		 * 新验证码不能为空
		 */
		public static final String NEW_CODE_NOT_NULL = "8";
		
		/**
		 * 新手机号码不能为空
		 */
		public static final String PHONE_NOT_NULL = "9";
		
		/**
		 * 新手机号码格式错误
		 */
		public static final String PHONE_FORMAT_ERROR  = "10";
	}
	
	/**
	* @Title: ResultStatusConstant.java     
	* @Description: 帐号余额以及汇率    
	* @author： WangPinQun 
	* @E-mail：wangpinqun@tzdr.com
	* @company： 上海信闳投资管理有限公司重庆分公司
	* @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
	* @date： 2016年3月23日 下午1:54:59    
	* @version： V1.0
	 */
	public static class BalancerateConstant{
		
		/**
		 * 业务类型不能为空
		 */
		public static final String BUSINESSTYPE_NOT_NULL = "9";
	}
	
	/**
	* @Title: ResultStatusConstant.java     
	* @Description: 追加保证金    
	* @author： WangPinQun 
	* @E-mail：wangpinqun@tzdr.com
	* @company： 上海信闳投资管理有限公司重庆分公司
	* @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
	* @date： 2016年3月23日 下午3:14:58    
	* @version： V1.0
	 */
	public static class AddbondConstant{
		
		/**
		 * 追加保证金大于用户余额
		 */
		public static final String BALANCE_NOT_ENOUGH = "2";
		
		/**
		 * 未找到该方案
		 */
		public static final String UN_FIND_DATA = "3";
		
		/**
		 * 方案已完结
		 */
		public static final String DATA_IS_OVER = "4";
		
		/**
		 * 追加金额低于默认最小追加保证金
		 */
		public static final String ADDBOND_TOO_SMALL = "5";
		
		/**
		 * 方案编号不能为空
		 */
		public static final String ID_NOT_NULL = "6";
		
		/**
		 * 追加金额不能为空
		 */
		public static final String ADDBOND_NOT_NULL = "7";
	}
	
	/**
	* @Title: ResultStatusConstant.java     
	* @Description: 期货终结方案    
	* @author： WangPinQun 
	* @E-mail：wangpinqun@tzdr.com
	* @company： 上海信闳投资管理有限公司重庆分公司
	* @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
	* @date： 2016年3月23日 下午3:35:51    
	* @version： V1.0
	 */
	public static class EndtradeConstant{
		
		/**
		 * 未找到该方案
		 */
		public static final String UN_FIND_FRADE_DATA = "2";
		
		/**
		 * 已申请终结
		 */
		public static final String ALREADY_SUBMIT = "3";
		
		/**
		 * 非操盘中
		 */
		public static final String UN_STOCK_OPERATION = "4";
		
		/**
		 * 未找到折扣劵信息
		 */
		public static final String UN_FIND_COUPON_DATA = "5";
		
		/**
		 * 该折扣劵无法使用
		 */
		public static final String COUPON_EEROR = "6";
		
		/**
		 * 方案编号
		 */
		public static final String ID_NOT_NULL = "7";
		
		/**
		 * 折扣劵编号不能为空
		 */
		public static final String CID_NOT_NULL = "8";
		
		/**
		 * 业务类型不能为空
		 */
		public static final String BUSINESSTYPE_NOT_NULL = "9";
	}
	
	/**
	* @Title: ResultStatusConstant.java     
	* @Description: 使用红包      
	* @author： WangPinQun 
	* @E-mail：wangpinqun@tzdr.com
	* @company： 上海信闳投资管理有限公司重庆分公司
	* @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
	* @date： 2016年3月24日 下午3:10:53    
	* @version： V1.0
	 */
	public static class CouponEmployConstant{
		
		/**
		 * 未找到该方案
		 */
		public static final String UN_FIND_FCOUPON_DATA = "2";
		
		/**
		 * 该优惠劵已过期
		 */
		public static final String FCOUPON_OVER_TIME = "3";
		
		/**
		 * 该优惠劵已使用
		 */
		public static final String FCOUPON_IS_USE = "4";
		
		/**
		 * 非法类型数据
		 */
		public static final String FCOUPON_TYPE_ERROR = "5";
		
		/**
		 * 红包编号不能为空
		 */
		public static final String ID_NOT_NULL = "6";
	}
	
	/**
	* @Title: ResultStatusConstant.java     
	* @Description: 网页充值   
	* @author： WangPinQun 
	* @E-mail：wangpinqun@tzdr.com
	* @company： 上海信闳投资管理有限公司重庆分公司
	* @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
	* @date： 2016年3月28日 下午4:22:41    
	* @version： V1.0
	 */
	public static class PayConstant{
		
		/**
		 * 充值金额不能为空
		 */
		public static final String MONEY_NOT_NULL = "2";
		
		/**
		 * 充值金额错误
		 */
		public static final String MONEY_ERROR = "3";
	}
}
