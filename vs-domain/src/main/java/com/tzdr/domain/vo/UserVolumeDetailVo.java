package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigInteger;

/**
* @Description: 用户劵信息VO类
* @ClassName: UserTradeVo
* @author wangpinqun
* @date 2015年4月30日 下午3:39:20
 */
public class UserVolumeDetailVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * id
	 */
	private String id;

	/**
	 * 编号
	 */
	private String code;
	
	/**
	 * 面值
	 */
	private Double money;
	
	/**
	 * 劵名称
	 */
	private String name;
	
	/**
	 * 使用方式编号
	 */
	private String useType;
	
	/**
	 * 使用方式名称
	 */
	private String useTypeName;
	
	/**
	 * 启用日期
	 */
	private BigInteger startupDateValue;
	
	/**
	 * 截止日期
	 */
	private BigInteger endDateValue;
	
	/**
	 * 使用时间
	 */
	private BigInteger userDateValue;
	
	/**
	 * 获取抵扣卷的时间
	 */
	private BigInteger timeValueOfGetVolume;
	
	/**
	 * 1:已使用、-1未使用、2：已过期
	 */
	private BigInteger useState;
	
	/**
	 * 用户编号
	 */
	private String uid;
	
	/**
	 * 备注
	 */
	private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public String getUseTypeName() {
		return useTypeName;
	}

	public void setUseTypeName(String useTypeName) {
		this.useTypeName = useTypeName;
	}

	public BigInteger getStartupDateValue() {
		return startupDateValue;
	}

	public void setStartupDateValue(BigInteger startupDateValue) {
		this.startupDateValue = startupDateValue;
	}

	public BigInteger getEndDateValue() {
		return endDateValue;
	}

	public void setEndDateValue(BigInteger endDateValue) {
		this.endDateValue = endDateValue;
	}

	public BigInteger getUserDateValue() {
		return userDateValue;
	}

	public void setUserDateValue(BigInteger userDateValue) {
		this.userDateValue = userDateValue;
	}

	public BigInteger getTimeValueOfGetVolume() {
		return timeValueOfGetVolume;
	}

	public void setTimeValueOfGetVolume(BigInteger timeValueOfGetVolume) {
		this.timeValueOfGetVolume = timeValueOfGetVolume;
	}

	public BigInteger getUseState() {
		return useState;
	}

	public void setUseState(BigInteger useState) {
		this.useState = useState;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
