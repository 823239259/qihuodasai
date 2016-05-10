package com.tzdr.domain.vo;

import java.io.Serializable;

import com.tzdr.common.utils.AllowExcel;

/**
 * 现货预约
 * @ClassName SpotBookingVo
 * @author L.Y
 * @email liuyuan@peigubao.com
 * @date 2015年10月8日
 */
public class SpotBookingVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6111298899983253950L;

	/**
	 * 预约时间(格式化)
	 */
	@AllowExcel(name = "预约时间")
	private String createTime;
	
	/**
	 * 真实姓名
	 */
	@AllowExcel(name = "真实姓名")
	private String autonym;
	
	/**
	 * 手机号码
	 */
	@AllowExcel(name = "手机号码")
	private String mobile;
	
	/**
	 * 销售姓名
	 */
	@AllowExcel(name = "销售姓名")
	private String salesmanName;
	
	/**
	 * 销售电话
	 */
	@AllowExcel(name = "销售电话")
	private String salesmanMobile;

	public SpotBookingVo() {
	}
	
	public SpotBookingVo(String createTime, String autonym, String mobile,
			String salesmanName, String salesmanMobile) {
		super();
		this.createTime = createTime;
		this.autonym = autonym;
		this.mobile = mobile;
		this.salesmanName = salesmanName;
		this.salesmanMobile = salesmanMobile;
	}
	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getAutonym() {
		return autonym;
	}

	public void setAutonym(String autonym) {
		this.autonym = autonym;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSalesmanName() {
		return salesmanName;
	}

	public void setSalesmanName(String salesmanName) {
		this.salesmanName = salesmanName;
	}

	public String getSalesmanMobile() {
		return salesmanMobile;
	}

	public void setSalesmanMobile(String salesmanMobile) {
		this.salesmanMobile = salesmanMobile;
	}
}