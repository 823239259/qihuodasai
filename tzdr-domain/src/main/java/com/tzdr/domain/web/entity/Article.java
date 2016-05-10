package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;


@Entity
@Table(name="w_article")
public class Article  extends BaseEntity {

	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;

	private String catid;
	private String keywords;
	private String description;
	private String redirecturl;
	private Integer click;
	private String urlname;
	private String title;
	private String shorttitle;
	private String color;
	private String author;
	private String source;
	private String litpic;
	private Integer createdate;
	private Integer resetdate;
	private boolean recommend;
	private boolean istop;
	private String body;
	private Integer orders;
	private boolean status;
	private Integer addtime;
	private Integer ispage;

	@Column(name="catid", length=32)
	public String getCatid() {
		return this.catid;
	}

	public void setCatid(String catid) {
		this.catid = catid;
	}

	@Column(name="keywords")
	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	@Column(name="description")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="redirecturl")
	public String getRedirecturl() {
		return this.redirecturl;
	}

	public void setRedirecturl(String redirecturl) {
		this.redirecturl = redirecturl;
	}

	@Column(name="click")
	public Integer getClick() {
		return this.click;
	}

	public void setClick(Integer click) {
		this.click = click;
	}

	@Column(name="urlname", length=100)
	public String getUrlname() {
		return this.urlname;
	}

	public void setUrlname(String urlname) {
		this.urlname = urlname;
	}

	@Column(name="title", nullable=false)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name="shorttitle")
	public String getShorttitle() {
		return this.shorttitle;
	}

	public void setShorttitle(String shorttitle) {
		this.shorttitle = shorttitle;
	}

	@Column(name="color")
	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Column(name="author")
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name="source")
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name="litpic")
	public String getLitpic() {
		return this.litpic;
	}

	public void setLitpic(String litpic) {
		this.litpic = litpic;
	}

	@Column(name="createdate", nullable=false)
	public Integer getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Integer createdate) {
		this.createdate = createdate;
	}

	@Column(name="resetdate")
	public Integer getResetdate() {
		return this.resetdate;
	}

	public void setResetdate(Integer resetdate) {
		this.resetdate = resetdate;
	}

	@Column(name="recommend")
	public boolean getRecommend() {
		return this.recommend;
	}

	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}

	@Column(name="istop")
	public boolean getIstop() {
		return this.istop;
	}

	public void setIstop(boolean istop) {
		this.istop = istop;
	}

	@Column(name="body", length=65535)
	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Column(name="orders", nullable=false)
	public Integer getOrders() {
		return this.orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	@Column(name="status", nullable=false)
	public boolean getStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Column(name="addtime")
	public Integer getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Integer addtime) {
		this.addtime = addtime;
	}

	@Column(name="ispage", nullable=false)
	public Integer getIspage() {
		return this.ispage;
	}

	public void setIspage(Integer ispage) {
		this.ispage = ispage;
	}
}