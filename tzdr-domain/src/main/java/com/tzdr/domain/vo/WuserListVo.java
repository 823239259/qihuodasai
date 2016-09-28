package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.cache.CacheManager;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see
 * @version 2.0
 * 2015年2月27日上午10:51:50
 */
public class WuserListVo implements Serializable {
	
	private static final long serialVersionUID = 5413198153714337951L;
	@SqlColumn
	private String id;
	@AllowExcel(name = "手机号")
	@SqlColumn
	private String mobile;//手机号
	@AllowExcel(name = "用户姓名")
	@SqlColumn
	private String tname;
	@SqlColumn
	private String uname;//用户昵称
	
	/**
	 * 用户类型
	 */
	@AllowExcel(name = "用户类型")
	@SqlColumn
	private String userType;

	/**
	 * 配资保证金
	 */
	@AllowExcel(name = "配资金额")
	@SqlColumn
	private Double allocationMoney;
	

	/**
	 * 账户余额
	 */
	@AllowExcel(name = "账号余额")
	@SqlColumn
	private Double avlBal;
	
	//冻结金额
	@AllowExcel(name = "冻结金额")
	@SqlColumn
	private Double frzBal;
	
	@AllowExcel(name = "总充值金额")
	@SqlColumn
	private Double totalCharge;//总充值金额
	@AllowExcel(name = "总申请操盘金额")
	@SqlColumn
	private BigDecimal totalOperate;//总申请操盘金额
	@AllowExcel(name = "恒指操盘手数")
	@SqlColumn
	private BigDecimal htranActualLever;//恒指操盘手数
	@AllowExcel(name = "原油操盘手数")
	@SqlColumn
	private BigDecimal ytranActualLever;//原油操盘手数
	@AllowExcel(name = "富时A50操盘手数")
	@SqlColumn
	private BigDecimal atranActualLever;//富时A50操盘手数
	@AllowExcel(name = "国际综合操盘手数")
	@SqlColumn
	private BigDecimal interActualLever;//国际综合操盘手数
	@AllowExcel(name = "累计提现金额")
	@SqlColumn
	private Double withDrawMoney;//申请提现金额
	
	@AllowExcel(name = "身份证号")
	@SqlColumn
	private String idcard;//身份证号
	@AllowExcel(name = "邮箱")
	@SqlColumn
	private String email;//邮箱
	/**
	 * 支付宝帐号
	 */
	@AllowExcel(name = "支付宝帐号")
	@SqlColumn
	private String alipayAccount;
	
	
	@SqlColumn
	private BigInteger ctime;//注册时间
	
	

	@SqlColumn
	private BigInteger lastLoginTime;//最后登陆时间
	
	/**
	 * 来源网站
	 */
	
	@SqlColumn
	private Integer source;
	
	@AllowExcel(name = "注册时间")
	private String ctimeStr;
		
	@AllowExcel(name = "最后登陆时间")
	private String lastLoginTimeStr;
		
	@AllowExcel(name = "来源网站")
	private String sourceName;
		
	@AllowExcel(name = "渠道来源")
	@SqlColumn
	private String channel;
	@AllowExcel(name = "关键词来源")
	@SqlColumn
	private String keyword;


	

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getTname() {
		return tname == null ? this.uname:tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}


	public String getUserType() {
		return CacheManager.getDataMapByKey("userType",this.userType);
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}


	public Double getAllocationMoney() {
		return allocationMoney;
	}

	public void setAllocationMoney(Double allocationMoney) {
		this.allocationMoney = allocationMoney;
	}


	public Double getAvlBal() {
		return avlBal;
	}

	public void setAvlBal(Double avlBal) {
		this.avlBal = avlBal;
	}
	
	public Double getFrzBal() {
		return frzBal;
	}

	public void setFrzBal(Double frzBal) {
		this.frzBal = frzBal;
	}

	
	
	
	public Double getTotalCharge() {
		return totalCharge;
	}

	public void setTotalCharge(Double totalCharge) {
		this.totalCharge = totalCharge;
	}

	public BigDecimal getTotalOperate() {
		return totalOperate;
	}

	public void setTotalOperate(BigDecimal totalOperate) {
		this.totalOperate = totalOperate;
	}

	public BigDecimal getHtranActualLever() {
		return htranActualLever;
	}

	public void setHtranActualLever(BigDecimal htranActualLever) {
		this.htranActualLever = htranActualLever;
	}

	public BigDecimal getYtranActualLever() {
		return ytranActualLever;
	}

	public void setYtranActualLever(BigDecimal ytranActualLever) {
		this.ytranActualLever = ytranActualLever;
	}

	public BigDecimal getAtranActualLever() {
		return atranActualLever;
	}

	public void setAtranActualLever(BigDecimal atranActualLever) {
		this.atranActualLever = atranActualLever;
	}

	public BigDecimal getInterActualLever() {
		return interActualLever;
	}

	public void setInterActualLever(BigDecimal interActualLever) {
		this.interActualLever = interActualLever;
	}

	public Double getWithDrawMoney() {
		return withDrawMoney;
	}

	public void setWithDrawMoney(Double withDrawMoney) {
		this.withDrawMoney = withDrawMoney;
	}
	
	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getAlipayAccount() {
		return alipayAccount;
	}

	public void setAlipayAccount(String alipayAccount) {
		this.alipayAccount = alipayAccount;
	}

	public BigInteger getCtime() {
		return ctime;
	}

	public void setCtime(BigInteger ctime) {
		this.ctime = ctime;
	}
	

	public BigInteger getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(BigInteger lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}


	
	public String getCtimeStr() {
		if (this.ctime != null) {
			this.ctimeStr = TypeConvert.long1000ToDatetimeStr(this.ctime.longValue());
		}
		return ctimeStr;
	}

	public void setCtimeStr(String ctimeStr) {
		this.ctimeStr = ctimeStr;
	}

	public String getLastLoginTimeStr() {
		if (this.lastLoginTime != null) {
			this.lastLoginTimeStr = TypeConvert.long1000ToDatetimeStr(this.lastLoginTime.longValue());
		}
		return lastLoginTimeStr;
	}

	public void setLastLoginTimeStr(String lastLoginTimeStr) {
		this.lastLoginTimeStr = lastLoginTimeStr;
	}

	
	public String getSourceName() {
		if(this.source == 2) {
			sourceName = "配股宝Wap";
		} else if(this.source == 5) {
			sourceName = "配股宝Web";
		} else if(this.source == 6) {
			sourceName = "喊单直播间";
		} else if(this.source == 7) {
			sourceName = "维胜APP";
		} else {
			sourceName = "维胜Web";
		}
		
		/*
		if(this.source == 2 || this.source == 5){
			sourceName = "配股宝";
		}else{
			sourceName = "投资达人";
		}
		*/
		
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}


	
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	


}
