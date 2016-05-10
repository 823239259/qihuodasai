package com.tzdr.domain.vo;

import java.io.Serializable;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.SqlOrder;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see 手工充值记录
 * @version 2.0
 * 2015年2月17日上午9:24:50
 */
public class HandUserFundVoNew implements Serializable {

	private static final long serialVersionUID = -3103269866800173377L;

	@SqlColumn
	private String id;
	
	@SqlColumn
	@AllowExcel(name="手机号")
	private String mobile;
	@SqlColumn
	private String uid;
	//内部流水号
	@AllowExcel(name="流水号")
	@SqlColumn
	private String no;
	
	/**
	 * 类型：1:充值,2:提现,,3:系统调账,4:系统冲账,5:投标冻结,6:投标成功,10:配资支出,11:利息支出 ,
	 * 12：扣取管理费（新增），13佣金收入，15配资撤回,16利润提取17 追加保证金、18配资欠费、19活动赠送、20活动收回、21补偿收入
	 * ,23提现撤回
	 */
	@SqlColumn
	private Integer type;
	//交易金额
	@SqlColumn
	@AllowExcel(name="金额")
	private double money;
	//总余额
	@SqlColumn
	private double amount;
	//冻结金额
	@SqlColumn
	private double freeze;
	/**
	 * 配资组合号
	 */
	@SqlColumn
	private String lid;
	//外部交易号
	@SqlColumn
	private String trxId;
	//rid 存储配资方案号 programNo
	@SqlColumn
	private String rid;
	@SqlColumn
	private String ruid;
	@SqlColumn
	private Integer addtime;
	/**
	 *支付时间
	 */
	@SqlColumn
	private Integer uptime;
	@AllowExcel(name="原因")
	@SqlColumn
	private String remark;
	
	@AllowExcel(name="调账类型")
	private String typeStr;
	
	@SqlOrder("addtime")
	private String addtimeStr;
	
	@AllowExcel(name="充值时间")
	@SqlOrder("uptime")
	private String uptimeStr;
	
	//收入
	private String inMoney;
	//去出
	private String outMoney;
	
	/**
	 * 支付状态 0未支付 1已支付
	 */
	@SqlColumn
	private short payStatus;
	
	/**
	 * 支付状态 String
	 */
	private String payStatusStr;

	@SqlColumn
	private double inSubMoney;
	
	@SqlColumn
	private double outSubMoney;
	
	private String inSubMoneyStr;
	
	private String outSubMoneyStr;
	
	@SqlColumn
	private double inTolMoney;
	
	@SqlColumn
	private double outTolMoney;
	
	private String inTolMoneyStr;
	
	private String outTolMoneyStr;
	
	public HandUserFundVoNew() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getFreeze() {
		return freeze;
	}

	public void setFreeze(double freeze) {
		this.freeze = freeze;
	}

	public String getLid() {
		return lid;
	}

	public void setLid(String lid) {
		this.lid = lid;
	}

	public String getTrxId() {
		return trxId;
	}

	public void setTrxId(String trxId) {
		this.trxId = trxId;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getRuid() {
		return ruid;
	}

	public void setRuid(String ruid) {
		this.ruid = ruid;
	}

	public Integer getAddtime() {
		return addtime;
	}

	public void setAddtime(Integer addtime) {
		this.addtime = addtime;
	}

	public Integer getUptime() {
		return uptime;
	}

	public void setUptime(Integer uptime) {
		this.uptime = uptime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTypeStr() {
		this.typeStr = CacheManager.getDataMapByKey("userfundType", this.getType() + "");
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	public String getAddtimeStr() {
		if (this.addtime != null) {
			this.addtimeStr = TypeConvert.long1000ToDatetimeStr(this.getAddtime().longValue());
		}
		return addtimeStr;
	}

	public void setAddtimeStr(String addtimeStr) {
		this.addtimeStr = addtimeStr;
	}

	public String getUptimeStr() {
		if (this.uptime != null) {
			this.uptimeStr = TypeConvert.long1000ToDatetimeStr(this.getUptime().longValue());
		}
		return uptimeStr;
	}

	public void setUptimeStr(String uptimeStr) {
		this.uptimeStr = uptimeStr;
	}

	public String getInMoney() {
		if (this.money > 0) {
			this.inMoney = 
					TypeConvert.doubleToBigDecimalScale(money, 
							    TypeConvert.SCALE_VALUE).toString();
		}
		return inMoney;
	}

	public void setInMoney(String inMoney) {
		this.inMoney = inMoney;
	}

	public String getOutMoney() {
		if (this.money < 0) {
			this.outMoney = 
					TypeConvert.doubleToBigDecimalScale(money, 
							    TypeConvert.SCALE_VALUE).toString();
		}
		return outMoney;
	}

	public void setOutMoney(String outMoney) {
		this.outMoney = outMoney;
	}

	public short getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(short payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayStatusStr() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.USERFUND_PAYSTATUS, String.valueOf(this.payStatus));
	}

	public void setPayStatusStr(String payStatusStr) {
		this.payStatusStr = payStatusStr;
	}

	public double getInSubMoney() {
		return inSubMoney;
	}

	public void setInSubMoney(double inSubMoney) {
		this.inSubMoney = inSubMoney;
	}

	public double getOutSubMoney() {
		return outSubMoney;
	}

	public void setOutSubMoney(double outSubMoney) {
		this.outSubMoney = outSubMoney;
	}

	public String getInSubMoneyStr() {
		if (this.inSubMoney > 0) {
			this.inSubMoneyStr = 
					TypeConvert.doubleToBigDecimalScale(inSubMoney, 
							    TypeConvert.SCALE_VALUE).toString();
		}
		return inSubMoneyStr;
	}

	public void setInSubMoneyStr(String inSubMoneyStr) {
		this.inSubMoneyStr = inSubMoneyStr;
	}

	public String getOutSubMoneyStr() {
		if (this.outSubMoney < 0) {
			this.outSubMoneyStr = 
					TypeConvert.doubleToBigDecimalScale(outSubMoney, 
							    TypeConvert.SCALE_VALUE).toString();
		}
		return outSubMoneyStr;
	}

	public void setOutSubMoneyStr(String outSubMoneyStr) {
		this.outSubMoneyStr = outSubMoneyStr;
	}

	public double getInTolMoney() {
		return inTolMoney;
	}

	public void setInTolMoney(double inTolMoney) {
		this.inTolMoney = inTolMoney;
	}

	public double getOutTolMoney() {
		return outTolMoney;
	}

	public void setOutTolMoney(double outTolMoney) {
		this.outTolMoney = outTolMoney;
	}

	public String getInTolMoneyStr() {
		if (this.inTolMoney > 0) {
			this.inTolMoneyStr = 
					TypeConvert.doubleToBigDecimalScale(inTolMoney, 
							    TypeConvert.SCALE_VALUE).toString();
		}
		return inTolMoneyStr;
	}

	public void setInTolMoneyStr(String inTolMoneyStr) {
		this.inTolMoneyStr = inTolMoneyStr;
	}

	public String getOutTolMoneyStr() {
		if (this.outTolMoney < 0) {
			this.outTolMoneyStr = 
					TypeConvert.doubleToBigDecimalScale(outTolMoney, 
							    TypeConvert.SCALE_VALUE).toString();
		}
		return outTolMoneyStr;
	}

	public void setOutTolMoneyStr(String outTolMoneyStr) {
		this.outTolMoneyStr = outTolMoneyStr;
	}
}
