package com.tzdr.domain.vo;

import java.io.Serializable;

import jodd.util.StringUtil;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.web.entity.VolumeDetail;

/**
 * 
 * 
 * <p></p>
 * @author LiuQing
 * @see 卷详细
 * @version 2.0
 * 2015年4月28日上午9:35:17
 */
public class VolumeDetailVo implements Serializable {
	
	private static final long serialVersionUID = -8591246450899980591L;
	
	@SqlColumn
	
	private String id;
	
	/**
	 * 券编号
	 */
	@SqlColumn
	@AllowExcel(name="券编号")
	private String code;
	
	/**
	 * 名称
	 */
	@SqlColumn
	@AllowExcel(name="名称")
	private String name;
	
	/**
	 * 类型
	 */
	@SqlColumn
	private String type;
	
	@AllowExcel(name="类型")
	private String typeStr;
	
	/**
	 * 使用方式
	 */
	@SqlColumn(name="use_type")
	private String useType;
	
	@AllowExcel(name="使用方式")
	private String useTypeStr;
	
	/**
	 * 券金额
	 */
	@SqlColumn(name="money")
	@AllowExcel(name="券金额")
	private Double volumeMoney;
	
	/**
	 * 实际抵扣金额
	 */
	@SqlColumn(name="real_pay_amount")
	@AllowExcel(name="抵扣金额")
	private Double realPayAmount;
	
	/**
	 * 状态
	 */
	@SqlColumn(name="use_state")
	private Integer useState;
	
	@AllowExcel(name="状态")
	private String useStateStr;
	/** 
	 * 使用时间
	 */
	@SqlColumn(name="use_date_value")
	private Integer useDateValue;
	
	@AllowExcel(name="使用日期")
	private String useDateValueStr;
	
	/**
	 * 截止日期
	 */
	@SqlColumn(name="end_date_value")
	
	private Integer endDateValue;
	
	/**
	 * 用户id
	 */
	@SqlColumn(name="uid")
	private String useUserid;
	

	/**
	 * 使用人
	 */
	@SqlColumn(name="use_username")
	@AllowExcel(name="使用人")
	private String useUsername;
	
	/**
	 * 备注
	 */
	@SqlColumn
	@AllowExcel(name="备注")
	private String remark;
	
	
	
	public String getUseDateValueStr() {
		if (this.useDateValue != null) {
			this.useDateValueStr = TypeConvert.long1000ToDateStr(this.useDateValue.longValue());
		}
		return useDateValueStr;
	}

	public void setUseDateValueStr(String useDateValueStr) {
		this.useDateValueStr = useDateValueStr;
	}

	public String getTypeStr() {
		if (this.type != null) {
			return CacheManager.getDataMapByKey(VolumeDeductibleVo.VOLUME_TYPE,this.type);
		}
		return typeStr;
	}
	
    public String getUseTypeStr() {
		if (this.useType != null) {
			return CacheManager.getDataMapByKey(VolumeDeductibleVo.VOLUME_USE_TYPE,this.useType);
		}
		return useTypeStr;
	}

	public void setUseTypeStr(String useTypeStr) {
		this.useTypeStr = useTypeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public Double getVolumeMoney() {
		return volumeMoney;
	}

	public void setVolumeMoney(Double volumeMoney) {
		this.volumeMoney = volumeMoney;
	}

	public Double getRealPayAmount() {
		return realPayAmount;
	}

	public void setRealPayAmount(Double realPayAmount) {
		this.realPayAmount = realPayAmount;
	}

	public Integer getUseDateValue() {
		return useDateValue;
	}

	public Integer getUseState() {
		return useState;
	}

	public void setUseState(Integer useState) {
		this.useState = useState;
	}

	public String getUseStateStr() {
		if (StringUtil.isNotBlank(this.useState.toString())) {
			if(this.useState==VolumeDetail.USE_STATE_USED){
				return "已使用";
			}else if(this.useState==VolumeDetail.USE_STATE_USE){
				if(TypeConvert.dbDefaultDate() >this.endDateValue.longValue()){
					return "已过期";
				}else if(StringUtil.isBlank(this.useUserid)){
					return "未发放";
				}else{
					return "未使用";
				}
			}
		}
		
		return useStateStr;
	}

	public void setUseStateStr(String useStateStr) {
		this.useStateStr = useStateStr;
	}

	public void setUseDateValue(Integer useDateValue) {
		this.useDateValue = useDateValue;
	}

	public String getUseUsername() {
		return useUsername;
	}

	public void setUseUsername(String useUsername) {
		this.useUsername = useUsername;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public Integer getEndDateValue() {
		return endDateValue;
	}

	public void setEndDateValue(Integer endDateValue) {
		this.endDateValue = endDateValue;
	}
	public String getUseUserid() {
		return useUserid;
	}

	public void setUseUserid(String useUserid) {
		this.useUserid = useUserid;
	}
}
