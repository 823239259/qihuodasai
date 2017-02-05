package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;


@Entity
@Table(name="w_score_list")
public class ScoreList   extends BaseEntity {


	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;

	private String uid;
	private String lid;
	private String plid;
	private double points;
	private double avlscoree;
	private double score;
	private Integer addtime;
	private Integer uptime;
	private short status;
	private String remark;


	@Column(name="uid", length=32)
	public String getUid() {
		return this.uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Column(name="lid", length=32)
	public String getLid() {
		return this.lid;
	}

	public void setLid(String lid) {
		this.lid = lid;
	}

	@Column(name="plid", length=32)
	public String getPlid() {
		return this.plid;
	}

	public void setPlid(String plid) {
		this.plid = plid;
	}

	@Column(name="points", nullable=false, precision=10)
	public double getPoints() {
		return this.points;
	}

	public void setPoints(double points) {
		this.points = points;
	}

	@Column(name="avlscoree", nullable=false, precision=10)
	public double getAvlscoree() {
		return this.avlscoree;
	}

	public void setAvlscoree(double avlscoree) {
		this.avlscoree = avlscoree;
	}

	@Column(name="score", nullable=false, precision=10)
	public double getScore() {
		return this.score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	@Column(name="addtime", nullable=false)
	public Integer getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Integer addtime) {
		this.addtime = addtime;
	}

	@Column(name="uptime", nullable=false)
	public Integer getUptime() {
		return this.uptime;
	}

	public void setUptime(Integer uptime) {
		this.uptime = uptime;
	}

	@Column(name="status", nullable=false)
	public short getStatus() {
		return this.status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	@Column(name="remark", nullable=false)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}