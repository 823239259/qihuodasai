package com.tzdr.business.pay.pingpp.config.enums;

public enum TradeStatus {
	PENDING(0,"待处理"),
	FAIL(1,"失败"),
	SUCCESS(21,"交易成功");
	private int code;
	private String note;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	private TradeStatus(int code, String note) {
		this.code = code;
		this.note = note;
	}
	public static TradeStatus newInstance(int code) {
		for (TradeStatus c : TradeStatus.values()) {
			if (c.getCode() == code) {
				return c;
			}
		}
		return null;
	}
}
