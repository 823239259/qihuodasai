package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;

@Entity
@Table(name = "crawler_wallstreetn_live")
public class CrawlerWallstreetnLive extends BaseEntity{

	private static final long serialVersionUID = 1L;
	/**
	 * 是否发布 （0-未发布，1-发布）
	 */
	private String published;
	/**
	 * 标题
	 */
	private String liveTitle;
	/**
	 * 第三方数据id
	 */
	private String liveWallstreetnId;
	/**
	 * 数据类型（1-全球，2-A股，4-美股）
	 */
	private String liveType;
	/**
	 * 创建时间
	 */
	private Long liveCreatetime;
	/**
	 * 修改时间
	 */
	private Long liveUpdatetime;
	private String channelSet;
	private String type;
	private String codeType;
	private String importance;
	private Long createdAt;
	private Long updatedAt;
	private String commentStatus;
	private String star;
	public String getLiveTitle() {
		return liveTitle;
	}
	public void setLiveTitle(String liveTitle) {
		this.liveTitle = liveTitle;
	}
	public String getLiveWallstreetnId() {
		return liveWallstreetnId;
	}
	public void setLiveWallstreetnId(String liveWallstreetnId) {
		this.liveWallstreetnId = liveWallstreetnId;
	}
	public Long getLiveCreatetime() {
		return liveCreatetime;
	}
	public void setLiveCreatetime(Long liveCreatetime) {
		this.liveCreatetime = liveCreatetime;
	}
	public Long getLiveUpdatetime() {
		return liveUpdatetime;
	}
	public void setLiveUpdatetime(Long liveUpdatetime) {
		this.liveUpdatetime = liveUpdatetime;
	}
	public String getLiveType() {
		return liveType;
	}
	public void setLiveType(String liveType) {
		this.liveType = liveType;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public String getImportance() {
		return importance;
	}
	public void setImportance(String importance) {
		this.importance = importance;
	}
	public Long getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}
	public Long getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Long updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getCommentStatus() {
		return commentStatus;
	}
	public void setCommentStatus(String commentStatus) {
		this.commentStatus = commentStatus;
	}
	public String getStar() {
		return star;
	}
	public void setStar(String star) {
		this.star = star;
	}
	public String getChannelSet() {
		return channelSet;
	}
	public void setChannelSet(String channelSet) {
		this.channelSet = channelSet;
	}
	
	public String getPublished() {
		return published;
	}
	public void setPublished(String published) {
		this.published = published;
	}
	
	public CrawlerWallstreetnLive(String published, String liveTitle, String liveWallstreetnId, String liveType,
			Long liveCreatetime, Long liveUpdatetime, String channelSet, String type, String codeType,
			String importance, Long createdAt, Long updatedAt, String commentStatus, String star) {
		super();
		this.published = published;
		this.liveTitle = liveTitle;
		this.liveWallstreetnId = liveWallstreetnId;
		this.liveType = liveType;
		this.liveCreatetime = liveCreatetime;
		this.liveUpdatetime = liveUpdatetime;
		this.channelSet = channelSet;
		this.type = type;
		this.codeType = codeType;
		this.importance = importance;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.commentStatus = commentStatus;
		this.star = star;
	}
	public CrawlerWallstreetnLive() {
	}
	
	
}
