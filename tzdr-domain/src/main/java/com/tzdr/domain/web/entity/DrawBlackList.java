package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;

/**
 * 提现黑名单
 * <P>title:@DrawBlackList.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2015 tzdr</p>
 * <p>Company: 上海信宏</p>
 * History：
 * @author:zhangjun
 * @date 2015年5月22日
 * @version 1.0
 */

@Entity
@Table(name="w_draw_black_list")
public class DrawBlackList extends BaseCrudEntity{
	private String uid;//用户id
	private String reason;//原因
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
}

