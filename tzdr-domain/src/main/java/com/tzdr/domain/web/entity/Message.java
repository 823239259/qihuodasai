package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.tzdr.common.domain.BaseEntity;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;


@Entity
@Table(name="w_message")
public class Message extends BaseEntity {


	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;

	/**
	 * 反馈类型
	 */
	private String type;  
	private String title;
	/**
	 * 反馈内容
	 */
	private String content; 
	 /**
	  * 反馈时间
	  */
	private Long addtime;
	/**
	 * 客服回复内容
	 */
	private String recontent;
	/**
	 * 客服回复时间
	 */
	private Long endtime;
	/**
	 * 客服 sysid
	 */
	private String reid;
	
	/**
	 * 客服 名称
	 */
	private String replyUserName;
	
	
	/**
	 * 0：未回复，1：已回复，未读，2：已读
	 */
	private short status;
	
	@Transient
	private String  typeValue;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)               
	@JoinColumn(name="uid", nullable=false)  
	private WUser wuser;

	@Column(name="type")
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name="title")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name="content")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name="addtime")
	public Long getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Long addtime) {
		this.addtime = addtime;
	}

	@Column(name="recontent")
	public String getRecontent() {
		return this.recontent;
	}

	public void setRecontent(String recontent) {
		this.recontent = recontent;
	}

	@Column(name="endtime")
	public Long getEndtime() {
		return this.endtime;
	}

	public void setEndtime(Long endtime) {
		this.endtime = endtime;
	}

	@Column(name="reid")
	public String getReid() {
		return this.reid;
	}

	public void setReid(String reid) {
		this.reid = reid;
	}

	@Column(name="status")
	public short getStatus() {
		return this.status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public WUser getWuser() {
		return wuser;
	}

	public void setWuser(WUser wuser) {
		this.wuser = wuser;
	}

	public String getTypeValue() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.MESSAGE,this.type);
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public String getReplyUserName() {
		return replyUserName;
	}

	public void setReplyUserName(String replyUserName) {
		this.replyUserName = replyUserName;
	}
	
	
	
}