package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import jodd.util.ObjectUtil;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.constants.Constant;



/**
 * 币币支付 提现待处理订单
 * @zhouchen
 * 2015年12月3日
 */
public class BibiTreatDrawListVo implements Serializable{
	 /**
	  * 提现记录 id 
	  */
	 private String id;
	 
	 /**
	  * 币币支付订单号
	  */
	 private String bbOrderId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBbOrderId() {
		return bbOrderId;
	}

	public void setBbOrderId(String bbOrderId) {
		this.bbOrderId = bbOrderId;
	}
	 
	 
}