package com.tzdr.domain.vo;

import java.io.Serializable;

import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.SqlOrder;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.web.entity.VolumeDeductible;

/**
 * 
 * <p></p>
 * @author LiuQing
 * @see 
 * @version 2.0
 * 2015年4月28日上午9:35:17
 */
public class VolumeDeductibleVo implements Serializable {
	
	private static final long serialVersionUID = -8591246450899980591L;
	
	@SqlColumn
	private String id;
	
	@SqlColumn
	@SqlOrder
	private String name;
	
	/**
	 * 类型
	 */
	@SqlColumn
	private String type;
	
	@SqlOrder("type")
	private String typeStr;
	
	/**
	 * 类型编号
	 */
	@SqlColumn(name="type_code")
	@SqlOrder("type_code")
	private String typeCode;
	
	/**
	 * 使用方式
	 */
	@SqlColumn(name="use_type")
	private String useType;
	
	@SqlOrder("use_type")
	private String useTypeStr;
	
	/**
	 * 金额
	 */
	@SqlColumn
	@SqlOrder
	private double money;
	
	/**
	 * 发行张数
	 */
	@SqlColumn(name="release_num")
	@SqlOrder("release_num")
	private int releaseNum;
	
	/**
	 * 使用张数
	 */
	@SqlColumn(name="use_num")
	@SqlOrder("use_num")
	private int useNum;
	
	/**
	 * 启用日期
	 */
	@SqlColumn(name="startup_date_value")
	private Integer startupDateValue;
	
	@SqlOrder("startup_date_value")
	private String startupDateValueStr;
	
	@SqlOrder("startup_date_value")
	private String startupDateValueTimeStr;
	
	
	/**
	 * 发放结束日期
	 */
	@SqlColumn(name="deal_start_date_value")
	private Long dealStartDateValue;
	
	
	/**
	 * 发放结束日期
	 */
	@SqlColumn(name="deal_end_date_value")
	private Long dealEndDateValue;
	
	@SqlOrder("deal_end_date_value")
	private String dealEndDateValueStr;
	
	@SqlOrder("deal_start_date_value")
	private String dealStartDateValueStr;
	
	@SqlOrder("deal_end_date_value")
	private String dealEndDateNotTimeValueStr;
	
	@SqlOrder("deal_start_date_value")
	private String dealStartDateNotTimeValueStr;
	
	/**
	 * 截止日期
	 */
	@SqlColumn(name="end_date_value")
	private Integer endDateValue;
	
	@SqlOrder("end_date_value")
	private String endDateValueStr;
	
	@SqlOrder("end_date_value")
	private String endDateValueTimeStr;
	
	/**
	 * 截止天数
	 */
	@SqlColumn(name="end_day_value")
	private Integer endDayValue;
	
	@SqlOrder("end_day_value")
	private String endDayValueStr;
	
	/**
	 * 截至期限类型：0：截止日期、1：使用周期
	 */
	@SqlColumn(name="deadline_type")
	private int deadlineType;
	
	/**
	 * 状态 1：启用、-1：停用
	 */
	@SqlColumn(name="state_value")
	private int stateValue;
	
	@SqlOrder("state_value")
	private String stateValueStr;
	
	/**
	 * 0:限制、1：不限制
	 */
	@SqlColumn(name="limit_type")
	@SqlOrder("limit_type")
	private int limitType;
	
	@SqlColumn
	@SqlOrder
	private String createUserName;
	
	@SqlColumn(name="create_time")
	private Long createTime;
	
	@SqlOrder("create_time")
	private String createTimeStr;
	
	
	/**
	 * 获取实例
	 * @return VolumeDeductible
	 */
	public static VolumeDeductible getEntity(VolumeDeductibleVo vo) {
		VolumeDeductible volume = new VolumeDeductible();
		//类型
		volume.setType(vo.type);
		volume.setUseType(vo.useType);
		volume.setName(vo.name);
		volume.setMoney(vo.money);
		volume.setReleaseNum(vo.releaseNum);
		volume.setStartupDateValue(
				TypeConvert.strToDatetime1000Long(vo.startupDateValueStr));
		volume.setDeadlineType(vo.deadlineType);
		
		volume.setDealStartDateValue(TypeConvert.strToDatetime1000Long(vo.dealStartDateValueStr));
		volume.setDealEndDateValue(TypeConvert.strToDatetime1000Long(vo.dealEndDateValueStr));
		
		if (vo.deadlineType == 0) {
			volume.setEndDateValue(TypeConvert.strToDatetime1000Long(vo.endDateValueStr));
		}
		else {
			volume.setEndDayValue(vo.endDayValue);
			volume.setEndDateValue(
					TypeConvert.strToZeroTime1000ByPattern("yyyy-MM-dd HH:mm:ss",vo.startupDateValueStr,
							vo.endDayValue,-1));
		}
		volume.setCreateTime(TypeConvert.dbDefaultDate());
		return volume;
	}
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public int getReleaseNum() {
		return releaseNum;
	}

	public void setReleaseNum(int releaseNum) {
		this.releaseNum = releaseNum;
	}

	public int getUseNum() {
		return useNum;
	}

	public void setUseNum(int useNum) {
		this.useNum = useNum;
	}

	public Integer getStartupDateValue() {
		return startupDateValue;
	}

	public void setStartupDateValue(Integer startupDateValue) {
		this.startupDateValue = startupDateValue;
	}

	public Integer getEndDateValue() {
		return endDateValue;
	}

	public void setEndDateValue(Integer endDateValue) {
		this.endDateValue = endDateValue;
	}

	public Integer getEndDayValue() {
		return endDayValue;
	}

	public void setEndDayValue(Integer endDayValue) {
		this.endDayValue = endDayValue;
	}

	public int getDeadlineType() {
		return deadlineType;
	}

	public void setDeadlineType(int deadlineType) {
		this.deadlineType = deadlineType;
	}

	public int getStateValue() {
		return stateValue;
	}

	public void setStateValue(int stateValue) {
		this.stateValue = stateValue;
	}

	public int getLimitType() {
		return limitType;
	}

	public void setLimitType(int limitType) {
		this.limitType = limitType;
	}

	public String getTypeStr() {
		if (this.type != null) {
			return CacheManager.getDataMapByKey(VOLUME_TYPE,this.type);
		}
		return typeStr;
	}
	
	/**
	 * 抵扣卷类型
	 */
	public static final String VOLUME_TYPE = "volumeType";
	
	/**
	 * 使用方式
	 */
	public static final String VOLUME_USE_TYPE = "volumeUseType";
	
	/**
	 * 状态
	 */
	public static final String VOLUME_STATE_TYPE = "volumeStateType";

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	public String getUseTypeStr() {
		
		if (this.useType != null) {
			return CacheManager.getDataMapByKey(VOLUME_USE_TYPE,this.useType);
		}
		return useTypeStr;
	}

	public void setUseTypeStr(String useTypeStr) {
		this.useTypeStr = useTypeStr;
	}

	public String getStartupDateValueStr() {
		if (this.startupDateValue != null) {
			this.startupDateValueStr = TypeConvert.long1000ToDateStr(this.startupDateValue.longValue());
		}
		return startupDateValueStr;
	}

	public void setStartupDateValueStr(String startupDateValueStr) {
		this.startupDateValueStr = startupDateValueStr;
	}

	public String getEndDateValueStr() {
		if (this.endDateValue != null && deadlineType != 1) {
			this.endDateValueStr = TypeConvert.long1000ToDateStr(this.endDateValue.longValue());
		}
		return endDateValueStr;
	}

	public void setEndDateValueStr(String endDateValueStr) {
		this.endDateValueStr = endDateValueStr;
	}

	public String getStateValueStr() {
		
		if (this.stateValue != 0) {
			this.stateValueStr = CacheManager.getDataMapByKey(VOLUME_STATE_TYPE,this.stateValue + "");
		}
		return stateValueStr;
	}

	public void setStateValueStr(String stateValueStr) {
		this.stateValueStr = stateValueStr;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getCreateTimeStr() {
		if (this.createTime != null) {
			this.createTimeStr = TypeConvert.long1000ToDateStr(this.createTime);
		}
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getEndDayValueStr() {
		if (this.endDayValue != null) {
			this.endDayValueStr = this.endDayValue + "天";
		}
		return endDayValueStr;
	}

	public void setEndDayValueStr(String endDayValueStr) {
		this.endDayValueStr = endDayValueStr;
	}

	public String getStartupDateValueTimeStr() {
		
		if (this.startupDateValue != null) {
			this.startupDateValueTimeStr = TypeConvert.long1000ToDatetimeStr(this.startupDateValue.longValue());
		}
		return startupDateValueTimeStr;
	}

	public void setStartupDateValueTimeStr(String startupDateValueTimeStr) {
		this.startupDateValueTimeStr = startupDateValueTimeStr;
	}

	public String getEndDateValueTimeStr() {
		if (this.endDateValue != null) {
			this.endDateValueTimeStr = TypeConvert.long1000ToDatetimeStr(this.endDateValue.longValue());
		}
		return endDateValueTimeStr;
	}

	public void setEndDateValueTimeStr(String endDateValueTimeStr) {
		this.endDateValueTimeStr = endDateValueTimeStr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getDealStartDateValue() {
		return dealStartDateValue;
	}

	public void setDealStartDateValue(Long dealStartDateValue) {
		this.dealStartDateValue = dealStartDateValue;
	}

	public String getDealStartDateValueStr() {
		return dealStartDateValueStr;
	}

	public void setDealStartDateValueStr(String dealStartDateValueStr) {
		this.dealStartDateValueStr = dealStartDateValueStr;
	}

	public Long getDealEndDateValue() {
		if (this.dealEndDateValue != null) {
			this.dealEndDateValueStr = TypeConvert.long1000ToDatetimeStr(dealEndDateValue);
		}
		return dealEndDateValue;
	}

	public void setDealEndDateValue(Long dealEndDateValue) {
		this.dealEndDateValue = dealEndDateValue;
	}

	public String getDealEndDateValueStr() {
		if (this.dealStartDateValue != null) {
			this.dealStartDateValueStr = TypeConvert.long1000ToDatetimeStr(dealStartDateValue);
		}
		return dealEndDateValueStr;
	}

	public void setDealEndDateValueStr(String dealEndDateValueStr) {
		this.dealEndDateValueStr = dealEndDateValueStr;
	}

	public String getDealEndDateNotTimeValueStr() {
		if (this.dealEndDateValue != null) {
			this.dealEndDateNotTimeValueStr = TypeConvert.long1000ToDateStr(dealEndDateValue);
		}
		return dealEndDateNotTimeValueStr;
	}

	public void setDealEndDateNotTimeValueStr(String dealEndDateNotTimeValueStr) {
		this.dealEndDateNotTimeValueStr = dealEndDateNotTimeValueStr;
	}

	public String getDealStartDateNotTimeValueStr() {
		if (this.dealStartDateValue != null) {
			this.dealStartDateNotTimeValueStr = TypeConvert.long1000ToDateStr(dealStartDateValue);
		}
		return dealStartDateNotTimeValueStr;
	}

	public void setDealStartDateNotTimeValueStr(String dealStartDateNotTimeValueStr) {
		this.dealStartDateNotTimeValueStr = dealStartDateNotTimeValueStr;
	}
	
	

}
