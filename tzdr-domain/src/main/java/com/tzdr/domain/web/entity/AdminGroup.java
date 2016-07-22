package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;


@Entity
@Table(name="w_admingroup")
public class AdminGroup  extends BaseEntity {

	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;

	private String title;
	private String leve;
	private short orders;



	@Column(name="title", nullable=false, length=100)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name="leve", nullable=false, length=65535)
	public String getLeve() {
		return this.leve;
	}

	public void setLeve(String leve) {
		this.leve = leve;
	}

	@Column(name="orders", nullable=false)
	public short getOrders() {
		return this.orders;
	}

	public void setOrders(short orders) {
		this.orders = orders;
	}
}