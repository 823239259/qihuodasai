package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tzdr.common.domain.BaseCrudEntity;
import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.SqlColumn;

@Entity
@Table(name="identity_card_history")
public class IdentityCardHistory  extends BaseCrudEntity {
	
	private static final long serialVersionUID = -6469817179539585307L;
	
	/**
	 * 身份证号码
	 */
	@SqlColumn(name="id_card")
	@AllowExcel(name="身份证号码")
	private String idCard;
	
	/**
	 * 姓名
	 */
	
	@SqlColumn(name="name")
	@AllowExcel(name="姓名")
	private String  name;
	
	/**
	 * 性别
	 */
	@SqlColumn(name="sex")
	@AllowExcel(name="性别")
	private String  sex;
	
	/**
	 * 生日
	 */
	@SqlColumn(name="birthday")
	@AllowExcel(name="生日")
	private String  birthday;
	
	/**
	 * 认证结果
	 */
	@SqlColumn(name="result")
	@AllowExcel(name="认证结果")
	private String result;
	
	/**
	 * 认证时间
	 */
	@Transient
	@SqlColumn(name="create_time_string")
	@AllowExcel(name="认证时间")
	private String createTimeString;
	
	

	public String getCreateTimeString() {
		return createTimeString;
	}

	public void setCreateTimeString(String createTimeString) {
		this.createTimeString = createTimeString;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
	
}
