package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;

@Entity
@Table(name = "crawler_wallstreetn_live_content")
public class CrawlerWallstreetnLiveContent extends BaseEntity{

	private static final long serialVersionUID = 1L;
	/**
	 * 实时数据id
	 */
	private String liveId;
	/**
	 * 实时数据html内容
	 */
	private String liveContentHtml;
	/**
	 * 实时数据文本内容
	 */
	private String liveContentText;
	/**
	 * 实时数据图片
	 */
	private String liveContentImage;
	/**
	 * 创建时间
	 */
	private Long liveContentCreatetime;
	/**
	 * 修改时间
	 */
	private Long liveContentUpdatetime;
	public String getLiveId() {
		return liveId;
	}
	public void setLiveId(String liveId) {
		this.liveId = liveId;
	}
	public String getLiveContentHtml() {
		return liveContentHtml;
	}
	public void setLiveContentHtml(String liveContentHtml) {
		this.liveContentHtml = liveContentHtml;
	}
	public String getLiveContentText() {
		return liveContentText;
	}
	public void setLiveContentText(String liveContentText) {
		this.liveContentText = liveContentText;
	}
	public String getLiveContentImage() {
		return liveContentImage;
	}
	public void setLiveContentImage(String liveContentImage) {
		this.liveContentImage = liveContentImage;
	}
	public Long getLiveContentCreatetime() {
		return liveContentCreatetime;
	}
	public void setLiveContentCreatetime(Long liveContentCreatetime) {
		this.liveContentCreatetime = liveContentCreatetime;
	}
	public Long getLiveContentUpdatetime() {
		return liveContentUpdatetime;
	}
	public void setLiveContentUpdatetime(Long liveContentUpdatetime) {
		this.liveContentUpdatetime = liveContentUpdatetime;
	}
	public CrawlerWallstreetnLiveContent(String liveId, String liveContentHtml, String liveContentText,
			String liveContentImage, Long liveContentCreatetime, Long liveContentUpdatetime) {
		super();
		this.liveId = liveId;
		this.liveContentHtml = liveContentHtml;
		this.liveContentText = liveContentText;
		this.liveContentImage = liveContentImage;
		this.liveContentCreatetime = liveContentCreatetime;
		this.liveContentUpdatetime = liveContentUpdatetime;
	}
	public CrawlerWallstreetnLiveContent() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
