package com.tzdr.domain.vo.future;


import java.io.Serializable;

/**
 * 
 * 
 * <p></p>
 * @author LiuQing
 * @see 消息体
 * @version 2.0
 * 2015年8月3日下午4:28:13
 */
public class MessageInfoVo implements Serializable {
	
	private static final long serialVersionUID = -4454557310569185153L;

	/**
	 * 
	 */
	private String code;
	
	/**
	 * 
	 */
	private String messageText;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

}
