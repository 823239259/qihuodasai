package com.tzdr.domain.vo;

/**
 * 新闻vo
 * <P>title:@OperationalConfigVo.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2015 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:zhangjun
 * @date 2015年2月2日
 * @version 1.0
 */
public class OperationalConfigVo {
	private String name;
	private String addtime;
	private String defineReleaseTime;
	public String getDefineReleaseTime() {
		return defineReleaseTime;
	}
	public void setDefineReleaseTime(String defineReleaseTime) {
		this.defineReleaseTime = defineReleaseTime;
	}
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private String linkUrl;
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
}


