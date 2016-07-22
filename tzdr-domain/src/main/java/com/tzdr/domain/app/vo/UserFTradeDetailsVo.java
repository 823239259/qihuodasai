package com.tzdr.domain.app.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.tzdr.common.utils.SqlColumn;

/**  
 * @Title: FTradeVo.java     
 * @Description: 用户期货产品方案详情信息VO    
 * @author： WangPinQun 
 * @E-mail：wangpinqun@tzdr.com
 * @company： 上海信闳投资管理有限公司重庆分公司
 * @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
 * @date： 2016年3月22日 上午9:49:05    
 * @version： V1.0  
 */
public class UserFTradeDetailsVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 方案编号
	 */
	@SqlColumn(name="id")
	private String id;
	
	/**
	 * 申请时间
	 */
	@SqlColumn(name="app_time")
	private Long appTime;
	
	/**
	 * 手数
	 */
	@SqlColumn(name="tran_lever")
	private Integer tranLever;
	
	/**
	 * 操盘保证金
	 */
	@SqlColumn(name="trader_bond")
	private BigDecimal traderBond;
	
	/**
	 * 追加保证金
	 */
	@SqlColumn(name="append_trader_bond")
	private BigDecimal appendTraderBond;
	
	/**
	 * 总操盘金额
	 */
	@SqlColumn(name="trader_total")
	private BigDecimal traderTotal;
	
	/**
	 * 亏损平仓线
	 */
	@SqlColumn(name="line_loss")
	private BigDecimal lineLoss;
	
	/**
	 * 账号管理费
	 */
	@SqlColumn(name="fee_manage")
	private BigDecimal feeManage;
	
	/**
	 * 交易手续费(元/手【双向】)
	 */
	@SqlColumn(name="tran_fees")
	private BigDecimal tranFees;
	
	/**
	 * 交易账号
	 */
	@SqlColumn(name="tran_account")
	private String tranAccount;
	
	/**
	 * 交易密码
	 */
	@SqlColumn(name="tran_password")
	private String tranPassword;
	
	/**
	 * 结束时间
	 */
	@SqlColumn(name="end_time")
	private Long endtime;
	
	/**
	 * 交易盈亏(美元$)
	 */
	@SqlColumn(name="tran_profit_loss")
	private BigDecimal tranProfitLoss;
	
	/**
	 * 结算汇率(人民币￥)
	 */
	@SqlColumn(name="end_parities")
	private BigDecimal endParities;
	
	/**
	 * 交易总手续费
	 */
	@SqlColumn(name="tran_fees_total")
	private BigDecimal tranFeesTotal;
	
	/**
	 * 交易手数
	 */
	@SqlColumn(name="tran_actual_lever")
	private Integer tranActualLever;
	
	/**
	 * 国际原油交易手数
	 */
	@SqlColumn(name="crude_tran_actual_lever")
	private Integer crudeTranActualLever;
	
	/**
	 * 恒指期货交易手数
	 */
	@SqlColumn(name="hsi_tran_actual_lever")
	private Integer hsiTranActualLever;
	
	/**
	 * 迷你道指交易手数
	 */
	@SqlColumn(name="mdtran_actual_lever")
	private Integer mdtranActualLever;
	
	/**
	 * 迷你纳指交易手数
	 */
	@SqlColumn(name="mntran_actual_lever")
	private Integer mntranActualLever;
	
	/**
	 * 迷你标普交易手数
	 */
	@SqlColumn(name="mbtran_actual_lever")
	private Integer mbtranActualLever;
	
	/**
	 * 德国DAX交易手数
	 */
	@SqlColumn(name="daxtran_actual_lever")
	private Integer daxtranActualLever;
	
	/**
	 * 日经225交易手数
	 */
	@SqlColumn(name="nikkei_tran_actual_lever")
	private Integer nikkeiTranActualLever;
	
	/**
	 * 小恒指
	 */
	@SqlColumn(name="lhsi_tran_actual_lever")
	private Integer lhsiTranActualLever;
	
	/**
	 * 美黄金
	 * @return
	 */
	@SqlColumn(name="ag_tran_actual_lever")
	private Integer agTranActualLever;
	
	
	/**
	 * 结算金额
	 */
	@SqlColumn(name="end_amount")
	private BigDecimal endAmount;

	/**
	 * 状态   如：1：开户中;2：申请结算;3：待结算;4：操盘中;5：审核不通过;6：已结算;
	 */
	@SqlColumn(name="state_type")
	private Integer stateType;
	
	/**
	 * 业务类型 如：0：富时A50;6：国际原油;7：恒指期货;8：国际综合;
	 */
	@SqlColumn(name="business_type")
	private Integer businessType;
	
	/**
	 * 交易品种集合
	 */
	private List<OutDiskVo> outDiskVoList = new ArrayList<OutDiskVo>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getAppTime() {
		return appTime;
	}

	public void setAppTime(Long appTime) {
		this.appTime = appTime;
	}

	public Integer getTranLever() {
		return tranLever;
	}

	public void setTranLever(Integer tranLever) {
		this.tranLever = tranLever;
	}

	public BigDecimal getTraderBond() {
		return traderBond;
	}

	public void setTraderBond(BigDecimal traderBond) {
		this.traderBond = traderBond;
	}

	public BigDecimal getAppendTraderBond() {
		return appendTraderBond;
	}

	public void setAppendTraderBond(BigDecimal appendTraderBond) {
		this.appendTraderBond = appendTraderBond;
	}

	public BigDecimal getTraderTotal() {
		return traderTotal;
	}

	public void setTraderTotal(BigDecimal traderTotal) {
		this.traderTotal = traderTotal;
	}

	public BigDecimal getLineLoss() {
		return lineLoss;
	}

	public void setLineLoss(BigDecimal lineLoss) {
		this.lineLoss = lineLoss;
	}

	public BigDecimal getFeeManage() {
		return feeManage;
	}

	public void setFeeManage(BigDecimal feeManage) {
		this.feeManage = feeManage;
	}

	public BigDecimal getTranFees() {
		return tranFees;
	}

	public void setTranFees(BigDecimal tranFees) {
		this.tranFees = tranFees;
	}

	public String getTranAccount() {
		return tranAccount;
	}

	public void setTranAccount(String tranAccount) {
		this.tranAccount = tranAccount;
	}

	public String getTranPassword() {
		return tranPassword;
	}

	public void setTranPassword(String tranPassword) {
		this.tranPassword = tranPassword;
	}

	public Long getEndtime() {
		return endtime;
	}

	public void setEndtime(Long endtime) {
		this.endtime = endtime;
	}

	public BigDecimal getTranProfitLoss() {
		return tranProfitLoss;
	}

	public void setTranProfitLoss(BigDecimal tranProfitLoss) {
		this.tranProfitLoss = tranProfitLoss;
	}

	public BigDecimal getEndParities() {
		return endParities;
	}

	public void setEndParities(BigDecimal endParities) {
		this.endParities = endParities;
	}

	public BigDecimal getTranFeesTotal() {
		return tranFeesTotal;
	}

	public void setTranFeesTotal(BigDecimal tranFeesTotal) {
		this.tranFeesTotal = tranFeesTotal;
	}

	public Integer getTranActualLever() {
		return tranActualLever;
	}

	public void setTranActualLever(Integer tranActualLever) {
		this.tranActualLever = tranActualLever;
	}

	public Integer getCrudeTranActualLever() {
		return crudeTranActualLever;
	}

	public void setCrudeTranActualLever(Integer crudeTranActualLever) {
		this.crudeTranActualLever = crudeTranActualLever;
	}

	public Integer getHsiTranActualLever() {
		return hsiTranActualLever;
	}

	public void setHsiTranActualLever(Integer hsiTranActualLever) {
		this.hsiTranActualLever = hsiTranActualLever;
	}

	public Integer getMdtranActualLever() {
		return mdtranActualLever;
	}

	public void setMdtranActualLever(Integer mdtranActualLever) {
		this.mdtranActualLever = mdtranActualLever;
	}

	public Integer getMntranActualLever() {
		return mntranActualLever;
	}

	public void setMntranActualLever(Integer mntranActualLever) {
		this.mntranActualLever = mntranActualLever;
	}

	public Integer getMbtranActualLever() {
		return mbtranActualLever;
	}

	public void setMbtranActualLever(Integer mbtranActualLever) {
		this.mbtranActualLever = mbtranActualLever;
	}

	public Integer getDaxtranActualLever() {
		return daxtranActualLever;
	}

	public void setDaxtranActualLever(Integer daxtranActualLever) {
		this.daxtranActualLever = daxtranActualLever;
	}

	public Integer getNikkeiTranActualLever() {
		return nikkeiTranActualLever;
	}

	public void setNikkeiTranActualLever(Integer nikkeiTranActualLever) {
		this.nikkeiTranActualLever = nikkeiTranActualLever;
	}

	public BigDecimal getEndAmount() {
		return endAmount;
	}

	public void setEndAmount(BigDecimal endAmount) {
		this.endAmount = endAmount;
	}

	public Integer getStateType() {
		return stateType;
	}

	public void setStateType(Integer stateType) {
		this.stateType = stateType;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public List<OutDiskVo> getOutDiskVoList() {
		return outDiskVoList;
	}

	public void setOutDiskVoList(List<OutDiskVo> outDiskVoList) {
		this.outDiskVoList = outDiskVoList;
	}

	public Integer getLhsiTranActualLever() {
		return lhsiTranActualLever;
	}

	public void setLhsiTranActualLever(Integer lhsiTranActualLever) {
		this.lhsiTranActualLever = lhsiTranActualLever;
	}

	public Integer getAgTranActualLever() {
		return agTranActualLever;
	}

	public void setAgTranActualLever(Integer agTranActualLever) {
		this.agTranActualLever = agTranActualLever;
	}
	
	
}
