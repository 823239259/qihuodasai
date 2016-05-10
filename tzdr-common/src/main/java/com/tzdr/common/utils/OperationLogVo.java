package com.tzdr.common.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see 操作日志
 * @version 2.0
 * 2015年3月6日上午9:23:05
 */
public class OperationLogVo implements Serializable {

	private static final long serialVersionUID = -1692679077436920929L;
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 操作人
	 */
	private String operationPeople;
	/**
	 * 账户
	 */
	private String account;
	
	/**
	 * 其它显示内容
	 */
	private List<String> data = new ArrayList<String>();
	
	
	public void add(String value) {
		this.data.add(value);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOperationPeople() {
		return operationPeople;
	}

	public void setOperationPeople(String operationPeople) {
		this.operationPeople = operationPeople;
	}

	public List<String> getData() {
		return data;
	}

	public void setData(List<String> data) {
		this.data = data;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

}
