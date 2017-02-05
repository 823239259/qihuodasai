package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigInteger;

import com.tzdr.common.utils.SqlColumn;

/**
 * 配资用户统计
 * @author 张军
 *
 */
public class UserTradeSummaryCmsVo  implements Serializable {

	
	@SqlColumn
	private String uid;
	@SqlColumn
	private Long starttime;
	@SqlColumn
	private Long endtime;
	@SqlColumn
	private int status;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Long getStarttime() {
		return starttime;
	}
	public void setStarttime(Long starttime) {
		this.starttime = starttime;
	}
	public Long getEndtime() {
		return endtime;
	}
	public void setEndtime(Long endtime) {
		this.endtime = endtime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	
}
