package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import jodd.util.StringUtil;

import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.web.entity.RechargeList;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;

/**
 * 
 * <p>支付审核VO对象</p>
 * @author LiuQing
 * @see 手机号
 * @version 2.0
 * 2015年1月5日下午7:58:20
 */
public class RechargeListVo implements Serializable {

	private static final long serialVersionUID = 6997408108243390441L;
	
	private WUser wuser;
	
	private String id;
	
	//手机号
	private String mobileNo;
	//充值金额
	private String money;
	//实际到账金额
	private String actualMoney;
	
	//登录帐号
	private String account;
	//充值状态
	private String status;
	
	private Integer statusValue;
	
	//交易号
	private String tradeNo;
	//订单号
	private String orderNo;
	//用户ID
	private String uid;
	//充值时间
	private String oktime;
	
	private String addtime;
	
	//所属银行
	private String tradeAccountBank;
	
	private String remark;
	
	private String rechargeAccountName;//操作人员名
	
	private String sysType;
	
	private String no;
	
	private String type;
	
	private String tname;//用户姓名
	
	/**
	 * 来源
	 */
	private String source;
	
	
	public RechargeListVo(RechargeList re,WUser wuser) {
		this.id = re.getId();
		this.wuser = (WUser)TypeConvert.createBaseTypeClone(wuser);
		BigDecimal moneyVar = null;
		if (re.getMoney() != null) {
			moneyVar = new BigDecimal(re.getMoney());
		}
		BigDecimal moneyBig = TypeConvert.scale(moneyVar, TypeConvert.SCALE_VALUE);
		this.money = moneyBig == null?"":moneyBig.toString();
		if (this.wuser != null) {
			this.mobileNo = this.wuser.getMobile();
		}
		status = CacheManager.getDataMapByKey("paystatus",re.getStatus().toString());
		
		this.statusValue = re.getStatus();
		this.account = re.getAccount();
		this.tradeNo = re.getTradeNo();
		if (re.getActualMoney() != null) {
			BigDecimal actualMoneyVar = new BigDecimal(re.getActualMoney());
			BigDecimal actualMoneyBig = 
					TypeConvert.scale(actualMoneyVar, TypeConvert.SCALE_VALUE);
			this.actualMoney = actualMoneyBig == null?"":actualMoneyBig.toString();
			
		}
		if (re.getOktime() != null) {
			this.oktime = TypeConvert.longToDatetimeStrNotNull(re.getOktime() * 1000);
		}
		if (re.getSysType() != null && !"".equals(re.getSysType())) {
			this.sysType = CacheManager.getDataMapByKey("sysType",re.getSysType());
		}
		this.no = re.getNo();
		
		//所属银行
		if (re.getTradeAccount() != null) {
			this.tradeAccountBank = CacheManager.getDataMapByKey("bankname",re.getTradeAccount().toString());
		}
		
		if (re.getAddtime() != null) {
			this.addtime = TypeConvert.long1000ToDatetimeStr(re.getAddtime());
		}
		if (re.getType() != null) {
			this.type = CacheManager.getDataMapByKey("paytype", re.getType());
		}
		// 来源
		if (null == re.getSource()) {
			this.setSource("投资达人");
		} else {
			switch (re.getSource()) {
			case Constant.Source.TZDR:
				this.setSource("投资达人");
				break;
			case Constant.Source.PGB:
				this.setSource("配股宝");
				break;
			default:
				this.setSource("投资达人");
				break;
			}
		}
	    if (wuser != null) {
	    	UserVerified userVer = wuser.getUserVerified();
	    	if (userVer != null) {
	    		this.tname = userVer.getTname();
	    	}
	    }
	    else
	    {
	    	// 如果是支付宝充值   未能自动充值的成功的记录，账户姓名存储在uid 当中，记录状态为未处理或失败时，此处处理显示真实姓名
	    	if (StringUtil.equals(re.getType(),TypeConvert.ALIPAY_TYPE) 
	    			&& (re.getStatus().intValue()==TypeConvert.RECHARGE_LIST_PAYS_STATUS_WAITING
	    				|| re.getStatus().intValue()==1)){
	    		this.tname = re.getUid();
	    	}
	    }
		
		this.remark = re.getRemark();
		
	}
	
	public RechargeListVo() {
		super();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMobileNo() {
		return mobileNo;
	}
	
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}

	public String getMoney() {
		return money;
	}


	public void setMoney(String money) {
		this.money = money;
	}


	public String getActualMoney() {
		return actualMoney;
	}


	public void setActualMoney(String actualMoney) {
		this.actualMoney = actualMoney;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getTradeNo() {
		return tradeNo;
	}
	
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	
	public String getOrderNo() {
		return orderNo;
	}
	
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getUid() {
		return uid;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}

	public WUser getWuser() {
		return wuser;
	}

	public void setWuser(WUser wuser) {
		this.wuser = wuser;
	}

	public String getOktime() {
		return oktime;
	}

	public void setOktime(String oktime) {
		this.oktime = oktime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(Integer statusValue) {
		this.statusValue = statusValue;
	}

	public String getTradeAccountBank() {
		return tradeAccountBank;
	}

	public void setTradeAccountBank(String tradeAccountBank) {
		this.tradeAccountBank = tradeAccountBank;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRechargeAccountName() {
		return rechargeAccountName;
	}

	public void setRechargeAccountName(String rechargeAccountName) {
		this.rechargeAccountName = rechargeAccountName;
	}

	public String getSysType() {
		return sysType;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
}
