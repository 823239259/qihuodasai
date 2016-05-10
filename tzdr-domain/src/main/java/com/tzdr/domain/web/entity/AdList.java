package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;


@Entity
@Table(name="w_ad_list")
public class AdList extends BaseEntity{

	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	
	private String pid;
	private short adtype;
	private String adname;
	private String adlink;
	private String adtitle;
	private String adcode;
	private long startdate;
	private long enddate;
	private Integer clinkcount;
	private short isactive;
	private Integer createdate;
	private Integer type;


	@Column(name="pid", length=32)
	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Column(name="adtype", nullable=false)
	public short getAdtype() {
		return this.adtype;
	}

	public void setAdtype(short adtype) {
		this.adtype = adtype;
	}

	@Column(name="adname", nullable=false)
	public String getAdname() {
		return this.adname;
	}

	public void setAdname(String adname) {
		this.adname = adname;
	}

	@Column(name="adlink", nullable=false)
	public String getAdlink() {
		return this.adlink;
	}

	public void setAdlink(String adlink) {
		this.adlink = adlink;
	}

	@Column(name="adtitle", nullable=false)
	public String getAdtitle() {
		return this.adtitle;
	}

	public void setAdtitle(String adtitle) {
		this.adtitle = adtitle;
	}

	@Column(name="adcode", nullable=false, length=65535)
	public String getAdcode() {
		return this.adcode;
	}

	public void setAdcode(String adcode) {
		this.adcode = adcode;
	}

	@Column(name="startdate", nullable=false)
	public long getStartdate() {
		return this.startdate;
	}

	public void setStartdate(long startdate) {
		this.startdate = startdate;
	}

	@Column(name="enddate", nullable=false)
	public long getEnddate() {
		return this.enddate;
	}

	public void setEnddate(long enddate) {
		this.enddate = enddate;
	}

	@Column(name="clinkcount", nullable=false)
	public Integer getClinkcount() {
		return this.clinkcount;
	}

	public void setClinkcount(Integer clinkcount) {
		this.clinkcount = clinkcount;
	}

	@Column(name="isactive", nullable=false)
	public short getIsactive() {
		return this.isactive;
	}

	public void setIsactive(short isactive) {
		this.isactive = isactive;
	}

	@Column(name="createdate", nullable=false)
	public Integer getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Integer createdate) {
		this.createdate = createdate;
	}

	@Column(name="type", nullable=false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}