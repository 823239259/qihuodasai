package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import jodd.util.ObjectUtil;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.constants.Constant;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see
 * @version 2.0
 * 2015年3月9日下午4:36:51
 */
public class AllRechargeListVo implements Serializable {
	
private static final long serialVersionUID = 6997408108243390441L;
	
	@SqlColumn
	private String id;
	//手机号
	@SqlColumn
	@AllowExcel(name="手机号")
	private String mobile;
	
	@SqlColumn
	private Integer paymentChannel;
	
	@SqlColumn
	@AllowExcel(name="用户姓名")
	private String tname;//用户姓名
	
	@SqlColumn
	@AllowExcel(name="订单号")
	private String rechargeID;
	
	@SqlColumn
	@AllowExcel(name="流水号")
	private String tradeNo;
	
	//所属银行
	@SqlColumn
	private String tradeAccountBank;
	
	@AllowExcel(name="交易银行")
	private String tradeAccountBankStr;

	//充值金额
	@SqlColumn
	private Double money;
	
	@AllowExcel(name="充值表单金额")
	private String moneyStr;
	
	@SqlColumn
	private String type;
	
	@AllowExcel(name="支付类型")
	private String typeStr;
	
	@AllowExcel(name="网银通道")
	private String paymentChannelStr;
	
	@SqlColumn
	private BigInteger addtime;
	
	@AllowExcel(name="提交时间")
	private String addtimeStr;
	
	//实际到账金额
	@SqlColumn
	private Double actualMoney;
	
	@AllowExcel(name="实际到账金额")
	private String actualMoneyStr;
	
	//充值状态
	@SqlColumn
	private Integer status;
	
	@AllowExcel(name="充值状态")
	private String statusStr;
	
	@SqlColumn
	private BigInteger oktime;
	
	@AllowExcel(name="审核时间")
	private String okTimeStr;
	
	@SqlColumn
	private Integer source;
	
	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}


	@AllowExcel(name="来源网站")
	private String sourceStr;
	
	public String getSourceStr() {
		if (null == this.getSource()) {
			// this.setSourceStr("投资达人");
		} else if (Constant.Source.TZDR == this.getSource()) {
			this.setSourceStr("维胜");
		} else if (Constant.Source.PGB == this.getSource()) {
			this.setSourceStr("维胜");
		}
		return sourceStr;
	}

	public void setSourceStr(String sourceStr) {
		this.sourceStr = sourceStr;
	}
	
	
	
	
	
	//public RechargeListVo(RechargeList re) {
		/*this.id = re.getId();
		this.wuser = wuser;
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
	    if (wuser != null) {
	    	UserVerified userVer = wuser.getUserVerified();
	    	if (userVer != null) {
	    		this.tname = userVer.getTname();
	    	}
	    }
		
		this.remark = re.getRemark();*/
		


	public String getTypeStr() {
		if (this.getType() != null) {
			this.typeStr = CacheManager.getDataMapByKey("paytype", this.getType());
		}
		return typeStr;
	}


	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}


	public String getAddtimeStr() {
		if (this.addtime != null) {
			this.addtimeStr = TypeConvert.long1000ToDatetimeStr(this.addtime.longValue());
		}
		return addtimeStr;
	}


	public void setAddtimeStr(String addtimeStr) {
		this.addtimeStr = addtimeStr;
	}


	public String getStatusStr() {
		if (this.getStatus() != null) {
			this.statusStr = CacheManager.getDataMapByKey("paystatus",this.getStatus().toString());
		}
		return statusStr;
	}


	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}


	//}
	public AllRechargeListVo() {
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getTname() {
		return tname;
	}


	public void setTname(String tname) {
		this.tname = tname;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getRechargeID() {
		return rechargeID;
	}


	public void setRechargeID(String rechargeID) {
		this.rechargeID = rechargeID;
	}


	public String getTradeNo() {
		return tradeNo;
	}


	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}


	public String getTradeAccountBank() {
		return tradeAccountBank;
	}


	public void setTradeAccountBank(String tradeAccountBank) {
		this.tradeAccountBank = tradeAccountBank;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}


	public String getType() {
		return this.type;
	}


	public void setType(String type) {
		this.type = type;
	}

	public BigInteger getAddtime() {
		return addtime;
	}


	public void setAddtime(BigInteger addtime) {
		this.addtime = addtime;
	}

	public Double getActualMoney() {
		return actualMoney;
	}

	public void setActualMoney(Double actualMoney) {
		this.actualMoney = actualMoney;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
	public BigInteger getOktime() {
		return oktime;
	}


	public void setOktime(BigInteger oktime) {
		this.oktime = oktime;
	}


	public String getOkTimeStr() {
		if (this.oktime != null) {
			this.okTimeStr = TypeConvert.long1000ToDatetimeStr(this.oktime.longValue());
		}
		return okTimeStr;
	}


	public void setOkTimeStr(String okTimeStr) {
		this.okTimeStr = okTimeStr;
	}


	public String getTradeAccountBankStr() {
		if (this.getTradeAccountBank() != null) {
			this.tradeAccountBankStr = CacheManager.getDataMapByKey("bankname",this.getTradeAccountBank().toString());
		}
		return tradeAccountBankStr;
	}

	public void setTradeAccountBankStr(String tradeAccountBankStr) {
		this.tradeAccountBankStr = tradeAccountBankStr;
	}


	public String getMoneyStr() {
		
		if (this.getMoney() != null) {
			BigDecimal moneyVar = new BigDecimal(this.getMoney());
			BigDecimal moneyBig = 
					TypeConvert.scale(moneyVar, TypeConvert.SCALE_VALUE);
			this.moneyStr = moneyBig == null?"":moneyBig.toString();
		}
		
		return moneyStr;
	}


	public void setMoneyStr(String moneyStr) {
		this.moneyStr = moneyStr;
	}


	public String getActualMoneyStr() {
		if (this.getActualMoney() != null) {
			BigDecimal actualMoneyVar = new BigDecimal(this.getActualMoney());
			BigDecimal actualMoneyBig = 
					TypeConvert.scale(actualMoneyVar, TypeConvert.SCALE_VALUE);
			this.actualMoneyStr = actualMoneyBig == null?"":actualMoneyBig.toString();
		}
		return actualMoneyStr;
	}


	public void setActualMoneyStr(String actualMoneyStr) {
		this.actualMoneyStr = actualMoneyStr;
	}


	public Integer getPaymentChannel() {
		
		return paymentChannel;
	}


	public void setPaymentChannel(Integer paymentChannel) {
		this.paymentChannel = paymentChannel;
	}
	

	
	public String getPaymentChannelStr() {
		Integer tempChannel = this.getPaymentChannel();
		if (ObjectUtil.equals(null, tempChannel)){
			return "";
		}
		if (Constant.PaymentChannel.BB_PAY==tempChannel){
			return "币币支付";
		}
		if (Constant.PaymentChannel.UM_PAY==tempChannel){
			return "联动优势";
		}
		return "";
	}


	public void setPaymentChannelStr(String paymentChannelStr) {
		this.paymentChannelStr = paymentChannelStr;
	}
	
	

}
