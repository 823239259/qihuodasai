package com.tzdr.api.request;

import com.tzdr.api.constants.DataConstant;
import com.tzdr.domain.constants.Constant;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;


/**
 * 
 * <B>说明: </B>支付宝自动 充值用户信息请求
 * @zhouchen
 * 2016年1月20日
 */
public class AutoAliPayRequest{

	/**
	 * 流水号 
	 */
	private String serialNo;
	
	/**
	 * 交易时间
	 */
	private String tradeTime;
	
	/**
	 *  充值金额
	 */
	private Double money;
	
	
	/**
	 * 支付宝帐号
	 */
	private String account;
	
	/**
	 * 真实姓名
	 */
	private String realName;
	
	/**
	 * 充值来源(1:投资达人 2：配股宝)
	 */
	private int source;

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	/**
	 * 校验请求参数是否 无效
	 * 无效 ：返回 true 有效：返回false
	 * @return
	 */
	public boolean isInvalid(){
		if (StringUtil.isBlank(this.account) 
			|| StringUtil.isBlank(this.serialNo)
			|| StringUtil.isBlank(this.tradeTime)
			|| StringUtil.isBlank(this.realName)
			|| ObjectUtil.equals(null,money)
			|| this.money <= DataConstant.ZERO_MONEY){
			
			return true;
		}
		//充值来源输入有误
		if (source !=Constant.Source.PGB 
				&& source !=Constant.Source.TZDR){
			return true;
		}
		return false;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}
	
}
