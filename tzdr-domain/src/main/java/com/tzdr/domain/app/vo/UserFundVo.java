package com.tzdr.domain.app.vo;

import java.io.Serializable;

import com.tzdr.common.utils.SqlColumn;

/**  
 * @Title: UserFundVo.java     
 * @Description: 用户资金明细信息VO    
 * @author： WangPinQun 
 * @E-mail：wangpinqun@tzdr.com
 * @company： 上海信闳投资管理有限公司重庆分公司
 * @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
 * @date： 2016年3月24日 上午10:08:01    
 * @version： V1.0  
 */
public class UserFundVo implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 提交时间
	 */
	@SqlColumn(name="addtime")
	private Long subTime;
	
	/**
	 * 类型：1:充值,2:提现,,3:系统调账,4:系统冲账,5:投标冻结,6:投标成功,10:配资支出,11:利息支出 ,
	 * 12：扣取管理费（新增），13佣金收入，15配资撤回,16利润提取17 追加保证金、18配资欠费、19活动赠送、20活动收回、21补偿收入
	 * ,23提现撤回,24 抵扣劵收入 25:配资管理费撤回  26:配资利息撤回 27:补仓欠费 30:内转划入 31：内转划出 
	 */
	@SqlColumn(name="type")
	private Integer type;
	
	/**
	 * 金额
	 */
	@SqlColumn(name="money")
	private Double money;
	
	/**
	 * 支付时间
	 */
	@SqlColumn(name="uptime")
	private Long payTime;
	
	/**
	 * 支付状态  如： 0：未支付；1：已支付
	 */
	@SqlColumn(name="pay_status")
	private Integer payStatus;
	
	/**
	 * 方案组合号
	 */
	@SqlColumn(name="lid")
	private String lid;
	
	/**
	 * 方案号
	 */
	@SqlColumn(name="rid")
	private String rid;
	
	/**
	 * 描述
	 */
	@SqlColumn(name="remark")
	private String remark;

	public Long getSubTime() {
		return subTime;
	}

	public void setSubTime(Long subTime) {
		this.subTime = subTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Long getPayTime() {
		return payTime;
	}

	public void setPayTime(Long payTime) {
		this.payTime = payTime;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getLid() {
		return lid;
	}

	public void setLid(String lid) {
		this.lid = lid;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
