package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;

@Entity
@Table(name = "crawler_wallstreetn_live")
public class CrawlerWallstreetnLive extends BaseEntity{

	private static final long serialVersionUID = 1L;
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
	public CrawlerWallstreetnLive(String liveTitle, String liveWallstreetnId, String liveType, Long liveCreatetime,
			Long liveUpdatetime) {
		super();
		this.liveTitle = liveTitle;
		this.liveWallstreetnId = liveWallstreetnId;
		this.liveType = liveType;
		this.liveCreatetime = liveCreatetime;
		this.liveUpdatetime = liveUpdatetime;
	}
	public CrawlerWallstreetnLive() {
	}
	
	
}
