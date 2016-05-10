package com.tzdr.domain.app.vo;

import java.io.Serializable;

import com.tzdr.common.utils.SqlColumn;

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
public class BannerVo implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@SqlColumn(name="id")
	private String id;
	
	/**
	 * 图片URL
	 */
	@SqlColumn(name="img_path")
	private String imgPath;
	
	/**
	 * 新闻编号
	 */
	@SqlColumn(name="link_url")
	private String newId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getNewId() {
		return newId;
	}

	public void setNewId(String newId) {
		this.newId = newId;
	}
}
