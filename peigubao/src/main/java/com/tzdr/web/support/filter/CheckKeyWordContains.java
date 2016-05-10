package com.tzdr.web.support.filter;

/**
 * 用于安全过滤验证是否包含关键符号 返回值
 * @author zhaoliang
 *
 */
public class CheckKeyWordContains {
	//是否包含关键字
	private boolean contains;
	
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isContains() {
		return contains;
	}

	public void setContains(boolean contains) {
		this.contains = contains;
	}
}
