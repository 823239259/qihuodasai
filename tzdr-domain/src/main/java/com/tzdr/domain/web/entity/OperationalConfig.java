package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.tzdr.common.domain.BaseCrudEntity;

/**
 * 运营配置实体
 * @zhouchen
 * 2015年1月21日
 */
@Entity
@Table(name = "w_operational_config")
public class OperationalConfig extends BaseCrudEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 1:友情链接 2：新闻栏目 3：新闻 4：banner 5：合作伙伴6:登陆背景图 7 托管银行  8 配股宝banner 9投资达人APP-bannaer
	 */
	private  Integer  type;

	/**
	 * 标题/名称
	 */
	private String name;
	
	/**
	 * 内容
	 */
	private String content;

	/**
	 * 连接地址
	 */
	private  String linkUrl;
	
	/**
	 * 图片地址
	 */
	private String  imgPath;  
	
	/**
	 * 发布时间
	 */
	private Long releaseTime;
	
	/**
	 * 自定义的 发布时间
	 */
	private String defineReleaseTime;
	
	/**
	 * 默认未发布
	 */
	private Boolean  isRelease=Boolean.FALSE;
	
	
	/**
	 * 默认 不置顶  true为置顶
	 */
	private Boolean  isTop=Boolean.FALSE;
	
	/**
	 * true 默认启用
	 */
	private Boolean isEnable=Boolean.TRUE;
	
	/**
	 * 摘要 简要说明
	 */
	private String summary;
	
	/**
	 * 关键字
	 */
	private String keyWord;
	
	/**
	 * banner 根据这个值排序  默认为0显示为第一张，依次排序
	 */
	private Integer valueType=0;
	
	/**
	 * 新闻对应的父节点 - 新闻栏目
	 */
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	@JoinColumn(name = "parent_id")
	private OperationalConfig parentConfig;


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getLinkUrl() {
		return linkUrl;
	}


	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}


	public String getImgPath() {
		return imgPath;
	}


	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}


	public Long getReleaseTime() {
		return releaseTime;
	}


	public void setReleaseTime(Long releaseTime) {
		this.releaseTime = releaseTime;
	}


	public Boolean getIsRelease() {
		return isRelease;
	}


	public void setIsRelease(Boolean isRelease) {
		this.isRelease = isRelease;
	}


	public Boolean getIsEnable() {
		return isEnable;
	}


	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}


	public OperationalConfig getParentConfig() {
		return parentConfig;
	}


	public void setParentConfig(OperationalConfig parentConfig) {
		this.parentConfig = parentConfig;
	}


	public String getSummary() {
		return summary;
	}


	public void setSummary(String summary) {
		this.summary = summary;
	}


	public String getKeyWord() {
		return keyWord;
	}


	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}


	public Integer getValueType() {
		return valueType;
	}


	public void setValueType(Integer valueType) {
		this.valueType = valueType;
	}


	public Boolean getIsTop() {
		return isTop;
	}


	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}


	public String getDefineReleaseTime() {
		return defineReleaseTime;
	}


	public void setDefineReleaseTime(String defineReleaseTime) {
		this.defineReleaseTime = defineReleaseTime;
	}
	
	
	
}