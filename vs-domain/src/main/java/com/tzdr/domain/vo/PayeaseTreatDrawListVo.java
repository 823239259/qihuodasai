package com.tzdr.domain.vo;

import java.io.Serializable;



/**
 * 币币支付 提现待处理订单
 * @zhouchen
 * 2015年12月3日
 */
public class PayeaseTreatDrawListVo implements Serializable{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	  * 提现记录 id 
	  */
	 private String id;
	 
	 /**
	  * 订单号
	  */
	 private String  orderId;
	 
	 /**
	  * 商户号
	  */
	 private String vmid;
	 
	 /**
	  * 商户秘钥
	  */
	 private String secret;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getVmid() {
		return vmid;
	}

	public void setVmid(String vmid) {
		this.vmid = vmid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
	 
	 
}