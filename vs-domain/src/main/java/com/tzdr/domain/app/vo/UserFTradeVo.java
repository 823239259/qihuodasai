package com.tzdr.domain.app.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import com.tzdr.common.utils.SqlColumn;

/**  
 * @Title: FTradeVo.java     
 * @Description: 用户期货产品方案信息VO    
 * @author： WangPinQun 
 * @E-mail：wangpinqun@tzdr.com
 * @company： 上海信闳投资管理有限公司重庆分公司
 * @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
 * @date： 2016年3月22日 上午9:49:05    
 * @version： V1.0  
 */
public class UserFTradeVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 方案编号
	 */
	@SqlColumn(name="id")
	private String id;
	
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
	 * 手数
	 */
	@SqlColumn(name="tran_lever")
	private Integer tranLever;
	
	/**
	 * 申请时间
	 */
	@SqlColumn(name="app_time")
	private Long appTime;
	
	/**
	 * 状态   如：1：开户中;2：申请结算;3：待结算;4：操盘中;5：审核不通过;6：已结算;
	 */
	@SqlColumn(name="state_type")
	private Integer stateType;
	
	/**
	 * 业务类型 如：0：富时A50;6：国际原油;7：恒指期货;8：国际综合;9:小恒指;
	 */
	@SqlColumn(name="business_type")
	private Integer businessType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Integer getTranLever() {
		return tranLever;
	}

	public void setTranLever(Integer tranLever) {
		this.tranLever = tranLever;
	}

	public Long getAppTime() {
		return appTime;
	}

	public void setAppTime(Long appTime) {
		this.appTime = appTime;
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
}
