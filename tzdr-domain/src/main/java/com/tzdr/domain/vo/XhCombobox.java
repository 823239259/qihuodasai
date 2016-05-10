package com.tzdr.domain.vo;

import java.io.Serializable;

import com.tzdr.domain.entity.DataMap;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see
 * @version 2.0
 * 2015年2月9日下午7:48:51
 */
public class XhCombobox implements Serializable {

	private static final long serialVersionUID = -2645172342959520296L;
	//选中值
	private String id;
	
	//显示内容
	private String text;
	
	private boolean selected;
	
	public XhCombobox(DataMap dataMap) {
		super();
		this.id = dataMap.getValueKey();
		this.text = dataMap.getValueName();
	}

	public XhCombobox() {
		super();
	}

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

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
