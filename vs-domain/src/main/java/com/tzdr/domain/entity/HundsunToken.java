package com.tzdr.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;

/**
 * 
 * @author Lin Feng 数据字典
 * @date 2014年12月26日
 */

@Entity
@Table(name = "w_hundsun_token")
public class HundsunToken extends BaseEntity {

	private static final long serialVersionUID = 838028999390597368L;

	private String account;
	
	private String token;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	
}