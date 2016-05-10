package com.tzdr.domain.app.vo;

import java.io.Serializable;

import jodd.util.ObjectUtil;

import com.tzdr.common.utils.SqlColumn;
import com.tzdr.domain.web.entity.OperationalConfig;

/**  
 * @Title: BannerVo.java     
 * @Description: banner信息VO   
 * @author： WangPinQun 
 * @E-mail：wangpinqun@tzdr.com
 * @company： 上海信闳投资管理有限公司重庆分公司
 * @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
 * @date： 2016年3月22日 下午1:42:33    
 * @version： V1.0  
 */
public class NewsVo implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 新闻id
	 */
	@SqlColumn(name="nid")
	private String nid;
	
	/**
	 * 新闻标题
	 */
	@SqlColumn(name="title")
	private String title;
	
	/**
	 *新闻内容
	 */
	@SqlColumn(name="content")
	private String content;

	/**
	 * 新闻发布日期
	 */
	@SqlColumn(name="issueDate")
	private String issueDate;
	
	public NewsVo() {
		// TODO Auto-generated constructor stub
	}

	public NewsVo(OperationalConfig config) {
		if (ObjectUtil.equals(null, config)){
			return;
		}
		this.title=config.getName();
		this.content = config.getContent();
		this.issueDate=config.getDefineReleaseTime();
		this.nid=config.getId();
	}
	
	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
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

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	
	
}
