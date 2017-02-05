package com.tzdr.domain.web.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tzdr.common.domain.BaseCrudEntity;
import com.tzdr.common.domain.BaseEntity;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;

/**
 * 过户费差，佣金差
 * @author Administrator
 *
 */
@Entity
@Table(name="w_free_diff")
public class FreeDiff   extends BaseCrudEntity {
	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;

	private double money;//金额
	
	private String account;//恒生账号
	
	private String type;//类型
	
	@Transient
	private String createdate;//创建时间
	private Long addtime;//chang时间
	@Transient 
	private String typevalue;
	public String getTypevalue() {
		 return CacheManager.getDataMapByKey(DataDicKeyConstants.FREETYPE, String.valueOf(this.type));
	}

	public void setTypevalue(String typevalue) {
		this.typevalue = typevalue;
	}

	public Long getAddtime() {
		return addtime;
	}

	public void setAddtime(Long addtime) {
		this.addtime = addtime;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.addtime=Dates.parse(createdate,Dates.CHINESE_DATE_FORMAT_LINE).getTime()/1000;
		this.createdate = createdate;
	}

	
}