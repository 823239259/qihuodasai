package com.tzdr.domain.vo;

import java.io.Serializable;

/**
* @Description: TODO(在线留言信息Vo)
* @ClassName: MessageVo
* @author wangpinqun
* @date 2015年1月6日 下午5:11:41
 */
public class MessageVo implements Serializable{

	/**
	* 
	*/ 
	private static final long serialVersionUID = 1L;
	private String id;
	private String type;
	private String title;
	private String content;
	private Long addtime;
	private String recontent;
	private Long endtime;
	private String reid;
	private short status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getAddtime() {
		return addtime;
	}
	public void setAddtime(Long addtime) {
		this.addtime = addtime;
	}
	public String getRecontent() {
		return recontent;
	}
	public void setRecontent(String recontent) {
		this.recontent = recontent;
	}
	public Long getEndtime() {
		return endtime;
	}
	public void setEndtime(Long endtime) {
		this.endtime = endtime;
	}
	public String getReid() {
		return reid;
	}
	public void setReid(String reid) {
		this.reid = reid;
	}
	public short getStatus() {
		return status;
	}
	public void setStatus(short status) {
		this.status = status;
	}
}
