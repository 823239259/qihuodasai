package com.tzdr.domain.vo.activity;


import jodd.util.ObjectUtil;

import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.SqlColumn;

/**
 * 
 * <p>活动奖品</p>
 * @author WangPinQun
 * @see 
 * @version 1.0
 * 2016年01月08日下午13:47:24
 */
public class ActivityKudoWebVo {
	
	/**
	 * 活动类型：1-微信抽奖；2-web抽奖（开箱壕礼）；3-新春礼包
	 */
	@SqlColumn(name="activity_type")
	private Integer activityType;

	/**
	 * 用户ID
	 */
	@SqlColumn(name="user_id")
	private String userId;
	
	/**
	 * 奖品编号
	 */
	@SqlColumn(name="id")
	private String id;
	
	/**
	 * 奖品名称
	 */
	@SqlColumn(name="kudo_name")
	private String kudoName;

	/**
	 * 奖品类型：1-实物，2-红包，3-抵扣券，4-免单，5-折扣券，6-黑名单折扣券，7-新春大礼包
	 */
	@SqlColumn(name="kudo_type")
	private Integer kudoType;

	/**
	 * 奖品状态：0-未获得；1-已获得/立即使用；2-已使用/已寄出；3-申请使用；默认0
	 */
	@SqlColumn(name="kudo_status")
	private Integer kudoStatus;
	
	/**
	 * 奖品状态字符串
	 */
	private String kudoStatusStr;
	
	/**
	 * 奖品获得时间
	 */
	@SqlColumn(name="kudo_get_time")
	private Long kudoGetTime;
	
	/**
	 * 金额
	 */
	@SqlColumn(name="kudo_money")
	private double kudoMoney;
	
	/**
	 * 使用条件
	 */
	@SqlColumn(name="kudo_use_condition")
	private String kudoUseCondition;

	public Integer getActivityType() {
		return activityType;
	}

	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKudoName() {
		return kudoName;
	}

	public void setKudoName(String kudoName) {
		this.kudoName = kudoName;
	}

	public Integer getKudoType() {
		return kudoType;
	}

	public void setKudoType(Integer kudoType) {
		this.kudoType = kudoType;
	}

	public Integer getKudoStatus() {
		return kudoStatus;
	}

	public void setKudoStatus(Integer kudoStatus) {
		this.kudoStatus = kudoStatus;
	}

	public String getKudoStatusStr() {
		if(this.kudoStatus == null){
			this.kudoStatusStr = "";
		}else if(this.kudoStatus == 0){
			this.kudoStatusStr = "未获得";
		}else if(this.kudoStatus == 1){
			this.kudoStatusStr = "已获得";
		}else if(this.kudoStatus == 2){ 
			this.kudoStatusStr = this.kudoType == 1 ? "已寄出" : "已使用";
		}
		return kudoStatusStr;
	}

	public void setKudoStatusStr(String kudoStatusStr) {
		this.kudoStatusStr = kudoStatusStr;
	}

	public String getKudoGetTime() {
		if (ObjectUtil.equals(null,this.kudoGetTime)){
			return "";
		}
		return Dates.format(Dates.parseLong2Date(this.kudoGetTime), Dates.CHINESE_DATE_FORMAT_LINE);
	}

	public void setKudoGetTime(Long kudoGetTime) {
		this.kudoGetTime = kudoGetTime;
	}

	public double getKudoMoney() {
		return kudoMoney;
	}

	public void setKudoMoney(double kudoMoney) {
		this.kudoMoney = kudoMoney;
	}

	public String getKudoUseCondition() {
		return kudoUseCondition;
	}

	public void setKudoUseCondition(String kudoUseCondition) {
		this.kudoUseCondition = kudoUseCondition;
	}
}
