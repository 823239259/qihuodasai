package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;
import com.tzdr.common.utils.Dates;

/**
 * 系统中 电话、短信 通知记录表
 * @zhouchen
 * 2015年1月12日
 */
@Entity
@Table(name = "w_notice_record")
public class NoticeRecord extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	private String uid;

	/**
	 * 补仓提醒时 需记录组合id
	 * 配资方案的组合id
	 */
	private String groupId;
	/**
	 * 通知内容
	 */
	private String content;
	
	/**
	 * 通知时间
	 */
	private Long noticeTime=Dates.getCurrentLongDate();
	
	/**
	 * 通知类型： 1-次日余额不足电话通知  2-补仓提醒电话通知  3-次日余额不足短信通知  4-当日不够扣费短信通知 5-低于补仓线时发送的短信  
	 * 		  11-月月配即将到期定时提醒短信   12月月配自动延期成功或失败短信
	 */
	private Integer type;

	
	/**
	 * 通知状态 1：已通知    2：未接通
	 */
	private Integer status=1;
	
	public NoticeRecord() {
		// TODO Auto-generated constructor stub
	}
	
	
	public NoticeRecord(String uid, String content, Integer type) {
		super();
		this.uid = uid;
		this.content = content;
		this.type = type;
	}

	
	public NoticeRecord(String uid, String content, Integer type,Integer status) {
		super();
		this.uid = uid;
		this.content = content;
		this.type = type;
		this.status=status;
	}

	public NoticeRecord(String uid, String groupId, String content, Integer type) {
		super();
		this.uid = uid;
		this.groupId = groupId;
		this.content = content;
		this.type = type;
	}

	public NoticeRecord(String uid, String groupId, String content, Integer type,Integer status) {
		super();
		this.uid = uid;
		this.groupId = groupId;
		this.content = content;
		this.type = type;
		this.status=status;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}


	public Long getNoticeTime() {
		return noticeTime;
	}


	public void setNoticeTime(Long noticeTime) {
		this.noticeTime = noticeTime;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}