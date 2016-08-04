package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.tzdr.common.domain.BaseEntity;

@Entity
@Table(name="w_generalize_visit")
public class GeneralizeVisit  extends BaseEntity {
	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	
	private String clieantIp;
	
	private Long createdate;
	
	private String url;
	
	private String param;
	
	private String city;
	
	private String generalizeId;
	
	private String activity;
	@Column(name="clieant_ip")
	public String getClieantIp() {
		return this.clieantIp;
	}

	public void setClieantIp(String clieantIp) {
		this.clieantIp = clieantIp;
	}

	@Column(name="createdate")
	public Long getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Long createdate) {
		this.createdate = createdate;
	}

	@Column(name="url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name="param")
	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	@Column(name="city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "generalize_id")
	public String getGeneralizeId() {
		return generalizeId;
	}

	public void setGeneralizeId(String generalizeId) {
		this.generalizeId = generalizeId;
	}
	@Column(name="activity")
	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}
	
}