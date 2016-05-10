package com.tzdr.api.request;

import jodd.util.StringUtil;

/**
 * 
 * <B>说明: </B>绑定支付宝帐号请求
 * @zhouchen
 * 2016年1月20日
 */
public class BindAlipayRequest extends BaseRequet {

	/**
	 * 支付宝帐号
	 */
	private String alipayAcount;

	public String getAlipayAcount() {
		return alipayAcount;
	}

	public void setAlipayAcount(String alipayAcount) {
		this.alipayAcount = alipayAcount;
	}
	
	/**
	 * 校验参数是否有效 
	 * @return
	 */
	public boolean isInvalid(){
		if (StringUtil.isBlank(this.alipayAcount)
				|| StringUtil.isBlank(this.getUid())){
			return true;
		}
		
		return false;
	}
}
