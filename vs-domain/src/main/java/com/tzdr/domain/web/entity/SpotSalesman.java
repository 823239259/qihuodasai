package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;

/**
 * 现货销售员
 * @ClassName Salesman
 * @author L.Y
 * @email liuyuan@peigubao.com
 * @date 2015年10月8日
 */
@Entity
@Table(name = "s_spot_salesman")
public class SpotSalesman extends BaseCrudEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -266835027497171889L;

	/**
	 * 销售姓名
	 */
	@Column(name = "name", nullable = false)
	private String name;
	
	/**
	 * 手机号码
	 */
	@Column(name = "mobile", nullable = false)
	private String mobile;
	
	/**
	 * 计数
	 */
	@Column(name = "ctn", nullable = false)
	private Integer ctn;
	
	/**
	 * 备注（暂未使用）
	 */
	@Column(name = "remark")
	private String remark;
	
	public SpotSalesman() {
	}

	public SpotSalesman(String name, String mobile) {
		super();
		this.name = name;
		this.mobile = mobile;
	}

	public SpotSalesman(String name, String mobile, Integer ctn) {
		super();
		this.name = name;
		this.mobile = mobile;
		this.ctn = ctn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getCtn() {
		return ctn;
	}

	public void setCtn(Integer ctn) {
		this.ctn = ctn;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}