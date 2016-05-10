package com.tzdr.domain.vo;

import java.io.Serializable;

/**
 * 
 * <p></p>
 * @author LiuQing
 * @see Easyui Combobox
 * @version 2.0
 * 2015年1月7日上午10:43:20
 */
public class ComboboxVo implements Serializable  {

	private static final long serialVersionUID = -8061683477503324509L;
	
	private String id;// value
	
	private String text; //显示内容
	
	private String desc; //详细内容
	
	private boolean selected; //是否已选中

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
