package com.tzdr.domain.api.constants;


/**
 * app 常量
 * @author zhouchen
 * 2015年5月25日 下午5:04:51
 */
public class Constant {
	/**
	 * token 失效时间 2小时
	 */
	public static final long TOKEN_INVALID_TIME=2*60*60;
	
	/**
	 * 涌金版方案类型
	 */
	public static final int FEE_TYPE_WELLGOLD=2;
	/**
	 * 验资中
	 */
	public static final int CAPITAL_VERIFICATION=0;
	/**
	 * 操盘中
	 */
	public static final int OPERATING=1;
	/**
	 * 已完结
	 */
	public static final int overd=2;
	/**
	 * 开户中
	 */
	public static final int OPENING_ACCOUNT=3;
	/**
	 * 开户失败
	 */
	public static final int OPEN_FAIL=4;
	
	/**
	 * 待审核
	 */
	public static final int AUDIT_STATUS_WAIT=0;
	
	/**
	 * 审核通过
	 */
	public static final int AUDIT_STATUS_PASS=1;
	
	/**
	 * 审核不通过
	 */
	public static final int AUDIT_STATUS_NOT_PASS=2;
	
	
	
	/**
	 * 最大操盘方案数 typeKey
	 */
	public static final String HOLD_MAX_TRADENUM_TYPE_KEY="holdMaxNumType";
	
	/**
	 * 最大操盘方案数 valueKey
	 */
	public static final String HOLD_MAX_TRADENUM_VALUE_KEY="holdMaxNumValue";
	
	
	/**
	 * 最大操盘方案数默认值3 最多3个
	 */
	public static final int DEFAULT_HOLD_MAX_TRADENUM=3;
}
