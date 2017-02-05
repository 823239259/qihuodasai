package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tzdr.common.domain.BaseEntity;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;

@Entity
@Table(name="w_user_fund")
public class UserFund  extends BaseEntity {


	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;

	private String uid;
	//内部流水号
	private String no;
	
	/**
	 * 类型：1:充值,2:提现,,3:系统调账,4:系统冲账,5:投标冻结,6:投标成功,10:配资支出,11:利息支出 ,
	 * 12：扣取管理费（新增），13佣金收入，15配资撤回,16利润提取17 追加保证金、18配资欠费、19活动赠送、20活动收回、21补偿收入
	 * ,23提现撤回,24 抵扣劵收入 25:配资管理费撤回  26:配资利息撤回 27:补仓欠费 30:内转划入 31：内转划出 
	 */
	private Integer type;
	
	/**
	 * 业务类型：1：富时A50(方案支出),2:富时A50(方案撤回),3:富时A50结算,4：国际原油(方案支出),5：恒生指数(方案支出)
	 */
	private Integer businessType;
	
	//交易金额
	private double money;
	//总余额
	private double amount;
	//冻结金额
	private double freeze;
	/**
	 * 配资组合号
	 */
	private String lid;
	//外部交易号
	private String trxId;
	//rid 存储配资方案号 programNo
	private String rid;
	private String ruid;
	private Long addtime;
	private short sync;
	/**
	 *支付时间
	 */
	private Long uptime;
	
	/**
	 *管理员确认：1确认，0未确认  
	 */
	private short status;
	private String remark;
	
	private String sysUserId;//审核人
	
	@Transient 
	private String typevalue;
	public String getTypevalue() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.USER_FUND_TYPE, String.valueOf(this.type));
		
	}

	public void setTypevalue(String typevalue) {
		this.typevalue = typevalue;
	}

	/**
	 * 0:未支付 1:已支付
	 */
	private short payStatus;
	
	@Transient
	private boolean unpayFlag;
	
	

	public boolean isUnpayFlag() {
		return unpayFlag;
	}

	public void setUnpayFlag(boolean unpayFlag) {
		this.unpayFlag = unpayFlag;
	}

	@Column(name="uid", length=32)
	public String getUid() {
		return this.uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	@Transient 
	private String payStatusValue;
	
	/**
	 * type:27【补仓欠费】时，此字段表示补仓明细拆分 【 0：否 ， 1：是】  type:13【佣金收入】时，彼字段表示佣金明细异常【空：正常，1：手工执行】 type:18【配资欠费】时，此字段表示配资欠费明细拆分 【 0：否 ， 1：是】
	 */
	private Integer typeStatus;
	
	
	public String getPayStatusValue() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.USERFUND_PAYSTATUS, String.valueOf(this.payStatus));
	}

	public void setPayStatusValue(String payStatusValue) {
		this.payStatusValue = payStatusValue;
	}

	@Column(name="no", nullable=false, length=30)
	public String getNo() {
		return this.no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	@Column(name="type", nullable=false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name="money", nullable=false)
	public double getMoney() {
		return this.money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	@Column(name="amount", nullable=false)
	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Column(name="freeze", nullable=false)
	public double getFreeze() {
		return this.freeze;
	}

	public void setFreeze(double freeze) {
		this.freeze = freeze;
	}

	@Column(name="lid", length=32)
	public String getLid() {
		return this.lid;
	}

	public void setLid(String lid) {
		this.lid = lid;
	}

	@Column(name="TrxId", length=32)
	public String getTrxId() {
		return this.trxId;
	}

	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}

	@Column(name="rid", length=32)
	public String getRid() {
		return this.rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	@Column(name="ruid", length=32)
	public String getRuid() {
		return this.ruid;
	}

	public void setRuid(String ruid) {
		this.ruid = ruid;
	}

	@Column(name="addtime", nullable=false)
	public Long getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Long addtime) {
		this.addtime = addtime;
	}

	@Column(name="sync", nullable=false)
	public short getSync() {
		return this.sync;
	}

	public void setSync(short sync) {
		this.sync = sync;
	}

	@Column(name="uptime", nullable=false)
	public Long getUptime() {
		return this.uptime;
	}

	public void setUptime(Long uptime) {
		this.uptime = uptime;
	}

	@Column(name="status", nullable=false)
	public short getStatus() {
		return this.status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	@Column(name="remark", nullable=false)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public short getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(short payStatus) {
		this.payStatus = payStatus;
	}
	public UserFund() {
		// TODO Auto-generated constructor stub
	}

	@Column(name="type_status", nullable=false)
	public Integer getTypeStatus() {
		return typeStatus;
	}

	public void setTypeStatus(Integer typeStatus) {
		this.typeStatus = typeStatus;
	}

	public UserFund(String uid, String no, Integer type, double money,
			Long addtime, String remark, short payStatus) {
		super();
		this.uid = uid;
		this.no = no;
		this.type = type;
		this.money = money;
		this.addtime = addtime;
		this.remark = remark;
		this.payStatus = payStatus;
		
	}

	@Column(name="business_type")
	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}
	@Column(name="sys_user_id",length=32)
	public String getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(String sysUserId) {
		this.sysUserId = sysUserId;
	}
	
}